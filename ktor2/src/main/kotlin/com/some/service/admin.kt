package com.some.service

const val adminParam = "command"

fun exec(command: String) = when (command) {
    "ls", "ps", "whoami", "id", "pwd", "uname -a" ->
        Runtime.getRuntime().exec(command).inputStream.reader().readText()
    else -> null
}
