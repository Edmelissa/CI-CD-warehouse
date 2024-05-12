package com.example.features.product

import com.example.features.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureProductRouting() {
    routing {
        post("/product") {
            val productController = ProductController(call)
            productController.addProduct()
        }
    }
}