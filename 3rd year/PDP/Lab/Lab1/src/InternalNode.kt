class InternalNode(index: Int) : Node(index) {
    private val dependencies = ArrayList<BaseNode>()

    fun addDependency(node: BaseNode?) = node?.let { dependencies.add(it) }

    override fun computeValue(): Int {
        value = dependencies.sumOf { it.computeValue() }

        return value
    }

    override val isConsistent = value == dependencies.sumOf { it.getValueCopy() }
}