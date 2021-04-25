package com.akitektuo.assignment8.authentication

import com.akitektuo.assignment8.database.databaseManager
import com.akitektuo.assignment8.util.use

class CredentialsManager {
    companion object {
        fun authenticate(username: String, password: String) = databaseManager.use { connection ->
            val statement = connection.createStatement()

            statement.executeQuery(
                "SELECT COUNT(*) FROM users WHERE username = '$username' AND password = '$password'"
            ).use {
                it.next() && it.getInt(1) == 1
            }
        }

        fun register(username: String, password: String) = databaseManager.use { connection ->
            connection.createStatement().executeQuery(
                "SELECT COUNT(*) FROM users WHERE username = '$username'"
            ).use {
                if (it.next() && it.getInt(1) == 1) {
                    return false
                }
                connection.prepareStatement(
                    "INSERT INTO users(username, password) VALUES ('$username', '$password')"
                ).execute()
                true
            }
        }
    }
}