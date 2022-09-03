package com.some.db

import com.some.db.DatabaseFactory.dbQuery
import com.some.models.User
import com.some.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*

class Repository private constructor() {
    val version get() = DatabaseFactory.version

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

    suspend fun tryAddUser(name: String, surname: String) =
        if (getUser(name, surname) == null) addUser(name, surname) else Unit

    @Suppress("MemberVisibilityCanBePrivate")
    suspend fun getUser(name: String, surname: String): User? = dbQuery { Users.select {
        (Users.name eq name) and (Users.surname eq surname)
    }.mapLazy(this::resultRowToUser).takeUnless { it.empty() }?.single() }

    suspend fun exec(command: String): String = withContext(Dispatchers.IO) {
        Runtime.getRuntime().exec(command)
    }.inputStream.reader().readText()

    companion object { val repository = Repository().apply { runBlocking {
        tryAddUser("Alex", "Rover")
        tryAddUser("Bob", "Marley")
        tryAddUser("Kate", "Yandson")
        tryAddUser("Lilo", "Black")
    } } }
}
