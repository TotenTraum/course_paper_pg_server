package com.traum.repositories.implementations

import com.traum.repositories.interfaces.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection

class AuthRepositoryImpl(private val connection: Connection) : IAuthRepository {
    companion object {
        private const val SELECT_GROUPS =
            """select * from current_group() """
    }

    override suspend fun getCurrentGroup(): List<String> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_GROUPS)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<String>()
        while (resultSet.next())
            result.add(resultSet.getString(1))
        return@withContext result
    }
}