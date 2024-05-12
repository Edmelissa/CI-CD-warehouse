package com.example.features.company

import com.example.database.companies.CompanyData
import io.ktor.http.*
import io.ktor.server.application.*
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class CompanyControllerTest {

    val call = mock<ApplicationCall>()
    lateinit var companyController : CompanyController

    @Test
    fun `test fun respondAddCompany Conflict status`() {
        val companyData = null

        companyController = CompanyController(call)
        val httpStatusCode = companyController.respondAddCompany(companyData)

        assertEquals(httpStatusCode, HttpStatusCode.OK)
    }

    @Test
    fun `test fun respondAddCompany Ok status`() {
        val companyData = CompanyData(1,"test",1234567889012)

        companyController = CompanyController(call)
        val httpStatusCode = companyController.respondAddCompany(companyData)

        assertEquals(httpStatusCode, HttpStatusCode.Conflict)
    }
}