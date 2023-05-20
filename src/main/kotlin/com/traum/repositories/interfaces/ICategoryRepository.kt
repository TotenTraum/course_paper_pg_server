package com.traum.repositories.interfaces

import com.traum.models.Category

/**
 * Интерфейс репозитория для категорий
 */
interface ICategoryRepository {
    /**
     * Метод получения всех категорий
     * @return Список всех категорий
     */
    suspend fun getAll(): List<Category>

    /**
     * Метод получения категории по идентификатору
     * @param id идентификатор категории
     * @return Категорияя
     */
    suspend fun getById(id: Long): Category

    /**
     * Метод удаления категории
     * @param id идентификатор категории
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о категории
     * @param category категория
     */
    suspend fun update(category: Category)

    /**
     * Метод добавления данных о категорий
     * @param category категорий
     */
    suspend fun add(category: Category): Long
}