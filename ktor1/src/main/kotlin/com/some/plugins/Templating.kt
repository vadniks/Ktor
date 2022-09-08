package com.some.plugins

import com.some.db.Repository.Companion.getRepository
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
                    "users" to getRepository().getUsers(),
                    "dbVersion" to getRepository().version,
                    "osInfo" to
                            getProperty("os.name") + ' '
                            + getProperty("os.version")
                            + " username " + getProperty("user.name")
                            + " working in " + getRepository().exec("pwd"),
                    "javaVersion" to
                            getProperty("java.version") + ' '
                            + getProperty("java.home") + ' '
                            + getProperty("java.vm.name")
                )
            ))
        }
    }
}
