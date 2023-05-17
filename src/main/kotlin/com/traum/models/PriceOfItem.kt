package com.traum.models

import java.math.BigDecimal
import java.sql.Timestamp

/**
 * Цена товара
 * @property id Идентификатор строки в таблице
 * @property price Цена товара
 * @property dateOfChange Дата изменения цены на товар
 * @property itemId идентификатор товара
 * @property item Товар
 */
data class PriceOfItem(
    val id: Long,
    var price: BigDecimal,
    var dateOfChange: Timestamp,
    var itemId: Long
) {
    var item: Item? = null
}
