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
    suspend fun getAllByItemId(itemId: Long): List<PriceOfItem>

    /**
     * Метод получения цены по идентификатору цены
     * @param id идентификатор цены
     * @return Цена
     */
    suspend fun getById(id: Long): PriceOfItem

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