package com.traum.models

import java.math.BigDecimal

/**
 * Элемент заказа
 * @property id Идентификатор строки в таблице
 * @property sum Общая сумма элемента заказа
 * @property amount Количество
 * @property orderId идентификатор заказа
 * @property order Заказ
 * @property itemId идентификатор товара
 * @property item Товар
 */
class ElementOfOrder {
    var id: Long = 0
    var amount: Int = 0
    var sum: BigDecimal = BigDecimal(0)
    var orderId: Long = 0
    var itemId: Long = 0
    var priceOfItemId: Long = 0
    var order: Order? = null
    var item: Item? = null
}
