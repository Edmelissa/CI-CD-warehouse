package com.example.database.token

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object TokenModel : Table("token") {
    private val id = TokenModel.integer("id")
    private val idUser = TokenModel.integer("id_user")
    private val token = TokenModel.varchar("token", 50)

    fun insert(newToken: TokenData){
        transaction {
            TokenModel.insert {
                it[id] = newToken.id
                it[idUser] = newToken.idUser
                it[token] = newToken.token
            }
        }
    }

    fun getToken(id: Int) : TokenData? {
        var tokenData : TokenData? = null
        transaction {
            val tokensModel = TokenModel.selectAll().where { TokenModel.id.eq(id) }
            if(!tokensModel.empty()) {
                val tokenModel = tokensModel.single()
                tokenData = TokenData(
                    id = tokenModel[TokenModel.id],
                    idUser = tokenModel[TokenModel.idUser],
                    token = tokenModel[TokenModel.token]
                )
            }
        }
        return tokenData
    }

    fun getIdUser(token: String) : Int? {
        var idUser : Int? = null
        transaction {
            val tokensModel = TokenModel.selectAll().where { TokenModel.token.eq(token) }
            if(!tokensModel.empty()){
                val tokenModel = tokensModel.single()
                idUser = tokenModel[TokenModel.idUser]
            }
        }
        return idUser
    }

    fun size() : Int?{
        var id : Int? = null
        transaction {
            id = TokenModel.selectAll().count().toInt()
        }
        return id
    }
}