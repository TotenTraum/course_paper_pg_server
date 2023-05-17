package com.traum.models

import java.math.BigDecimal

/**
 * Мера товара в одном из измерений
 * @property id Идентификатор строки в таблице
 * @property amount Количество товара в данном измерении
 * @property itemId идентификатор товара
 * @property item Товар
 * @property measurementId идентификатор меры измерения
 * @property measurement Мера измерения
 */
data class MeasureOfItem(
    val id: Long,
    var amount: BigDecimal,
    var itemId: Long,
    var measurementId: Long
) {
    var item: Item? = null
    var measurement: Measurement? = null
}
