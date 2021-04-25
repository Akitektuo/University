package com.akitektuo.assignment8.game

import com.akitektuo.assignment8.database.waitingQueue
import com.akitektuo.assignment8.util.SECONDS_3
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameHost(private var playerA: Player? = null, private var playerB: Player? = null) {
    val isReady get() = playerA?.isReady == true && playerB?.isReady == true

    fun checkSpace() {
        if (playerA == null || playerB == null) {
            waitingQueue.getNext()
        }
    }

    fun registerPlayer(username: String): Player? {
        if (playerA == null) {
            waitingQueue.get(username)
            return Player(username, true).apply { playerA = this }
        }
        if (playerB == null) {
            waitingQueue.get(username)
            return Player(username, false).apply { playerB = this }
        }
        return null
    }

    fun getPlayer(username: String) = when (username) {
        playerA?.username -> playerA
        playerB?.username -> playerB
        else -> null
    }

    fun getOtherPlayer(player: Player) = if (player == playerA) playerB else playerA

    fun quit(username: String) = GlobalScope.launch {
        delay(SECONDS_3)
        when (username) {
            playerA?.username -> playerA = null
            playerB?.username -> playerB = null
            else -> Unit
        }
    }
}

val gameHost = GameHost()