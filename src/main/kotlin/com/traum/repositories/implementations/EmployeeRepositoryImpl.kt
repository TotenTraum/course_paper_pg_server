package com.traum.repositories.implementations

import com.traum.models.Employee
import com.traum.repositories.interfaces.IEmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

@Suppress("Unused")
class EmployeeRepositoryImpl(private val connection: Connection) : IEmployeeRepository {
    companion object {
        private const val SELECT_EMPLOYEE_BY_ID =
            """select "Id", "Name", "PhoneNumber" from "Employee" where "Id" = ?"""
        private const val SELECT_EMPLOYEE =
            """select "Id", "Name", "PhoneNumber" from "Employee""""
        private const val INSERT_EMPLOYEE = "{? = call add_employee(?, ?)}"
        private const val UPDATE_EMPLOYEE = "{call update_employee(?, ?, ?)}"
        private const val DELETE_EMPLOYEE = "{call delete_employee(?)}"
    }

    override suspend fun getAll(): List<Employee> = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_EMPLOYEE)
        val resultSet = statement.executeQuery()
        val result = mutableListOf<Employee>()
        while (resultSet.next())
            result.add(resultSet.toEmployee())
        return@withContext result
    }

    override suspend fun getById(id: Long): Employee = withContext(Dispatchers.IO) {
        val statement =
            connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)
        statement.setLong(1, id)
        val resultSet = statement.executeQuery()
        if (resultSet.next())
            return@withContext resultSet.toEmployee()
        else
            throw Exception("Record not found")
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(DELETE_EMPLOYEE)
        statement.setLong(1, id)
        statement.execute()
    }

    override suspend fun update(employee: Employee): Unit = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(UPDATE_EMPLOYEE)
        statement.setLong(1, employee.id)
        statement.setString(2, employee.name)
        statement.setString(3, employee.phoneNumber)
        statement.execute()
    }

    override suspend fun add(employee: Employee): Long = withContext(Dispatchers.IO) {
        val statement = connection.prepareCall(INSERT_EMPLOYEE)
        statement.setString(2, employee.name)
        statement.setString(3, employee.phoneNumber)
        statement.execute()
        return@withContext statement.getLong(1)
    }

    private fun ResultSet.toEmployee(): Employee {
        val employee = Employee()
        employee.id = getLong("\"Id\"")
        employee.name = getString("\"Name\"")
        employee.phoneNumber = getString("\"PhoneNumber\"")
        return employee
    }
}