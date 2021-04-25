package com.akitektuo.assignment8.game

enum class State(val symbol: String, val cssClass: String) {
    EMPTY(" ", "state-empty"),
    MISS("*", "state-miss"),
    BOAT("#", "state-boat"),
    HIT("X", "state-hit")
}