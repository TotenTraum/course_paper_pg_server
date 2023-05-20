package com.traum.models

import com.traum.currentTimestamp
import java.sql.Timestamp

/**
 * Таблица бронированных столов
 * @property id Идентификатор строки в таблице
 * @property contactData контактные данных посетителя
 * @property start cо скольки стол забронирован
 * @property end до скольки стол забронирован
 * @property isCanceled Отменено бронирование
 * @property tableId идентификатор стола
 * @property table стол
 */
class Booking {
    var id: Long = 0
    var contactData: String = ""
    var start: Timestamp = currentTimestamp()
    var end: Timestamp = currentTimestamp()
    var isCanceled: Boolean = false
    var tableId: Long = 0
    var table: Table? = null
}
