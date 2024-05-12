package com.example.database.warehouse

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object WarehouseModel: Table("warehouse") {
    private val id = WarehouseModel.integer("id")
    private val name = WarehouseModel.varchar("name", 20)
    private val address = WarehouseModel.varchar("address", 50)
    private val size = WarehouseModel.integer("size")
    private val idOwner = WarehouseModel.integer("id_owner")

    fun insert(warehouse: WarehouseData){
        transaction {
            WarehouseModel.insert {
                it[WarehouseModel.id] = warehouse.id
                it[WarehouseModel.name] = warehouse.name
                it[WarehouseModel.address] = warehouse.address
                it[WarehouseModel.size] = warehouse.size
                it[WarehouseModel.idOwner] = warehouse.idOwner
            }
        }
    }

    fun getWarehouseById(id: Int): WarehouseData?{
        var warehouseData : WarehouseData? = null
        transaction {
            val allWarehouseModel = WarehouseModel.selectAll().where { WarehouseModel.id.eq(id) }
            if(!allWarehouseModel.empty()) {
                val warehouseModel = allWarehouseModel.single()
                warehouseData = WarehouseData(
                    id = warehouseModel[WarehouseModel.id],
                    name = warehouseModel[WarehouseModel.name],
                    address = warehouseModel[WarehouseModel.address],
                    size = warehouseModel[WarehouseModel.size],
                    idOwner = warehouseModel[WarehouseModel.idOwner]
                )
            }
        }
        return warehouseData
    }

    fun getAllWarehouseByIdOwner(idOwner: Int): List<WarehouseData>{
        var allWarehouseData = mutableListOf<WarehouseData>()
        transaction {
            val allWarehouseModel = WarehouseModel.selectAll().where { WarehouseModel.idOwner.eq(idOwner) }
            for(warehouseModel in allWarehouseModel) {
                val warehouseData = WarehouseData(
                    id = warehouseModel[WarehouseModel.id],
                    name = warehouseModel[WarehouseModel.name],
                    address = warehouseModel[WarehouseModel.address],
                    size = warehouseModel[WarehouseModel.size],
                    idOwner = warehouseModel[WarehouseModel.idOwner]
                )

                allWarehouseData.add(warehouseData)
            }
        }

        return allWarehouseData
    }

    fun maxId() : Int? {
        var id : Int? = null
        transaction {
            val maxIdExpression = WarehouseModel.id.max()
            id = WarehouseModel.slice(maxIdExpression).selectAll().single()[maxIdExpression]
        }

        return id
    }
}