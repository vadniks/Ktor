package com.some.models

import org.jetbrains.exposed.sql.Table

const val ID = "id"
const val NAME = "name"
const val SURNAME = "surname"

const val VARCHAR_LIMIT = 64

data class User(
    val id: Int,
    val name: String,
    val surname: String
)

object Users : Table() {
    val id = integer(ID).autoIncrement()
    val name = varchar(NAME, VARCHAR_LIMIT)
    val surname = varchar(SURNAME, VARCHAR_LIMIT)
    override val primaryKey = PrimaryKey(id)
}
