package com.traum.models

import java.math.BigDecimal
import java.sql.Timestamp

/**
 * Заказ
 * @property id Идентификатор строки в таблице
 * @property sum Общая сумма заказа
 * @property date Дата совершени заказа
 * @property tableId идентификатор стола
 * @property table стол
 * @property employeeId идентификатор сотрудника
 * @property employee Обслуживший сотрудник
 */
data class Order(
    val id: Long,
    var date: Timestamp,
    var sum: BigDecimal,
    var employeeId: Long,
    var tableId: Long
) {
    var table: Table? = null
    var employee: Employee? = null
    var elements: MutableList<ElementOfOrder>? = null
}
