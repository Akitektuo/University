import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun readFileIndexed(
    fileName: String,
    operationBlock: (index: Int, line: String) -> Unit,
) = with(Scanner(File(fileName))) {
    var index = 0
    while (hasNextLine())
        operationBlock(index++, nextLine())
    close()
}

fun <T> async(command: () -> T): Future<T> {
    val executor = Executors.newCachedThreadPool()

    return executor.submit<T> { command() }
}