package com.akitektuo.assignment8.database

import com.mysql.jdbc.Driver
import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager(private var sqlConnection: Connection? = null) {
    private val CONNECTION_URL = "jdbc:mysql://localhost:3306/ships"

    val connection get() = sqlConnection ?: connect()

    private fun connect(): Connection = if (sqlConnection != null) sqlConnection!! else {
        Class.forName(Driver::class.java.canonicalName)
        DriverManager.getConnection(CONNECTION_URL, "root", "").apply {
            sqlConnection = this
        }
    }

    inline fun <R> use(block: (Connection) -> R) = try {
        block(connection).apply {
            disconnect()
        }
    } catch(exception: Exception) {
        disconnect()
        throw exception
    }

    fun disconnect() {
        sqlConnection?.close()
        sqlConnection = null
    }
}

val databaseManager = DatabaseManager()