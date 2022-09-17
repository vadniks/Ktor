package com.some.plugins

import com.some.service.draw
import com.some.service.drawerParam
import com.some.service.sort
import com.some.service.sortParam
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    routing {
        get("/drawer") {
            val triple = draw(call.request.queryParameters[drawerParam]?.toInt() ?: 0)
            call.respond(ThymeleafContent("drawer", mapOf(
                "error" to (triple?.third == null),
                "figure" to (triple?.third ?: ""),
                "width" to (triple?.first ?: 0u),
                "height" to (triple?.second ?: 0u)
            )))
        }
        get("/sort") {
            call.respond(ThymeleafContent("sort", mapOf(
                "sorted" to (
                    sort(call.request.queryParameters[sortParam] ?: "")
                    ?: "An error occurred"
                )
            )))
        }
    }
}
