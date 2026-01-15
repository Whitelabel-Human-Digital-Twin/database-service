package model

import db.query.model.AggregateQuery
import db.query.model.SelectQuery

sealed interface DomainCommand

data class RunSelectQuery(val query: SelectQuery) : DomainCommand
data class RunAggregateQuery(val query: AggregateQuery) : DomainCommand
data class NotifyFailureCommand(val reason: String) : DomainCommand