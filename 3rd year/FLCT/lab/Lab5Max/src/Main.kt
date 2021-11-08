import automata.FiniteAutomata

fun main() {
    val scanner = Scanner()

    scanner.validateTokens()

//    println(FiniteAutomata("./assets/input/fa-constant.in").isDeterministic)
//    println(FiniteAutomata("./assets/input/fa-identifier.in").isDeterministic)

    // mapl = my awesome programming language
    scanner.scan("./assets/input/p3.mapl")

    scanner.writeContainersToFiles()
}