package com.traum.repositories.interfaces

/**
 * Интерфейс репозитория авторизации
 */
interface IAuthRepository {
    /**
     * Возвращает текущую роль пользователя
     * @return роль пользователя
     */
    suspend fun getCurrentGroup(): List<String>
}