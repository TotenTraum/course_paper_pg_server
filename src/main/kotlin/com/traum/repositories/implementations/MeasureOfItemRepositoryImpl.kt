package com.traum.repositories.implementations

import com.traum.models.MeasureOfItem
import com.traum.repositories.interfaces.IMeasureOfItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("Unused")
class MeasureOfItemRepositoryImpl(private val connection: Connection) : IMeasureOfItemRepository {
    companion object {
        private const val SELECT_MEASURE_OF_ITEM_BY_ID =
            """select "Id", "MeasurementId", "ItemId", "Amount" from "MeasuresOfItem" where "Id" = ?"""
        private const val SELECT_MEASURE_OF_ITEM =
            """ select "Id", "MeasurementId", "ItemId", "Amount" from "MeasuresOfItem" """
        private const val INSERT_MEASURE_OF_ITEM = "{? = call add_measure_of_item(?, ?, ?)}"
        private const val UPDATE_MEASURE_OF_ITEM = "{call update_measure_of_item(?, ?, ?, ?)}"
        private const val DELETE_MEASURE_OF_ITEM = "{call delete_measure_of_item(?)}"
    }

    override suspend fun getAll(): List<MeasureOfItem> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_MEASURE_OF_ITEM)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<MeasureOfItem>()
        while (resultSet.next())
            result.add(resultSet.toMeasureOfItem())
        return@withContext result
    }

    override suspend fun getById(id: Long): MeasureOfItem = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_MEASURE_OF_ITEM_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toMeasureOfItem()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(DELETE_MEASURE_OF_ITEM)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(measureOfItem: MeasureOfItem): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(UPDATE_MEASURE_OF_ITEM)
        statement.setLong(1, measureOfItem.id)
        statement.setLong(2, measureOfItem.itemId)
        statement.setLong(3, measureOfItem.measurementId)
        statement.setBigDecimal(4, measureOfItem.amount)
        statement.execute()
    }

    override suspend fun add(measureOfItem: MeasureOfItem): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_MEASURE_OF_ITEM)
        statement.setLong(2, measureOfItem.itemId)
        statement.setLong(3, measureOfItem.measurementId)
        statement.setBigDecimal(4, measureOfItem.amount)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toMeasureOfItem(): MeasureOfItem {
        val measureOfItem = MeasureOfItem()
        measureOfItem.id = this.getLong("\"Id\"")
        measureOfItem.itemId = this.getLong("\"ItemId\"")
        measureOfItem.measurementId = this.getLong("\"MeasurementId\"")
        measureOfItem.amount = this.getBigDecimal("\"Amount\"")
        return measureOfItem
    }
}