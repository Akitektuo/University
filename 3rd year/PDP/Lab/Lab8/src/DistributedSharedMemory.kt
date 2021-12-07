import mpi.MPI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

class DistributedSharedMemory {
    var a = 0
        private set
    var b = 1
        private set
    var c = 2
        private set

    val subscribers = ConcurrentHashMap(hashMapOf("a" to hashSetOf<Int>(), "b" to hashSetOf(), "c" to hashSetOf()))

    private val lock = ReentrantLock()

    fun close() = sendAll(CloseMessage())

    fun setVariable(variable: String, value: Int) = when (variable) {
        "a" -> a = value
        "b" -> b = value
        "c" -> c = value
        else -> error("Unknown variable '$variable'")
    }

    fun subscribeTo(variable: String) = getProcessRank().let {
        subscribers[variable]?.add(it)
        sendAll(SubscribeMessage(variable, it))
    }

    fun syncSubscription(variable: String, rank: Int) = subscribers[variable]?.add(rank)

    fun checkAndReplace(variable: String, oldValue: Int, newValue: Int) = when (variable to oldValue) {
        "a" to a -> updateVariable("a", newValue)
        "b" to b -> updateVariable("b", newValue)
        "c" to c -> updateVariable("c", newValue)
        else -> {}
    }

    private fun updateVariable(variable: String, value: Int) {
        lock.lock()
        setVariable(variable, value)
        val message = UpdateMessage(variable, value)
        sendToSubscribers(variable, message)
        lock.unlock()
    }

    private fun sendToSubscribers(variable: String, message: Message) = subscribers[variable]?.let { subscriberList ->
        0.until(MPI.COMM_WORLD.Size())
            .filter { getProcessRank() != it && it in subscriberList }
            .forEach { send(arrayOf(message), it) }
    }

    private fun sendAll(message: Message) = 0.until(MPI.COMM_WORLD.Size())
        .filter { getProcessRank() != it || message is CloseMessage }
        .forEach { send(arrayOf(message), it) }
}