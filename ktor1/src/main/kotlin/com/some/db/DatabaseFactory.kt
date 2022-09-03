package com.some.db

import com.some.models.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.RoundingMode

object DatabaseFactory {
    var version: Int = -1; private set

    fun init() = transaction(Database.connect(
        "jdbc:postgresql://db:5432/appDB",
        "org.postgresql.Driver",
        "postgres",
        "postgres"
    )) {
        version = db.version.setScale(0, RoundingMode.HALF_UP).toInt()
        SchemaUtils.create(Users)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
