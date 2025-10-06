@file:Suppress("DEPRECATION")

package io.github.whdt.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

interface TransactionManager {
    suspend fun <T> executeTransaction(block: suspend Transaction.() -> T): T
}

 suspend fun <T> executeTransaction(block: suspend Transaction.() -> T): T =
     newSuspendedTransaction(Dispatchers.IO, statement = block)