import multiplication.BaseMultiplication
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun <T> BaseMultiplication.async(command: () -> T): Future<T> {
    val executor = Executors.newFixedThreadPool(getNumberOfThreads(result.degree))

    return executor.submit<T> { command() }
}

fun BaseMultiplication.runConcurrently(commands: List<() -> Unit>) {
    val executor = Executors.newFixedThreadPool(getNumberOfThreads(result.degree))

    commands.forEach { executor.execute(it) }

    executor.shutdown()
    executor.awaitTermination(30, TimeUnit.SECONDS)
}

fun getNumberOfThreads(maximum: Int): Int {
    var availableThreads = NUMBER_OF_THREADS

    if (NUMBER_OF_THREADS < 1) {
        availableThreads = Runtime.getRuntime().availableProcessors()
        //println("\n'NUMBER_OF_THREADS' set to 0, found $availableThreads threads on the machine.\n")
    }

    if (OPTIMIZE_THREADS && maximum < availableThreads) {
        availableThreads = maximum
        println("\nThread size reduced to $availableThreads\n")
    }

    return availableThreads
}
