package com.some

import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import com.some.plugins.*

fun main() { embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
    configureTemplating()
    configureRouting()
}.start(wait = true) }
