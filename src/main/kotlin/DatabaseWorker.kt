import db.query.model.AggregateQuery
import db.query.model.SelectField
import db.query.model.SelectQuery
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.DomainCommand
import model.InsertProperty
import model.NotifyFailureCommand
import model.RunAggregateQuery
import model.RunSelectQuery
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Application.configureDatabaseWorker(commandChannel: Channel<DomainCommand>) {
    launch {
        for (cmd in commandChannel) {
            when (cmd) {
                is RunSelectQuery -> handleSelectQuery(cmd.query)
                is RunAggregateQuery -> handleAggregateQuery(cmd.query)
                is InsertProperty -> handleInsertProperty(cmd.hdt, cmd.property, cmd.receivedAt)
                is NotifyFailureCommand -> log.warn("Failed: ${cmd.reason}")
            }
        }
    }
}

fun handleSelectQuery(query: SelectQuery) {

}

fun handleAggregateQuery(query: AggregateQuery) {

}

@OptIn(ExperimentalTime::class)
fun handleInsertProperty(hdt: HdtId, property: Property, receivedAt: Instant) {

}