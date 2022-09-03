package com.some.plugins

import com.some.db.Repository.Companion.repository
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.lang.System.currentTimeMillis
import kotlin.math.absoluteValue

fun Application.configureRouting() = routing {
    static("/static") {
        resources("static")
    }
    get("/add") {
        val millis = currentTimeMillis().toInt().absoluteValue
        repository.addUser("_name_$millis", "_surname_$millis")
        repository.addUser("_name_$millis", "_surname_$millis")
        call.respondRedirect("/")
    }
}
