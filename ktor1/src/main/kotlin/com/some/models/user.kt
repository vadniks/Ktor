package com.some.models

import org.jetbrains.exposed.sql.Table

const val ID = "id"
const val NAME = "name"
const val SURNAME = "surname"

data class User(
    val id: Int,
    val name: String,
    val surname: String
)

object Users : Table() {
    val id = integer(ID).autoIncrement()
    val name = varchar(NAME, 20)
    val surname = varchar(SURNAME, 40)
    override val primaryKey = PrimaryKey(id)
}
