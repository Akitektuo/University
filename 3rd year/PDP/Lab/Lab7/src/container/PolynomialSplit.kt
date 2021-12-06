package container

data class PolynomialSplit(
    val firstLow: Polynomial,
    val firstHigh: Polynomial,
    val secondLow: Polynomial,
    val secondHigh: Polynomial
)