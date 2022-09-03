package com.some.db

import com.some.db.DatabaseFactory.dbQuery
import com.some.models.User
import com.some.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class Repository private constructor() {

    private fun resultRowToUser(row: ResultRow) = User(
        row[Users.id],
        row[Users.name],
        row[Users.surname]
    )

    suspend fun getUsers(): List<User> = dbQuery {
        Users.selectAll().map(this::resultRowToUser)
    }

    suspend fun addUser(name: String, surname: String): Unit = dbQuery { Users.insert {
        it[Users.name] = name
        it[Users.surname] = surname
    } }

    val version get() = DatabaseFactory.version

    suspend fun exec(command: String): String = withContext(Dispatchers.IO) {
        Runtime.getRuntime().exec(command)
    }.inputStream.reader().readText()

    companion object { val repository = Repository() }
}
