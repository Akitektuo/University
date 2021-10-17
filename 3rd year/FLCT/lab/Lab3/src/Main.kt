fun main() {
    val table = SymbolTable()

    val tokens = listOf(
        "someVar",
        "\"string constant\"",
        "true",
        "false",
        "0",
        "36542",
        "-7654",
    )

    tokens.forEach { table.add(it) }

    tokens.forEach { println("Token '$it' stored at ${table.find(it)}") }
}