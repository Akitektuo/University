import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

val numberOfThreads = computeNumberOfThreads()

fun concurrently(block: ((Runnable) -> Unit) -> Unit): Unit = with(Executors.newFixedThreadPool(numberOfThreads)) {
    block(this::execute)

    shutdown()
    awaitTermination(10, TimeUnit.SECONDS)
}

private fun computeNumberOfThreads(): Int {
    var availableThreads = NUMBER_OF_THREADS

    if (NUMBER_OF_THREADS < 1) {
        availableThreads = Runtime.getRuntime().availableProcessors()
        println("\n'NUMBER_OF_THREADS' set to 0, found $availableThreads threads on the machine.\n")
    }

    return availableThreads
}

