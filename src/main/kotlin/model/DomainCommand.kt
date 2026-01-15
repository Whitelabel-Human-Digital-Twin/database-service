package io.github.whdt.model

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.coroutines.CompletableDeferred
import kotlinx.serialization.json.JsonElement
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface DomainCommand

@OptIn(ExperimentalTime::class)
data class UpdateProperty(
    val hdt: HdtId,
    val property: String,
    val value: JsonElement,
    val receivedAt: Instant
): DomainCommand

data class ReadProperty(
    val hdt: HdtId,
    val property: String,
    val replyTo: CompletableDeferred<JsonElement?>
) : DomainCommand

data class NotifyFailureCommand(val reason: String) : DomainCommand