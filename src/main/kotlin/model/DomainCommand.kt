package model

import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.db.entities.HumanDigitalTwin
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface DomainCommand

//data class RunSelectQuery(val query: SelectQuery) : DomainCommand
//data class RunAggregateQuery(val query: AggregateQuery) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertDigitalTwin(val hdt: HumanDigitalTwin, val receivedAt: Instant) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertDigitalTwinBatch(val hdts: List<HumanDigitalTwin>, val receivedAt: Instant) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertProperty(val hdt: HdtId, val property: Property, val receivedAt: Instant): DomainCommand

data class NotifyFailureCommand(val reason: String) : DomainCommand