import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

const val numberOfInputNodes = 10000
const val numberOfConnections = 25000

fun main() {
    generateFile("test.txt")
}

fun generateFile(fileName: String) {
    val buffer = StringBuffer()

    buffer.append("$numberOfInputNodes\n")
    for (i in 0 until numberOfInputNodes) {
        buffer.append("${Random.nextInt(1 until numberOfInputNodes)} ")
    }

    buffer.append("\n$numberOfConnections\n")
    for (i in 0 until numberOfConnections) {
        val parentNode = Random.nextInt(1 until numberOfConnections)
        val childNode = Random.nextInt(numberOfInputNodes until numberOfConnections)

        buffer.append("$parentNode $childNode\n")
    }

    File(fileName).writeText(buffer.toString())
}