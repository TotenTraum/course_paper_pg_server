package com.traum.models

/**
 * Товар
 * @property id Идентификатор строки в таблице
 * @property name Название товара
 * @property description Описание товара
 * @property isNotForSale Флаг, который указывает на то, что поступил в продажу товар или нет
 * @property categoryId идентификатор категории
 * @property category Категория
 * @property measures Список мер измерений
 */
data class Item(
    val id: Long,
    var name: String,
    var description: String,
    var isNotForSale: Boolean,
    var categoryId: Long
) {
    var category: Category? = null
    var measures: List<MeasureOfItem>? = null
}
