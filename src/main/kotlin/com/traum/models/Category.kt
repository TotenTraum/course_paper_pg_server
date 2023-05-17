package com.traum.models

/**
 * Категория товара
 * @property id Идентификатор строки в таблице
 * @property [name] Категория товара(Напитки, супы и т.д.)
 */
data class Category(val id: Long, var name: String)
