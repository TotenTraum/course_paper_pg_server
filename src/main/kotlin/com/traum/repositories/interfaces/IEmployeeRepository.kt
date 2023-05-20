package com.traum.repositories.interfaces

import com.traum.models.Employee

/**
 * Интерфейс репозитория для сотрудников
 */
interface IEmployeeRepository {
    /**
     * Метод получения всех сотрудников
     * @return Список всех сотрудников
     */
    suspend fun getAll(): List<Employee>

    /**
     * Метод получения сотрудника по идентификатору
     * @param id идентификатор сотрудника
     * @return сотрудник
     */
    suspend fun getById(id: Long): Employee

    /**
     * Метод удаления сотрудника
     * @param id идентификатор сотрудника
     */
    suspend fun delete(id: Long)

    /**
     * Метод обновления данных о сотруднике
     * @param employee сотрудник
     */
    suspend fun update(employee: Employee)

    /**
     * Метод добавления данных о сотруднике
     * @param employee сотрудник
     */
    suspend fun add(employee: Employee): Long
}