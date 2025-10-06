@file:Suppress("DEPRECATION")

package io.github.whdt.db.mappingRelations

import io.github.whdt.db.relations.Sampling
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object SamplingTable : IntIdTable("associated") {
    val time_id = integer( "property_id")
    val value_id = integer( "interface_id")
}

class SamplingDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SamplingDAO>(SamplingTable)

    var time_id by SamplingTable.time_id
    val value_id by SamplingTable.value_id
}

suspend fun <T> SamplingTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: SamplingDAO) = Sampling(
    dao.time_id,
    dao.value_id
)