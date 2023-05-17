package com.traum.models

/**
 * Сотрудник кафе
 * @property id Идентификатор строки в таблице
 * @property name ФИО сотрудника
 * @property phoneNumber номер телефона сотрудника
 */
data class Employee(val id: Long, var name: String, var phoneNumber: String)
