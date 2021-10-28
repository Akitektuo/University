fun main() {
    val scanner = Scanner()

    scanner.validateTokens()

    // mapl = my awesome programming language
    scanner.scan("./assets/input/p3.mapl")

    scanner.writeContainersToFiles()
}