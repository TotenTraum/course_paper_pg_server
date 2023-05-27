package com.traum.factories

import java.sql.Connection
import java.sql.DriverManager

class ConnectionFactoryImpl(var url: String) : IConnectionFactory {
    override fun createConnection(user: String, password: String): Connection {
        Class.forName("org.postgresql.Driver")
        return DriverManager.getConnection(url, user, password)
    }
}