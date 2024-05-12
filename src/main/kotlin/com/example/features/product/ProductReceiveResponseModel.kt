package com.example.features.product

import kotlinx.serialization.Serializable

@Serializable
data class AddProductReceiveModel(
    val name: String,
    val size: Int,
    val price: Long
)

@Serializable
data class AddProductResponseModel(
    val id: Int
)