package com.traum.models

/**
 * Виды измерения
 * @property id Идентификатор строки в таблице
 * @property [name] Название измерения
 */
data class Measurement(val id: Long, var name: String)
