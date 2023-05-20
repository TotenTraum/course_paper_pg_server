package com.traum.repositories.interfaces

import com.traum.models.Booking

/**
 * Интерфейс репозитория для бронирований
 */
interface IBookingRepository {
    /**
     * Метод получения всех бронирований
     * @return Список всех бронирований
     */
    suspend fun getAll(): List<Booking>

    /**
     * Метод получения бронирования по идентификатору
     * @param id идентификатор бронирования
     * @return бронирования
     */
    suspend fun getById(id: Long): Booking

    /**
     * Метод удаления бронирования
     * @param id идентификатор бронирования
     */
    suspend fun delete(id: Long)

    /**
     * Метод добавления данных о бронировании
     * @param booking бронирование
     */
    suspend fun add(booking: Booking): Long
}