package com.traum.repositories.implementations

import com.traum.models.Table
import com.traum.repositories.interfaces.ITableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types

@Suppress("Unused")
class TableRepositoryImpl(private val connection: Connection) : ITableRepository {
    companion object {
        private const val SELECT_TABLE_BY_ID =
            """select "Id", "TableNumber" from "Tables" where "Id" = ?"""
        private const val SELECT_TABLE =
            """select "Id", "TableNumber" from "Tables" """
        private const val INSERT_TABLE = "{? = call add_table(?)}"
        private const val UPDATE_TABLE = "call update_table(?, ?)"
        private const val DELETE_TABLE = "call delete_table(?)"
    }

    override suspend fun getAll(): List<Table> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_TABLE)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Table>()
        while (resultSet.next())
            result.add(resultSet.toTable())
        return@withContext result
    }

    override suspend fun getById(id: Long): Table = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_TABLE_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toTable()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_TABLE)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(table: Table): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_TABLE)
        statement.setLong(1, table.id)
        statement.setInt(2, table.tableNumber)
        statement.execute()
    }

    override suspend fun add(table: Table): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_TABLE)
        statement.registerOutParameter(1, Types.BIGINT)
        statement.setInt(2, table.tableNumber)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toTable(): Table {
        val table = Table()
        table.id = getLong("Id")
        table.tableNumber = getInt("TableNumber")
        return table
    }
}