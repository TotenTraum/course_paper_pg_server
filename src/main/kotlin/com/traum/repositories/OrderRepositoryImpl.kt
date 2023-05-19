package com.traum.repositories

import com.traum.models.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Connection

class OrderRepositoryImpl : IOrderRepository, KoinComponent {

    val connection: Connection by inject()

    override suspend fun GetAll(): List<Order> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement("select \"Id\", \"EmployeeId\", \"TableId\", \"Sum\", \"Date\" from \"Orders\"")
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Order>()
        while (resultSet.next()) {
            var order = Order()
            order.id = resultSet.getLong("\"Id\"")
            order.employeeId = resultSet.getLong("\"EmployeeId\"")
            order.tableId = resultSet.getLong("\"TableId\"")
            order.sum = resultSet.getBigDecimal("\"Sum\"")
            order.date = resultSet.getTimestamp("\"Date\"")
            result.add(order)
        }
        return@withContext result
    }

    override suspend fun GetById(id: Long): Order = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement("select \"Id\", \"EmployeeId\", \"TableId\", \"Sum\", \"Date\" from \"Orders\" where \"Id\" = ?")
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next()) {
            var order = Order()
            order.id = resultSet.getLong("\"Id\"")
            order.employeeId = resultSet.getLong("\"EmployeeId\"")
            order.tableId = resultSet.getLong("\"TableId\"")
            order.sum = resultSet.getBigDecimal("\"Sum\"")
            order.date = resultSet.getTimestamp("\"Date\"")
            return@withContext order
        } else {
            throw Exception("Record not found")
        }
    }

    override suspend fun Delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall("{call delete_order(?)}")
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun Update(order: Order): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall("{call update_order(?, ?, ?, ?, ?)}")
        statement.setLong(1, order.id)
        statement.setLong(2, order.tableId)
        statement.setLong(3, order.employeeId)
        statement.setBigDecimal(4, order.sum)
        statement.setTimestamp(5, order.date)
        statement.execute()
    }

    override suspend fun Add(order: Order): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall("{? = call add_order(?, ?, ?, ?)}")
        statement.setLong(2, order.tableId)
        statement.setLong(3, order.employeeId)
        statement.setBigDecimal(4, order.sum)
        statement.setTimestamp(5, order.date)
        statement.execute()
        return@withContext statement.getLong(1)
    }
}