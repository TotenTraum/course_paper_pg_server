package com.traum.repositories.implementations

import com.traum.models.PriceOfItem
import com.traum.repositories.interfaces.IPriceOfItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("Unused")
class PriceOfItemRepositoryImpl(private val connection: Connection) : IPriceOfItemRepository {
    companion object {
        private const val SELECT_PRICE_BY_ID =
            """select "Id", "ItemId", "Price", "DateOfChange" from "PricesOfItem" where "ItemId" = ?"""
        private const val SELECT_PRICE_BY_ID_ORDERED =
            """select "Id", "ItemId", "Price", "DateOfChange" from "PricesOfItem" where "ItemId" = ? order by "DateOfChange" limit 1"""
        private const val INSERT_PRICE = "{? = call add_price_of_item(?, ?)}"
    }

    override suspend fun getAllById(itemId: Long): List<PriceOfItem> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_PRICE_BY_ID)
        statement.setLong(1, itemId)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<PriceOfItem>()
        while (resultSet.next())
            result.add(resultSet.toPrices())
        return@withContext result
    }

    override suspend fun getSingleByIdOrderByTime(itemId: Long): PriceOfItem = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_PRICE_BY_ID_ORDERED)
        statement.setLong(1, itemId)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toPrices()
        else
            throw Exception("Record not found")
    }

    override suspend fun add(priceOfItem: PriceOfItem): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_PRICE)
        statement.setLong(2, priceOfItem.itemId)
        statement.setBigDecimal(3, priceOfItem.price)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toPrices(): PriceOfItem {
        val priceOfItem = PriceOfItem()
        priceOfItem.id = this.getLong("\"Id\"")
        priceOfItem.itemId = this.getLong("\"ItemId\"")
        priceOfItem.dateOfChange = this.getTimestamp("\"DateOfChange\"")
        priceOfItem.price = this.getBigDecimal("\"Price\"")
        return priceOfItem
    }
}