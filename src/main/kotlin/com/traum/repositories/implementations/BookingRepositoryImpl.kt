package com.traum.repositories.implementations

import com.traum.models.Booking
import com.traum.repositories.interfaces.IBookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.postgresql.util.PGobject
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types

@Suppress("Unused")
class BookingRepositoryImpl(private val connection: Connection) : IBookingRepository {
    companion object {
        private const val SELECT_BOOKING_BY_ID =
            """select "Id", "TableId", "ContactData", "Start", "End", "IsCanceled" from "Bookings" where "Id" = ?"""
        private const val SELECT_BOOKING =
            """select "Id", "TableId", "ContactData", "Start", "End", "IsCanceled" from "Bookings" """
        private const val INSERT_BOOKING = "{? = call add_booking(?, ?, ?)}"
        private const val DELETE_BOOKING = "call delete_booking(?)"
    }

    override suspend fun getAll(): List<Booking> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_BOOKING)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Booking>()
        while (resultSet.next())
            result.add(resultSet.toBooking())
        return@withContext result
    }

    override suspend fun getById(id: Long): Booking = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_BOOKING_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toBooking()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_BOOKING)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun add(booking: Booking): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_BOOKING)
        statement.registerOutParameter(1, Types.BIGINT)
        val obj = PGobject()
        obj.type = "jsonb"
        obj.value = Json.encodeToString(booking.contactData)
        statement.setLong(2, booking.tableId)
        statement.setObject(3, obj)
        statement.setTimestamp(4, booking.start)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toBooking(): Booking {
        val booking = Booking()
        booking.id = this.getLong("Id")
        booking.contactData = this.getString("ContactData")
        booking.tableId = this.getLong("TableId")
        booking.start = this.getTimestamp("Start")
        booking.end = this.getTimestamp("End")
        booking.isCanceled = this.getBoolean("IsCanceled")
        return booking
    }
}