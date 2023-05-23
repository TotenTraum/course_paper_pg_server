package com.traum.repositories.interfaces

import com.traum.models.MeasureOfItem

/**
 * Интерфейс репозитория для мер измерений на товаре
 */
interface IMeasureOfItemRepository {
    /**
     * Метод получения всех мер измерений на товаре
     * @param itemId идентификатор товара
     * @return Список всех мер измерений на товаре
     */
    suspend fun getAll(itemId: Long): List<MeasureOfItem>

    /**
     * Метод получения меры измерений на товаре по идентификатору
     * @param id идентификатор меры измерения на товаре
     * @return мера измерения на товаре
     */
    suspend fun getById(id: Long): MeasureOfItem

    /**
     * Метод удаления меры измерения на товаре
     * @param id идентификатор меры измерения на товаре
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о мере измерения на товаре
     * @param measureOfItem мера измерения на товаре
     */
    suspend fun update(measureOfItem: MeasureOfItem)

    /**
     * Метод добавления данных о мере измерения на товаре
     * @param measureOfItem мера измерения на товаре
     */
    suspend fun add(measureOfItem: MeasureOfItem): Long
}