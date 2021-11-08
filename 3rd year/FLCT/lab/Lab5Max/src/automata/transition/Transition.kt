package automata.transition

import automata.InvalidFiniteAutomataException
import java.util.ArrayList

data class Transition(val position: Pair<String, String>, val value: String) {
    val source = position.first
    val destination = position.second

    override fun toString() = "$source -$value-> $destination"

    fun validate(states: ArrayList<String>, alphabet: ArrayList<String>) {
        if (!states.containsAll(position.toList()) || !alphabet.contains(value)) {
            throw InvalidFiniteAutomataException("The transition's (${toString()}) source or destination is not " +
                    "present in the state set or the symbol is not in the alphabet!")
        }
    }
}