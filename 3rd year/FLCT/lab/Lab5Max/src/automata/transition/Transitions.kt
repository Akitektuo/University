package automata.transition

class Transitions {
    private val map = HashMap<Pair<String, String>, ArrayList<Transition>>()

    var isDeterministic = true
        private set

    fun add(transition: Transition) {
        val key = transition.source to transition.value
        if (map.contains(key)) {
            isDeterministic = false
            map[key]?.add(transition)
        } else {
            map[key] = arrayListOf(transition)
        }
    }

    operator fun get(source: String, value: String) = map[source to value]

    override fun toString() = map.entries.joinToString("\n") { it.value.toString() }

    fun validate(states: ArrayList<String>, alphabet: ArrayList<String>) =
        map.values.forEach { transitions -> transitions.forEach { it.validate(states, alphabet) } }
}