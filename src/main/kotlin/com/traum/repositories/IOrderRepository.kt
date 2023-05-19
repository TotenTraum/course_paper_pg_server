package com.traum.repositories

import com.traum.models.Order

/**
 * Интерфейс репозитория для заказов
 */
interface IOrderRepository {
    /**
     * Метод получения всех заказов
     * @return Список всех заказов
     */
    suspend fun GetAll(): List<Order>

    /**
     * Метод получения заказа по идентификатору
     * @param id идентификатор заказа
     * @return Заказ
     */
    suspend fun GetById(id: Long): Order

    /**
     * Метод удаления заказа
     * @param id идентификатор заказа
     */
    suspend fun Delete(id: Long)

    /**
     * Метод обновления данных о заказе
     * @param order заказ
     */
    suspend fun Update(order: Order)

    /**
     * Метод добавления данных о заказе
     * @param order заказ
     */
    suspend fun Add(order: Order): Long
}