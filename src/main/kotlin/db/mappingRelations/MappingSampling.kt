package io.github.whdt.db.mappingRelations

import io.github.whdt.db.JdbcTransactionManager
import io.github.whdt.db.relations.Sampling
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

object SamplingTable : IntIdTable("associated") {
    val time_id = integer( "time_id")
    val value_id = integer( "interface_id")
}

class SamplingDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SamplingDAO>(SamplingTable)

    var time_id by SamplingTable.time_id
    var value_id by SamplingTable.value_id
}

suspend fun <T> SamplingTransaction(block: Transaction.() -> T): T =
    JdbcTransactionManager.execute(block)

fun daoToModel(dao: SamplingDAO) = Sampling(
    dao.time_id,
    dao.value_id
)