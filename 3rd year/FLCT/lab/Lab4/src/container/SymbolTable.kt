package container

class SymbolTable(private var capacity: Int = 17, private val loadFactor: Float = .75f) {
    private var map = getTableOfCapacity()
    private var size = 0

    fun add(token: String): Pair<Int, Int> {
        find(token)?.let {
            return it
        }

        growIfLoadFactorExceeded()
        size++
        val hashKey = token.hash()
        val tokenPosition = map[hashKey].size
        map[hashKey].add(token)

        return hashKey to tokenPosition
    }

    fun find(token: String): Pair<Int, Int>? {
        val hashKey = token.hash()

        return map[hashKey].indexOf(token).let { if (it == -1) null else hashKey to it }
    }

    private fun growIfLoadFactorExceeded() {
        if (size.toFloat() / capacity < loadFactor) {
            return
        }
        val mapToRehash = map
        capacity *= 2
        map = getTableOfCapacity()
        mapToRehash.flatMap { it }.forEach { add(it) }
    }

    private fun getTableOfCapacity() = Array(capacity) { ArrayList<String>() }

    private fun String.hash() = this.codePoints().sum() % capacity

    override fun toString() = map.joinToString("\n") { it.toString() }
}