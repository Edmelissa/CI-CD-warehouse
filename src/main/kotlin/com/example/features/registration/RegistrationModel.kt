package com.example.features.registration

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationReceiveModel(
    val login: String,
    val password: String,
    val name: String,
    val secondName: String,
    val role: Int,
    val idCompany: Int
)

@Serializable
data class RegistrationResponseModel(
    val token: String
)