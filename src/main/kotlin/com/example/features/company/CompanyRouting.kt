package com.example.features.company

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCompanyRouting() {
    routing {
        post("/company") {
            val companyController = CompanyController(call)
            companyController.addCompany()
        }

        get("/company"){
            val companyController = CompanyController(call)
            companyController.getCompany()
        }

        get("/companies"){
            val companyController = CompanyController(call)
            companyController.getCompanies()
        }
    }
}