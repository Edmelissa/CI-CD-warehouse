package com.example.database.companies

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object CompanyModel: Table("companies") {
    private val id = CompanyModel.integer("id")
    private val name = CompanyModel.varchar("name", 50)
    private val bin = CompanyModel.long("bin")

    fun insert(newCompany : CompanyData){
        transaction {
            CompanyModel.insert {
                it[CompanyModel.id] = newCompany.id
                it[CompanyModel.name] = newCompany.name
                it[CompanyModel.bin] = newCompany.bin
            }
        }
    }

    fun getCompany(id: Int) : CompanyData? {
        var companyData : CompanyData? = null
        transaction {
            val allCompanyModel = CompanyModel.selectAll().where { CompanyModel.id.eq(id) }
            if(!allCompanyModel.empty()) {
                val companyModel = allCompanyModel.single()
                companyData = CompanyData(
                    id = companyModel[CompanyModel.id],
                    name = companyModel[CompanyModel.name],
                    bin = companyModel[CompanyModel.bin]
                )
            }
        }
        return companyData
    }

    fun getCompanyByBin(bin: Long) : CompanyData? {
        var companyData : CompanyData? = null
        transaction {
            val allCompanyModel = CompanyModel.selectAll().where { CompanyModel.bin.eq(bin) }
            if(!allCompanyModel.empty()) {
                val companyModel = allCompanyModel.single()
                companyData = CompanyData(
                    id = companyModel[CompanyModel.id],
                    name = companyModel[CompanyModel.name],
                    bin = companyModel[CompanyModel.bin]
                )
            }
        }
        return companyData
    }

    fun getCompanies() : List<CompanyData> {
        var allCompanyData = mutableListOf<CompanyData>()

        transaction {
            val allCompanyModel = CompanyModel.selectAll()
            for(companyModel in allCompanyModel){
                val companyData = CompanyData(
                    id = companyModel[CompanyModel.id],
                    name = companyModel[CompanyModel.name],
                    bin = companyModel[CompanyModel.bin]
                )

                allCompanyData.add(companyData)
            }
        }

        return allCompanyData
    }

    fun size() : Int? {
        var size : Int? = null
        transaction {
            size = CompanyModel.selectAll().count().toInt()
        }
        return size
    }


}