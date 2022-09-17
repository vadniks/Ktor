package com.some.plugins

import com.some.service.adminParam
import com.some.service.exec
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        get("/command") {
            call.respond(exec(call.request.queryParameters[adminParam] ?: "") ?: "Not found")
        }
        static {
            resource("/", "/static/index.html")
            resource("/admin", "/static/admin.html")
            static("/static") {
                resources("static")
            }
        }
    }
}
