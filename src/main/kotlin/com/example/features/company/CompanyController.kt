package com.example.features.company

import com.example.database.companies.CompanyData
import com.example.database.companies.CompanyModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class CompanyController(private val call: ApplicationCall) {

    fun respondAddCompany(companyData: CompanyData?): HttpStatusCode{
        return if (companyData != null){
            HttpStatusCode.Conflict
        } else{
            HttpStatusCode.OK
        }
    }

    suspend fun addCompany() {
        val addCompanyReceiveModel = call.receive(AddCompanyReceiveModel::class)

        val companyData = CompanyModel.getCompanyByBin(addCompanyReceiveModel.bin)

        val statusCode = respondAddCompany(companyData)

        if(companyData != null){
            call.respond(statusCode, "Company exists")
        }
        else{
            val companyId = (CompanyModel.size() ?: 0) + 1
            CompanyModel.insert(
                CompanyData(
                    id = companyId,
                    name = addCompanyReceiveModel.name,
                    bin = addCompanyReceiveModel.bin
                )
            )

            call.respond(statusCode, AddCompanyResponseModel(id = companyId))
        }
    }

    suspend fun getCompany() {
        val id = (call.parameters["id"])?.toInt()

        if(id != null) {
            val CompanyData = CompanyModel.getCompany(id)

            if(CompanyData != null){
                call.respond(GetCompanyResponseModel(name = CompanyData.name))
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Company not found")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Company ID is not indicated")
        }

    }

    suspend fun getCompanies() {
        val allCompanyData = CompanyModel.getCompanies()

        if(allCompanyData.isEmpty()){
            call.respond(HttpStatusCode.BadRequest, "Company not found")
        }
        else{
            val companyIds = mutableListOf<Int>()

            for(companyData in allCompanyData){
                companyIds.add(companyData.id)
            }

            call.respond(GetCompaniesResponseModel(ids = companyIds))
        }
    }
}