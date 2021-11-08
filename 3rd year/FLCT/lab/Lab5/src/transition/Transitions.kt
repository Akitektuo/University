package transition

import java.util.ArrayList

class Transitions {
    private val map = HashMap<Pair<String, String>, Transition>()

    var isDeterministic = true
        private set

    fun add(transition: Transition) {
        val key = transition.source to transition.value
        if (map.contains(key)) {
            isDeterministic = false
        } else {
            map[key] = transition
        }
    }

    operator fun get(source: String, value: String) = map[source to value]

    override fun toString() = map.entries.joinToString("\n") { it.value.toString() }

    fun validate(states: ArrayList<String>, alphabet: ArrayList<String>) {
        map.values.forEach { it.validate(states, alphabet) }
    }
}