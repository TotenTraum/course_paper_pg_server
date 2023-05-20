package com.traum.repositories.interfaces

import com.traum.models.Measurement

/**
 * Интерфейс репозитория для мер измерений
 */
interface IMeasurementRepository {
    /**
     * Метод получения всех мер измерений
     * @return Список всех мер измерений
     */
    suspend fun getAll(): List<Measurement>

    /**
     * Метод получения меры измерений по идентификатору
     * @param id идентификатор меры измерений
     * @return меру измерения
     */
    suspend fun getById(id: Long): Measurement

    /**
     * Метод удаления меры измерения
     * @param id идентификатор меры измерения
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о мере измерения
     * @param measurement мера измерения
     */
    suspend fun update(measurement: Measurement)

    /**
     * Метод добавления данных о мере измерения
     * @param measurement мера измерения
     */
    suspend fun add(measurement: Measurement): Long
}