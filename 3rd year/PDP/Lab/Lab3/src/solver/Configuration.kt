package solver

import domain.Matrix

data class Configuration(
    val firstMatrix: Matrix,
    val secondMatrix: Matrix,
    val result: Matrix,
    val numberOfThreads: Int
)
