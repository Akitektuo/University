import ui.ConsoleUI

fun main() {
    val finiteAutomata = FiniteAutomata("./assets/input/FA.in")

    ConsoleUI()
        .addMenuOption(1, "1. Display set of states") { println(finiteAutomata.states) }
        .addMenuOption(2, "2. Display the alphabet") { println(finiteAutomata.alphabet) }
        .addMenuOption(3, "3. Display all transitions") { println(finiteAutomata.transitions) }
        .addMenuOption(4, "4. Display set of final states") { println(finiteAutomata.finalStates) }
        .addMenuOption(5, "5. Check if the finite automata is deterministic") {
            println(if (finiteAutomata.isDeterministic) "Is deterministic!" else "Is not deterministic!")
        }.addMenuOption(6, "6. Check a sequence from input") {
            it.nextLine()
            val sequence = it.nextLine()
            println(if (finiteAutomata.isAccepted(sequence)) "Accepted!" else "Rejected!")
        }.start()
}