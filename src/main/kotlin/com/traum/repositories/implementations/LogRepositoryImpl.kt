package com.traum.repositories.implementations

import com.traum.models.Log
import com.traum.repositories.interfaces.ILogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("Unused")
class LogRepositoryImpl(private val connection: Connection) : ILogRepository {
    companion object {
        private const val SELECT_LOGS =
            """select "Id", "Source", "RoleCreated", "Created" from get_logs() """
    }

    override suspend fun getAll(): List<Log> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_LOGS)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Log>()
        while (resultSet.next())
            result.add(resultSet.toLog())
        return@withContext result
    }

    private fun ResultSet.toLog(): Log {
        val log = Log()
        log.id = this.getLong("Id")
        log.roleCreated = this.getString("RoleCreated")
        log.source = this.getString("Source")
        log.created = this.getTimestamp("Created")
        return log
    }
}