package model

import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.model.property.Property
import kotlinx.coroutines.CompletableDeferred
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

sealed interface DomainCommand

//data class RunSelectQuery(val query: SelectQuery) : DomainCommand
//data class RunAggregateQuery(val query: AggregateQuery) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertDigitalTwin(val hdt: HumanDigitalTwin, val receivedAt: Instant, val reply: CompletableDeferred<Boolean>) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertDigitalTwinBatch(val hdts: List<HumanDigitalTwin>, val receivedAt: Instant, val reply: CompletableDeferred<Unit>) : DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertProperty(val hdt: HdtId, val property: Property, val receivedAt: Instant, val reply: CompletableDeferred<Boolean>): DomainCommand

@OptIn(ExperimentalTime::class)
data class InsertTime(val hdt: String, val property: String,val valu: String, val time: LocalDateTime, val receivedAt: Instant, val reply: CompletableDeferred<Boolean>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetPropertyOfDt(val hdt: String, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetMaxValue(val hdtName : String,val timeLess: LocalDateTime, val timeGreter: LocalDateTime,val propName: String, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetMinValue(val hdtName : String,val timeLess: LocalDateTime, val timeGreter: LocalDateTime,val propName: String, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetAvgValue(val hdtName : String,val timeLess: LocalDateTime, val timeGreter: LocalDateTime,val propName: String, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetDtPropertyRange( val propName: String,val minValue: String,val maxValue: String, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetValueOfTime(val timeLess: LocalDateTime, val timeGreter: LocalDateTime, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetValueOfTimeOfPropOfDt( val hdtName : String,val propName: String,val timeLess: LocalDateTime, val timeGreter: LocalDateTime, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

@OptIn(ExperimentalTime::class)
data class GetValueOfTimeDt( val hdt: String,val timeLess: LocalDateTime, val timeGreter: LocalDateTime, val receivedAt: Instant, val reply: CompletableDeferred<Any>): DomainCommand

data class NotifyFailureCommand(val reason: String) : DomainCommand