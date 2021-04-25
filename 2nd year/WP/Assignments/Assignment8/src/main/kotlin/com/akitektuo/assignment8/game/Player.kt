package com.akitektuo.assignment8.game

import com.akitektuo.assignment8.util.MINUTE_2

class Player(
    val username: String,
    var isTurn: Boolean,
    var isReady: Boolean = false,
    val board: Board = Board(),
    var time: Long = System.currentTimeMillis()
) {
    val isAfk get() = System.currentTimeMillis() - time > MINUTE_2
    val isLoss get() = health == 0
    var health = 17

    fun newBoard() = board.generate()

    fun resetTimer() {
        time = System.currentTimeMillis()
    }

    private fun getHit(i: Int, j: Int) = if (board.hit(i, j)) {
        health--
        true
    } else false

    fun fireAt(other: Player, i: Int, j: Int) = if (!isTurn) false else {
        other.getHit(i, j)
        isTurn = false
        other.isTurn = true
        true
    }

    fun mapBoard(rowBlock: (String) -> String, elementBlock: (State) -> String) = board.map(rowBlock) { state, _, _ ->
        elementBlock(state)
    }

    fun mapEnemyBoard(rowBlock: (String) -> String, elementBlock: (State, i: Int, j: Int) -> String) =
        board.map(rowBlock) { state, i, j ->
            if (state == State.BOAT) {
                elementBlock(State.EMPTY, i, j)
            } else {
                elementBlock(state, i, j)
            }
        }
}