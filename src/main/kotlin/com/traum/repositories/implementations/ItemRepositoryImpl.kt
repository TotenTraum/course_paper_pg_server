package com.traum.repositories.implementations

import com.traum.models.Item
import com.traum.repositories.interfaces.IItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("Unused")
class ItemRepositoryImpl(private val connection: Connection) : IItemRepository {
    companion object {
        private const val SELECT_ITEM_BY_ID =
            """select "Id", "CategoryId", "Name", "Description", "IsNotForSale" from "Items" where "Id" = ?"""
        private const val SELECT_ITEM =
            """ select "Id", "CategoryId", "Name", "Description", "IsNotForSale" from "Items" """
        private const val INSERT_ITEM = "{? = call add_item(?, ?, ?, ?)}"
        private const val UPDATE_ITEM = "{call update_item(?, ?, ?, ?, ?)}"
        private const val DELETE_ITEM = "{call delete_item(?)}"
    }

    override suspend fun getAll(): List<Item> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_ITEM)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Item>()
        while (resultSet.next())
            result.add(resultSet.toItem())
        return@withContext result
    }

    override suspend fun getById(id: Long): Item = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_ITEM_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toItem()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(DELETE_ITEM)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(item: Item): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(UPDATE_ITEM)
        statement.setLong(1, item.id)
        statement.setLong(2, item.categoryId)
        statement.setString(3, item.name)
        statement.setString(4, item.description)
        statement.setBoolean(5, item.isNotForSale)
        statement.execute()
    }

    override suspend fun add(item: Item): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_ITEM)
        statement.setLong(2, item.categoryId)
        statement.setString(3, item.name)
        statement.setString(4, item.description)
        statement.setBoolean(5, item.isNotForSale)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toItem(): Item {
        val item = Item()
        item.id = this.getLong("\"Id\"")
        item.categoryId = this.getLong("\"CategoryId\"")
        item.name = this.getString("\"Name\"")
        item.description = this.getString("\"Description\"")
        item.isNotForSale = this.getBoolean("\"IsNotForSale\"")
        return item
    }
}