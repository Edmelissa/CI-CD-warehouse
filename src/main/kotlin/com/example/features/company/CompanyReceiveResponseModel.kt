package com.example.features.company

import kotlinx.serialization.Serializable

@Serializable
data class AddCompanyReceiveModel(
    val name: String,
    val bin: Long
)

@Serializable
data class AddCompanyResponseModel(
    val id: Int
)

@Serializable
data class GetCompanyResponseModel(
    val name: String
)

@Serializable
data class GetCompaniesResponseModel(
    val ids: List<Int>
)

