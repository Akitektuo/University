import transition.Transition
import transition.Transitions

class FiniteAutomata(private val fileName: String) {
    val states = ArrayList<String>()
    private var initialState = ""
    val finalStates = ArrayList<String>()
    val alphabet = ArrayList<String>()
    val transitions = Transitions()

    val isDeterministic = transitions.isDeterministic

    init {
        initialize()
    }

    fun isAccepted(sequence: String): Boolean {
        if (!isDeterministic)
            return false

        var currentState = initialState

        sequence.forEach { symbolCharacter ->
            val symbol = symbolCharacter.toString()
            currentState = transitions[currentState, symbol]?.destination ?: return false
        }

        return finalStates.contains(currentState);
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