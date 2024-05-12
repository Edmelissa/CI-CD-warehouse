package com.example.features.warehouse

import com.example.database.token.TokenModel
import com.example.database.users.UserModel
import com.example.database.warehouse.WarehouseData
import com.example.database.warehouse.WarehouseModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class WarehouseController(private val call: ApplicationCall) {
    suspend fun addWarehouse() {
        val token = call.request.header("Auth")

        val addWarehouseReceiveModel = call.receive(AddWarehouseReceiveModel::class)

        if(token != null) {
            val idUser = TokenModel.getIdUser(token)

            if(idUser != null){

                val user = UserModel.getUserById(idUser)

                if(user != null) {
                    if(user.role == 2) {

                        val warehouseId = (WarehouseModel.maxId() ?: 0) + 1
                        WarehouseModel.insert(
                            WarehouseData(
                                id = warehouseId,
                                name = addWarehouseReceiveModel.name,
                                address = addWarehouseReceiveModel.address,
                                size = addWarehouseReceiveModel.size,
                                idOwner = idUser
                            )
                        )

                        call.respond(AddWarehouseResponseModel(id = warehouseId))
                    }
                    else{
                        call.respond(HttpStatusCode.Conflict, "User with this role cannot create warehouse")
                    }
                }
                else {
                    call.respond(HttpStatusCode.Conflict, "User with this ID not found")
                }
            }
            else{
                call.respond(HttpStatusCode.Conflict, "User with this token not found")
            }
        }
        else{
            call.respond(HttpStatusCode.BadRequest, "Token is not indicated")
        }
    }

    suspend fun getAllWarehouse(){
        val token = call.request.header("Auth")

        val idOwner = (call.parameters["idOwner"])?.toInt()

        if(token != null) {
            val idUser = TokenModel.getIdUser(token)

            if(idUser != null) {
                if(idOwner != null){
                    val allWarehouseData = WarehouseModel.getAllWarehouseByIdOwner(idOwner)

                    val warehouseIds = mutableListOf<Int>()
                    for(warehouseData in allWarehouseData){
                        warehouseIds.add(warehouseData.id)
                    }

                    call.respond(GetAllWarehouseResponseModel(ids = warehouseIds))
                }
                else {
                    call.respond(HttpStatusCode.BadRequest, "IdOwner is not indicated")
                }
            }
            else{
                call.respond(HttpStatusCode.Conflict, "User with this token not found")
            }
        }
        else {
            call.respond(HttpStatusCode.BadRequest, "Token is not indicated")
        }

    }
}