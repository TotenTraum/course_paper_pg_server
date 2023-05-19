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
class MeasureOfItem {
    var id: Long = 0
    var amount: BigDecimal = BigDecimal(0)
    var itemId: Long = 0
    var measurementId: Long = 0
    var item: Item? = null
    var measurement: Measurement? = null
}
