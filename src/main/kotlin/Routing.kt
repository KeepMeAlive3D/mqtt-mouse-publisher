package de.keepmealive3d

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.concurrent.thread

fun Application.configureRouting() {
    routing {
        staticResources("/", "static")

        get("/animate") {
            if(AnimStatus.running) {
                call.respondText("Anim already running ...")
                return@get
            }
            call.respond(HttpStatusCode.OK, "Started")
            AnimStatus.running = true
            thread { runAnimate() }
        }
    }
}
