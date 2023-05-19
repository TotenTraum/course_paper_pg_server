package com.traum.models

import com.traum.currentTimestamp
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
class Order {
    var id: Long = 0
    var date: Timestamp = currentTimestamp()

    var sum: BigDecimal = BigDecimal(0)
    var employeeId: Long = 0
    var tableId: Long = 0
    var table: Table? = null
    var employee: Employee? = null
    var elements: MutableList<ElementOfOrder>? = null
}
