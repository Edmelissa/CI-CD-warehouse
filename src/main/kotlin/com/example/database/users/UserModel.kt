package com.example.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel: Table("users") {
    private val id = UserModel.integer("id")
    private val login = UserModel.varchar("login", 20)
    private val password = UserModel.varchar("password", 20)
    private val name = UserModel.varchar("name", 20)
    private val secondName = UserModel.varchar("second_name", 20)
    private val role = UserModel.integer("role")
    private val idCompany = UserModel.integer("id_company")

    fun insert(user: UserData){
        transaction {
            UserModel.insert {
                it[id] = user.id
                it[login] = user.login
                it[password] = user.password

                it[name] = user.name
                it[secondName] = user.secondName
                it[role] = user.role
                it[idCompany] = user.idCompany
            }
        }
    }

    fun getUser(login: String) : UserData?{
        var userData : UserData? = null
        transaction {
            val usersModel = UserModel.selectAll().where { UserModel.login.eq(login) }
            if(!usersModel.empty()) {
                val userModel = usersModel.single()
                userData = UserData(
                    id = userModel[UserModel.id],
                    login = userModel[UserModel.login],
                    password = userModel[UserModel.password],

                    name = userModel[UserModel.name],
                    secondName = userModel[UserModel.secondName],
                    role = userModel[UserModel.role],
                    idCompany = userModel[UserModel.idCompany],
                )
            }
        }
        return userData
    }

    fun getUserById(id: Int) : UserData? {
        var userData : UserData? = null
        transaction {
            val usersModel = UserModel.selectAll().where { UserModel.id.eq(id) }
            if(!usersModel.empty()) {
                val userModel = usersModel.single()
                userData = UserData(
                    id = userModel[UserModel.id],
                    login = userModel[UserModel.login],
                    password = userModel[UserModel.password],

                    name = userModel[UserModel.name],
                    secondName = userModel[UserModel.secondName],
                    role = userModel[UserModel.role],
                    idCompany = userModel[UserModel.idCompany],
                )
            }
        }
        return userData
    }

    fun size() : Int?{
        var id : Int? = null
        transaction {
            id = UserModel.selectAll().count().toInt()
        }
        return id
    }
}