package com.traum.models

import java.sql.Timestamp

/**
 * Лог
 * @property id Идентификатор строки в таблице
 * @property source Источник создания лога
 * @property created Время создания лога
 * @property roleCreated Роль, при которой был создан лог
 */
data class Log(
    val id: Long,
    var source: String,
    var created: Timestamp,
    var roleCreated: String
)
