package com.akitektuo.assignment8.database

import com.akitektuo.assignment8.game.gameHost
import com.akitektuo.assignment8.util.MINUTE_1
import com.akitektuo.assignment8.util.SECONDS_30
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class UserCheck(val username: String, var time: Long = System.currentTimeMillis()) {
    var canPlay = false

    fun checkIn() {
        time = System.currentTimeMillis()
    }

    val expired get() = System.currentTimeMillis() - time > MINUTE_1
}

class WaitingQueue {
    private val queue = ArrayList<UserCheck>()

    init {
        GlobalScope.launch {
            while (true) {
                delay(MINUTE_1)
                cleanExpired()
            }
        }
    }

    fun addToQueue(username: String): Int {
        val userIndex = queue.indexOfFirst { it.username == username }

        if (userIndex < 0) {
            queue.add(UserCheck(username))
            gameHost.checkSpace()
            return queue.size
        }

        queue[userIndex].checkIn()
        if (queue[userIndex].canPlay) {
            queue.removeAt(userIndex)
            return 0
        }

        gameHost.checkSpace()
        return userIndex + 1
    }

    private suspend fun cleanExpired() = queue.removeIf { it.expired }

    fun getNext() {
        if (queue.isNotEmpty()) {
            queue.first().canPlay = true
        }
    }

    fun get(username: String) {
        queue.find { it.username == username }?.canPlay = true
    }
}

val waitingQueue = WaitingQueue()

