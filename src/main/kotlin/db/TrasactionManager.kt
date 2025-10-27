@file:Suppress("DEPRECATION")

package io.github.whdt.db
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

interface TransactionManager {
    suspend fun <T> execute(block: suspend Transaction.() -> T): T
}

object JdbcTransactionManager: TransactionManager {
    override suspend fun <T> execute(block: suspend Transaction.() -> T): T {
        return newSuspendedTransaction(context = Dispatchers.IO, statement = block)
    }
}

object R2dbcTransactionManager: TransactionManager {
    override suspend fun <T> execute(block: suspend Transaction.() -> T): T {
        return suspendTransaction(statement = block)
    }
}