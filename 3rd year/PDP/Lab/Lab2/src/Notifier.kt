import java.util.concurrent.locks.ReentrantLock

class Notifier<T>(private val withLogs: Boolean = false) {
    private var data: T? = null
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    fun notify(data: T) {
        lock.lock()

        while (this.data != null) {
            condition.await()
        }

        this.data = data
        if (withLogs) {
            println("Notifying data: $data")
        }
        condition.signal()

        lock.unlock()
    }

    fun waitForData(): T {
        lock.lock()

        while (data == null) {
            condition.await()
        }

        val data = readData()
        if (withLogs) {
            println("Waited for data: $data")
        }
        condition.signal()

        lock.unlock()

        return data!!
    }

    private fun readData(): T? {
        val data = this.data

        this.data = null

        return data
    }
}