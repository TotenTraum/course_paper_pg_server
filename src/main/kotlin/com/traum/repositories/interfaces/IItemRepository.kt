package com.traum.repositories.interfaces

import com.traum.models.Item

/**
 * Интерфейс репозитория для товаров
 */
interface IItemRepository {
    /**
     * Метод получения всех товаров
     * @return Список всех товаров
     */
    suspend fun getAll(): List<Item>

    /**
     * Метод получения товара по идентификатору
     * @param id идентификатор товара
     * @return товар
     */
    suspend fun getById(id: Long): Item

    /**
     * Метод удаления товара
     * @param id идентификатор товара
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о товара
     * @param item товар
     */
    suspend fun update(item: Item)

    /**
     * Метод добавления данных о товара
     * @param item товар
     */
    suspend fun add(item: Item): Long
}