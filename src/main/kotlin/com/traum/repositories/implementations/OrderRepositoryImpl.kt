package com.traum.repositories.implementations

import com.traum.models.Order
import com.traum.repositories.interfaces.IOrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import java.sql.Connection
import java.sql.ResultSet

@Suppress("unused")
class OrderRepositoryImpl(private val connection: Connection) : IOrderRepository, KoinComponent {
    companion object {
        private const val SELECT_ORDER_BY_ID =
            """select "Id", "EmployeeId", "TableId", "Sum", "Date" from "Orders" where "Id" = ?"""
        private const val SELECT_ORDER = """select "Id", "EmployeeId", "TableId", "Sum", "Date" from "Orders"""
        private const val INSERT_ORDER = "{? = call add_order(?, ?, ?, ?)}"
        private const val UPDATE_ORDER = "{call update_order(?, ?, ?, ?, ?)}"
        private const val DELETE_ORDER = "{call delete_order(?)}"
    }

    override suspend fun getAll(): List<Order> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_ORDER)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Order>()
        while (resultSet.next())
            result.add(resultSet.toOrder())
        return@withContext result
    }

    override suspend fun getById(id: Long): Order = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_ORDER_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toOrder()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(DELETE_ORDER)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(order: Order): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(UPDATE_ORDER)
        statement.setLong(1, order.id)
        statement.setLong(2, order.tableId)
        statement.setLong(3, order.employeeId)
        statement.setBigDecimal(4, order.sum)
        statement.setTimestamp(5, order.date)
        statement.execute()
    }

    override suspend fun add(order: Order): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_ORDER)
        statement.setLong(2, order.tableId)
        statement.setLong(3, order.employeeId)
        statement.setBigDecimal(4, order.sum)
        statement.setTimestamp(5, order.date)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toOrder(): Order {
        val order = Order()
        order.id = this.getLong("\"Id\"")
        order.employeeId = this.getLong("\"EmployeeId\"")
        order.tableId = this.getLong("\"TableId\"")
        order.sum = this.getBigDecimal("\"Sum\"")
        order.date = this.getTimestamp("\"Date\"")
        return order
    }
}