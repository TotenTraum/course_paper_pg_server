package com.traum.repositories.implementations

import com.traum.models.Measurement
import com.traum.repositories.interfaces.IMeasurementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types

@Suppress("Unused")
class MeasurementRepositoryImpl(private val connection: Connection) : IMeasurementRepository {
    companion object {
        private const val SELECT_CATEGORY_BY_ID =
            """select "Id", "Name" from "Measurements" where "Id" = ?"""
        private const val SELECT_CATEGORY =
            """select "Id", "Name" from "Measurements" """
        private const val INSERT_CATEGORY = "{? = call add_measurement(?)}"
        private const val UPDATE_CATEGORY = "call update_measurement(?, ?)"
        private const val DELETE_CATEGORY = "call delete_measurement(?)"
    }

    override suspend fun getAll(): List<Measurement> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_CATEGORY)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Measurement>()
        while (resultSet.next())
            result.add(resultSet.toMeasurement())
        return@withContext result
    }

    override suspend fun getById(id: Long): Measurement = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_CATEGORY_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toMeasurement()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_CATEGORY)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(measurement: Measurement): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_CATEGORY)
        statement.setLong(1, measurement.id)
        statement.setString(2, measurement.name)
        statement.execute()
    }

    override suspend fun add(measurement: Measurement): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_CATEGORY)
        statement.registerOutParameter(1, Types.BIGINT)
        statement.setString(2, measurement.name)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toMeasurement(): Measurement {
        val measurement = Measurement()
        measurement.id = this.getLong("Id")
        measurement.name = this.getString("Name")
        return measurement
    }
}