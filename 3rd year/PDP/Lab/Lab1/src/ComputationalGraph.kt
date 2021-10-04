import java.io.File
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ComputationalGraph {
    private val mutex = ReentrantLock()
    private val nodes = HashMap<Int, Node>()
    private val dependants = HashMap<Node, ArrayList<Node>>()
    private var numberOfLeafNodes = 0
    private var numberOfDependencies = 0

    private fun lock() {
        mutex.lock()
        println("Locking graph")
    }

    private fun unlock() {
        mutex.unlock()
        println("Unlocking graph")
    }

    private fun depthFirstSearch(node: BaseNode, quantity: Int) {
        node.addToValue(quantity)
        dependants[node]?.forEach { depthFirstSearch(it, quantity) }
    }

    fun updateNode(node: Node, newValue: Int) {
        lock()
        depthFirstSearch(node, newValue - node.getValueCopy())
        unlock()
    }

    fun loadGraph(fileName: String) {
        val input = Scanner(File("src/$fileName"))

        numberOfLeafNodes = input.nextInt()
        for (i in 1..numberOfLeafNodes) {
            nodes[i] = Node(i, input.nextInt())
        }

        numberOfDependencies = input.nextInt()
        for (i in 1..numberOfDependencies) {
            val parent = input.nextInt()
            val child = input.nextInt()

            nodes.putIfAbsent(parent, InternalNode(parent))
            nodes.putIfAbsent(child, InternalNode(child))

            if (nodes[child] is InternalNode) {
                (nodes[child] as InternalNode).addDependency(nodes[parent])
            }
            nodes[child]?.let { dependants[nodes[parent]]?.add(it) }
        }
    }

    fun computeInitialValue() {
        nodes.forEach { (_, node) -> node.computeValue() }
    }

    fun checkConsistency() {
        assert(nodes.all { it.value.isConsistent })
        println("Nodes are consistent")
    }

    fun printValues() {
        nodes.forEach { println("${it.key}: ${it.value.getValueCopy()}") }
    }

    fun getRandomNode() = nodes.values.random()
}