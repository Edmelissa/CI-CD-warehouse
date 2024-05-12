package com.example.features.registration

import com.example.database.token.TokenData
import com.example.database.token.TokenModel
import com.example.database.users.UserData
import com.example.database.users.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RegistrationController(private val call: ApplicationCall) {

    suspend fun registrationUser(){
        val registrationReceiveModel = call.receive(RegistrationReceiveModel::class)

        val userData = UserModel.getUser(registrationReceiveModel.login)

        if(userData != null){
            call.respond(HttpStatusCode.Conflict, "User exists")
        }
        else{
            val newToken = UUID.randomUUID().toString()

            val userId = (UserModel.size() ?: 0) + 1
            UserModel.insert(
                UserData(
                    id = userId,
                    login = registrationReceiveModel.login,
                    password = registrationReceiveModel.password,

                    name = registrationReceiveModel.name,
                    secondName = registrationReceiveModel.secondName,
                    role = registrationReceiveModel.role,
                    idCompany = registrationReceiveModel.idCompany,
                )
            )

            TokenModel.insert(
                TokenData(
                    id = TokenModel.size() ?: 0,
                    idUser = userId,
                    token = newToken
                )
            )

            call.respond(RegistrationResponseModel(token = newToken))
        }

    }
}