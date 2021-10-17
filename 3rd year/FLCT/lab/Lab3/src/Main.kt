fun main() {
    val table = SymbolTable()

    table.add("test")

    println(table.find("test"))
}