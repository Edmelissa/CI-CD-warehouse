package com.example.features.login

import com.example.database.token.TokenData
import com.example.database.token.TokenModel
import com.example.database.users.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun loginUser(){
        val loginReceiveModel = call.receive(LoginReceiveModel::class)
        val userData = UserModel.getUser(loginReceiveModel.login)

        if(userData == null){
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if(userData.password == loginReceiveModel.password){
                val newToken = UUID.randomUUID().toString()

                TokenModel.insert(
                    TokenData(
                        id = TokenModel.size() ?: 0,
                        idUser = userData.id,
                        token = newToken
                    )
                )

                call.respond(LoginResponseModel(token = newToken))
            }
            else{
                call.respond(HttpStatusCode.BadRequest, "Incorrect password")
            }
        }

        call.respond(HttpStatusCode.BadRequest)
    }
}