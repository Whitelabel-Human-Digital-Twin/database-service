package io.github.whdt

import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.distributed.message.Message
import io.github.whdt.distributed.namespace.Namespace
import model.DomainCommand
import model.NotifyFailureCommand
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun Application.configureMqttListener(commands: Channel<DomainCommand>) {
    val mqttClient = MqttClient.builder()
        .useMqttVersion5()
        .identifier(System.getenv("MQTT_IDENTIFIER") ?: "whdt-mqtt-client")
        .serverHost(System.getenv("MQTT_HOST") ?: "localhost")
        .serverPort((System.getenv("MQTT_PORT") ?: "1883").toInt())
        .buildAsync()

    // Connect
    mqttClient.connect()

    // Subscribe
    mqttClient.subscribeWith()
        .topicFilter("hdt/+/+")
        .callback { publish ->
            handleMqttMessage(publish, commands)
        }
        .send()

    log.info("MQTT listener started")
}

@OptIn(ExperimentalTime::class)
fun Application.handleMqttMessage(
    publish: Mqtt5Publish,
    commandChannel: SendChannel<DomainCommand>
) {
    val topic = publish.topic.toString()
    val payloadBytes = publish.payloadAsBytes
    val receivedAt = Clock.System.now()

    val message = runCatching {
        Json.decodeFromString<Message>(payloadBytes.decodeToString())
    }.getOrElse {
        log.error("Invalid MQTT message on $topic", it)
        return
    }

    val command = mapToDomainCommand(topic, message, receivedAt)

    val result = commandChannel.trySend(command)

    if (result.isFailure) {
        log.warn("Command queue full, dropping MQTT message")
    }
}

@OptIn(ExperimentalTime::class)
fun mapToDomainCommand(
    topic: String,
    message: Message,
    receivedAt: Instant
): DomainCommand {
    return when {
        topic.endsWith(Namespace.PROPERTY_UPDATE_REQUEST_POSTFIX_MQTT) -> {
            val prop = message.unwrap<Property>().getOrNull()
            if (prop != null) {
                TODO()
            } else {
                NotifyFailureCommand("Can't read property for topic $topic")
            }
        }
        else -> error("Unknown topic: $topic")
    }
}