package com.traum.repositories.interfaces

import com.traum.models.PriceOfItem

/**
 * Интерфейс репозитория для цен на товар
 */
interface IPriceOfItemRepository {
    /**
     * Метод получения цен по идентификатору товара
     * @return Список всех цен
     */
    suspend fun getAllById(itemId: Long): List<PriceOfItem>

    /**
     * Метод получения последней цены по идентификатору товара
     * @param itemId идентификатор товара
     * @return Цена
     */
    suspend fun getSingleByIdOrderByTime(itemId: Long): PriceOfItem

    /**
     * Метод добавления новой цены на товар
     * @param priceOfItem цена
     */
    suspend fun add(priceOfItem: PriceOfItem): Long
}