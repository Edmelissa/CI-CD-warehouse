package com.example.features.registration

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegistrationRouting() {
    routing {
        post("/registration") {
            val registrationController = RegistrationController(call)
            registrationController.registrationUser()
        }
    }
}