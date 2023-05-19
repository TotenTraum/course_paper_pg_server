package com.traum.models

import com.traum.currentTimestamp
import java.sql.Timestamp

/**
 * Лог
 * @property id Идентификатор строки в таблице
 * @property source Источник создания лога
 * @property created Время создания лога
 * @property roleCreated Роль, при которой был создан лог
 */
class Log {
    var id: Long = 0
    var source: String = ""
    var created: Timestamp = currentTimestamp()
    var roleCreated: String = ""
}
