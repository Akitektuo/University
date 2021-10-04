import java.util.concurrent.locks.ReentrantLock

abstract class BaseNode {
    private val mutex = ReentrantLock()

    abstract val index: Int
    abstract val isConsistent: Boolean

    abstract fun getValueCopy(): Int
    abstract fun addToValue(amount: Int)
    abstract fun computeValue(): Int

    protected fun lock() {
        mutex.lock()
        println("Locking $index")
    }

    protected fun unlock() {
        mutex.unlock()
        println("Unlocking $index")
    }
}