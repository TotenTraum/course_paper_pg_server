package com.traum.repositories.interfaces

import com.traum.models.Order

/**
 * Интерфейс репозитория для заказов
 */
interface IOrderRepository {
    /**
     * Метод получения всех заказов
     * @return Список всех заказов
     */
    suspend fun getAll(): List<Order>

    /**
     * Метод получения заказа по идентификатору
     * @param id идентификатор заказа
     * @return Заказ
     */
    suspend fun getById(id: Long): Order

    /**
     * Метод удаления заказа
     * @param id идентификатор заказа
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о заказе
     * @param order заказ
     */
    suspend fun update(order: Order)

    /**
     * Метод добавления данных о заказе
     * @param order заказ
     */
    suspend fun add(order: Order): Long
}