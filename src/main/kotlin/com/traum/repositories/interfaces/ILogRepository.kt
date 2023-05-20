package com.traum.repositories.interfaces

import com.traum.models.Log

/**
 * Интерфейс репозитория для логов
 */
interface ILogRepository {
    /**
     * Метод получения всех логов
     * @return Список всех логов
     */
    suspend fun getAll(): List<Log>
}