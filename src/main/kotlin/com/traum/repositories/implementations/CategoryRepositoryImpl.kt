package com.traum.repositories.implementations

import com.traum.models.Category
import com.traum.repositories.interfaces.ICategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types

@Suppress("Unused")
class CategoryRepositoryImpl(private val connection: Connection) : ICategoryRepository {
    companion object {
        private const val SELECT_CATEGORY_BY_ID =
            """select "Id", "Name" from "Categories" where "Id" = ?"""
        private const val SELECT_CATEGORY =
            """select "Id", "Name" from "Categories" """
        private const val INSERT_CATEGORY = "{? = call add_category(?)}"
        private const val UPDATE_CATEGORY = "call update_category(?, ?)"
        private const val DELETE_CATEGORY = "call delete_category(?)"
    }

    override suspend fun getAll(): List<Category> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_CATEGORY)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Category>()
        while (resultSet.next())
            result.add(resultSet.toCategory())
        return@withContext result
    }

    override suspend fun getById(id: Long): Category = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_CATEGORY_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toCategory()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_CATEGORY)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(category: Category): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_CATEGORY)
        statement.setLong(1, category.id)
        statement.setString(2, category.name)
        statement.execute()
    }

    override suspend fun add(category: Category): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_CATEGORY)
        statement.registerOutParameter(1, Types.BIGINT)
        statement.setString(2, category.name)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toCategory(): Category {
        val category = Category()
        category.id = this.getLong("Id")
        category.name = this.getString("Name")
        return category
    }
}