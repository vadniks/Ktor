package com.some

import com.some.db.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import com.some.plugins.*

fun main() = embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
    DatabaseFactory.init()
    configureTemplating()
    configureRouting()
}.start(wait = true).run(fun(_) = Unit)
