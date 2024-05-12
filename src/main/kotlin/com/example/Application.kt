package com.example

import com.example.features.company.configureCompanyRouting
import com.example.features.login.configureLoginRouting
import com.example.features.product.configureProductRouting
import com.example.features.registration.configureRegistrationRouting
import com.example.features.warehouse.configureWarehouseRouting
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect("jdbc:postgresql://db:5432/warehouse_inventory", driver="org.postgresql.Driver", "postgres", "Jkmuf2001")
    embeddedServer(Netty, port = 5500, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureRegistrationRouting()
    configureLoginRouting()
    configureCompanyRouting()
    configureProductRouting()
    configureWarehouseRouting()
}
