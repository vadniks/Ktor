package com.some.plugins

import com.some.db.Repository.Companion.repository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.lang.System.getProperty

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    routing {
        get("/") {
            call.respond(ThymeleafContent(
                "index",
                mapOf(
                    "users" to repository.getUsers(),
                    "dbVersion" to repository.version,
                    "osInfo" to repository.exec("uname -r") + getProperty("os.name") + getProperty("os.version"),
                    "javaVersion" to getProperty("java.version") + getProperty("java.home")
                )
            ))
        }
    }
}
