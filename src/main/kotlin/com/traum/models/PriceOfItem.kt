package com.traum.models

import com.traum.currentTimestamp
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
class PriceOfItem {
    var id: Long = 0
    var price: BigDecimal = BigDecimal(0)
    var dateOfChange: Timestamp = currentTimestamp()
    var itemId: Long = 0
    var item: Item? = null
}
