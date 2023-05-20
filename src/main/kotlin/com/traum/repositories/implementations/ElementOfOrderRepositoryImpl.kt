package com.traum.repositories.implementations

import com.traum.models.ElementOfOrder
import com.traum.repositories.interfaces.IElementOfOrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("unused")
class ElementOfOrderRepositoryImpl(private val connection: Connection) : IElementOfOrderRepository {
    companion object {
        private const val SELECT_ELEM_OF_ORDER_BY_ID =
            """select "Id", "OrderId", "ItemId", "priceOfItemId", "Sum", "Amount" from "ElementsOfOrder" where "Id" = ?"""
        private const val SELECT_ELEM_OF_ORDER =
            """select "Id", "OrderId", "ItemId", "priceOfItemId", "Sum", "Amount" from "ElementsOfOrder""""
        private const val INSERT_ELEM_OF_ORDER = "{? = call add_elem_order(?, ?, ?)}"
        private const val UPDATE_ELEM_OF_ORDER = "{call update_elem_order(?, ?, ?, ?)}"
        private const val DELETE_ELEM_OF_ORDER = "{call delete_elem_order(?, ?)}"
    }

    override suspend fun getAll(orderId: Long): List<ElementOfOrder> = withContext(Dispatchers.IO)
    {
        val statement =
            connection.prepareStatement(SELECT_ELEM_OF_ORDER)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<ElementOfOrder>()
        while (resultSet.next())
            result.add(resultSet.toElementOfOrder())
        return@withContext result
    }

    override suspend fun getById(orderId: Long, id: Long): ElementOfOrder = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_ELEM_OF_ORDER_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toElementOfOrder()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(orderId: Long, id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(DELETE_ELEM_OF_ORDER)
        statement.setLong(1, id)
        statement.setLong(2, orderId)
        statement.execute()
    }

    override suspend fun update(orderId: Long, elementOfOrder: ElementOfOrder): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(UPDATE_ELEM_OF_ORDER)
        statement.setLong(1, elementOfOrder.id)
        statement.setLong(2, elementOfOrder.orderId)
        statement.setLong(3, elementOfOrder.itemId)
        statement.setInt(4, elementOfOrder.amount)
        statement.execute()
    }

    override suspend fun add(orderId: Long, elementOfOrder: ElementOfOrder): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_ELEM_OF_ORDER)
        statement.setLong(2, elementOfOrder.orderId)
        statement.setLong(3, elementOfOrder.itemId)
        statement.setInt(4, elementOfOrder.amount)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toElementOfOrder(): ElementOfOrder {
        val order = ElementOfOrder()
        order.id = this.getLong("\"Id\"")
        order.itemId = this.getLong("\"ItemId\"")
        order.amount = this.getInt("\"Amount\"")
        order.sum = this.getBigDecimal("\"Sum\"")
        order.orderId = this.getLong("\"OrderId\"")
        return order
    }
}