import java.io.File
import java.io.FileWriter
import java.util.Scanner

fun writeFile(
    fileName: String,
    operationBlock: (write: (String) -> Unit) -> Unit,
) = with(FileWriter(fileName)) {
    operationBlock(this::write)
    flush()
    close()
}

fun readFileIndexed(
    fileName: String,
    operationBlock: (index: Int, line: String) -> Unit,
) = with(Scanner(File(fileName))) {
    var index = 0
    while (hasNextLine())
        operationBlock(index++, nextLine())
    close()
}

fun readFile(
    fileName: String,
    operationBlock: (line: String) -> Unit,
) = readFileIndexed(fileName) { _, line -> operationBlock(line) }