import container.TokenList

fun main() {
    val scanner = Scanner()

//    println(TokenList.fromLine("x = 1 + 2"))
    scanner.validateTokens()

    // mapl = my awesome programming language
    scanner.scan("./assets/input/p1err.mapl")

    scanner.writeContainersToFiles()
}