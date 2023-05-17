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
data class ElementOfOrder(
    val id: Long,
    var amount: Int,
    var sum: BigDecimal,
    var orderId: Long,
    var itemId: Long,
) {
    var order: Order? = null
    var item: Item? = null
}
