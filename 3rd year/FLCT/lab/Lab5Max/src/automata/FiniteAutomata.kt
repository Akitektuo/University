package automata

import readFileIndexed
import automata.transition.Transition
import automata.transition.Transitions

class FiniteAutomata(private val fileName: String) {
    val states = ArrayList<String>()
    private var initialState = ""
    val finalStates = ArrayList<String>()
    val alphabet = ArrayList<String>()
    val transitions = Transitions()

    init {
        initialize()
    }

    fun isDeterministic() = transitions.isDeterministic

    fun isAccepted(sequence: String): Boolean {
        if (!isDeterministic())
            return false

        return isAcceptedRecursive(initialState, sequence, 0)
    }

    private fun isAcceptedRecursive(currentState: String, sequence: String, index: Int): Boolean {
        if (index >= sequence.length)
            return finalStates.contains(currentState)

        val symbol = sequence[index].toString()
        val transitions = this.transitions[currentState, symbol] ?: return false

        return transitions.any { isAcceptedRecursive(it.destination, sequence, index + 1) }
    }

    private fun initialize() {
        try {
            readProperties()
            validate()
        } catch (exception: InvalidFiniteAutomataException) {
            exception.printStackTrace()
        }
    }

    private fun readProperties() = readFileIndexed(fileName) { index, line ->
        when (index) {
            0 -> states.addAll(line.split(" "))
            1 -> initialState = line
            2 -> finalStates.addAll(line.split(" "))
            3 -> alphabet.addAll(line.split(" "))
            else -> readTransition(line)
        }
    }

    private fun readTransition(line: String) {
        val (source, destination, value) = line.split(" ")
        transitions.add(Transition(source to destination, value))
    }

    private fun validate() {
        if (!states.contains(initialState)) {
            throw InvalidFiniteAutomataException("The initial state is not present in the set of states!")
        }
        if (!states.containsAll(finalStates)) {
            throw InvalidFiniteAutomataException("One of the final states is not present in the set of states!")
        }
        transitions.validate(states, alphabet)
    }
}