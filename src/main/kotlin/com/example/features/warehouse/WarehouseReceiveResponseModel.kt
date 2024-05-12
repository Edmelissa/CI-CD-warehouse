package com.example.features.warehouse

import kotlinx.serialization.Serializable

@Serializable
data class AddWarehouseReceiveModel(
    val name: String,
    val address: String,
    val size: Int
)

@Serializable
data class AddWarehouseResponseModel(
    val id: Int
)

@Serializable
data class GetAllWarehouseResponseModel(
    val ids: List<Int>
)


