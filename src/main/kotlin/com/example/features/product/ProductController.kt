package com.example.features.product

import com.example.database.product.ProductData
import com.example.database.product.ProductModel
import com.example.database.token.TokenModel
import com.example.database.users.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ProductController(private val call: ApplicationCall) {
    suspend fun addProduct(){
        val token = call.request.header("Auth")

        val addProductReceiveModel = call.receive(AddProductReceiveModel::class)

        if(token != null) {
            val idUser = TokenModel.getIdUser(token)

            if(idUser != null){

                val user = UserModel.getUserById(idUser)

                if(user != null) {
                    if(user.role == 2) {

                        val productId = (ProductModel.maxId() ?: 0) + 1
                        ProductModel.insert(
                            ProductData(
                                id = productId,
                                name = addProductReceiveModel.name,
                                size = addProductReceiveModel.size,
                                price = addProductReceiveModel.price
                            )
                        )

                        call.respond(AddProductResponseModel(id = productId))
                    }
                    else{
                        call.respond(HttpStatusCode.Conflict, "User with this role cannot create products")
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
}