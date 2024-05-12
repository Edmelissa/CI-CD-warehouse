package com.example.features.warehouse

import com.example.features.registration.RegistrationController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureWarehouseRouting() {
    routing {
        post("/warehouse") {
            val warehouseController = WarehouseController(call)
            warehouseController.addWarehouse()
        }

        get("/warehouses"){
            val warehouseController = WarehouseController(call)
            warehouseController.getAllWarehouse()
        }
    }
}