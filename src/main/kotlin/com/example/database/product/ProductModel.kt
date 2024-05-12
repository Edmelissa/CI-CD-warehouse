package com.example.database.product

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object ProductModel: Table("product") {
    private val id = ProductModel.integer("id")
    private val name = ProductModel.varchar("name", 50)
    private val size = ProductModel.integer("size")
    private val price = ProductModel.long("price")

    fun insert(newProduct : ProductData){
        transaction {
            ProductModel.insert {
                it[ProductModel.id] = newProduct.id
                it[ProductModel.name] = newProduct.name
                it[ProductModel.size] = newProduct.size
                it[ProductModel.price] = newProduct.price
            }
        }
    }

    fun getProductById(id : Int) : ProductData?{
        var productData : ProductData? = null
        transaction {
            val allProductModel = ProductModel.selectAll().where { ProductModel.id.eq(id) }
            if(!allProductModel.empty()) {
                val productModel = allProductModel.single()
                productData = ProductData(
                    id = productModel[ProductModel.id],
                    name = productModel[ProductModel.name],
                    size = productModel[ProductModel.size],
                    price = productModel[ProductModel.price]
                )
            }
        }
        return productData
    }

    fun maxId() : Int? {
        var id : Int? = null
        transaction {
            val maxIdExpression = ProductModel.id.max()
            id = ProductModel.slice(maxIdExpression).selectAll().single()[maxIdExpression]
        }

        return id
    }
}