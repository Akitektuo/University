package com.akitektuo.assignment8.game

import java.util.*

class Board(private val size: Int = 10) {
    private val random = Random(System.nanoTime())

    private val remainingShips = ArrayList<Int>()
    private val data = mutableListOf<MutableList<State>>()

    fun generate() {
        initializeData()

        while (remainingShips.isNotEmpty()) {
            val i = random.nextInt(size)
            val j = random.nextInt(size)
            val direction = random.nextInt(4)
            val boatSize = remainingShips.random()

            if (!checkSpace(i, j, direction, boatSize)) {
                continue
            }

            remainingShips.remove(boatSize)
            placeBoat(i, j, direction, boatSize)
        }
    }

    private fun initializeData() {
        data.clear()
        for (i in 0 until size) {
            val row = mutableListOf<State>()
            for (j in 0 until size) {
                row += State.EMPTY
            }
            data += row
        }

        remainingShips.clear()
        remainingShips.addAll(listOf(5, 4, 3, 3, 2))
    }

    private fun checkSpace(i: Int, j: Int, direction: Int, boatSize: Int): Boolean {
        when (direction) {
            0 -> (0 until boatSize).forEach { if (i - it < 0 || data[i - it][j] == State.BOAT) return false }
            1 -> (0 until boatSize).forEach { if (j + it >= size || data[i][j + it] == State.BOAT) return false }
            2 -> (0 until boatSize).forEach { if (i + it >= size || data[i + it][j] == State.BOAT) return false }
            3 -> (0 until boatSize).forEach { if (j - it < 0 || data[i][j - it] == State.BOAT) return false }
        }
        return true
    }

    private fun placeBoat(i: Int, j: Int, direction: Int, boatSize: Int) = when (direction) {
        0 -> (0 until boatSize).forEach { data[i - it][j] = State.BOAT }
        1 -> (0 until boatSize).forEach { data[i][j + it] = State.BOAT }
        2 -> (0 until boatSize).forEach { data[i + it][j] = State.BOAT }
        3 -> (0 until boatSize).forEach { data[i][j - it] = State.BOAT }
        else -> {
        }
    }

    fun print() {
        data.forEach { row ->
            row.forEach { print(it.symbol) }
            println()
        }
    }

    fun map(rowBlock: (String) -> String, elementBlock: (State, i: Int, j: Int) -> String) =
        data.mapIndexed { i, row ->
            rowBlock(row.mapIndexed { j, state -> elementBlock(state, i, j) }.joinToString(""))
        }.joinToString("")

    fun hit(i: Int, j: Int): Boolean {
        if (data[i][j] == State.BOAT) {
            data[i][j] = State.HIT
            return true
        }
        data[i][j] = State.MISS
        return false
    }
}