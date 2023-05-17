package com.traum.models

import java.sql.Timestamp

/**
 * Таблица бронированных столов
 * @property id Идентификатор строки в таблице
 * @property contactData контактные данных посетителя
 * @property start cо скольки стол забронирован
 * @property end до скольки стол забронирован
 * @property tableId идентификатор стола
 * @property table стол
 */
data class Booking(
    val id: Long,
    var contactData: String,
    var start: Timestamp,
    var end: Timestamp,
    var tableId: Long
) {
    var table: Table? = null
}
