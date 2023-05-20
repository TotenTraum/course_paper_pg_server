package com.traum.repositories.interfaces

import com.traum.models.ElementOfOrder

/**
 * Интерфейс репозитория для элементов заказа
 */
interface IElementOfOrderRepository {
    /**
     * Метод получения всех элементов заказа
     * @return Список всех элементов
     */
    suspend fun getAll(orderId: Long): List<ElementOfOrder>

    /**
     * Метод получения элементу по идентификатору
     * @param id идентификатор элемента
     * @return Элемент
     */
    suspend fun getById(orderId: Long, id: Long): ElementOfOrder

    /**
     * Метод удаления элемента
     * @param id идентификатор элемента
     */
    suspend fun delete(orderId: Long, id: Long)

    /**
     * Метод обновления данных о элементе заказа
     * @param elementOfOrder элемент заказа
     */
    suspend fun update(orderId: Long, elementOfOrder: ElementOfOrder)

    /**
     * Метод добавления данных о элементе заказа
     * @param elementOfOrder элемент заказа
     */
    suspend fun add(orderId: Long, elementOfOrder: ElementOfOrder): Long
}