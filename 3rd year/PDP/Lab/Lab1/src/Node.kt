open class Node(override var index: Int, protected var value: Int = 0) : BaseNode() {
    override val isConsistent = true

    override fun getValueCopy(): Int {
        lock()
        val valueCopy = value;
        unlock()

        return valueCopy
    }

    override fun addToValue(amount: Int) {
        lock()
        value += amount
        unlock()
    }

    override fun computeValue() = value
}