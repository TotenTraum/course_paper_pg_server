package com.traum.repositories.interfaces

import com.traum.models.Table

/**
 * Интерфейс репозитория для столов
 */
interface ITableRepository {
    /**
     * Метод получения всех столов
     * @return Список всех столов
     */
    suspend fun getAll(): List<Table>

    /**
     * Метод получения стола по идентификатору
     * @param id идентификатор стола
     * @return стол
     */
    suspend fun getById(id: Long): Table

    /**
     * Метод удаления стола
     * @param id идентификатор стола
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о столе
     * @param table стол
     */
    suspend fun update(table: Table)

    /**
     * Метод добавления данных о мере измерения
     * @param table стол
     */
    suspend fun add(table: Table): Long
}