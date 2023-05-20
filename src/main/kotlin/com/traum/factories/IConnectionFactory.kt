package com.traum.factories

import java.sql.Connection

/**
 * Интерфейс, который создаёт экземпляр соединения
 */
interface IConnectionFactory {
    fun createConnection(user: String, password: String): Connection
}