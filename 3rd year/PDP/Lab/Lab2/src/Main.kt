import kotlin.math.min

fun main() {
    val vector1 = listOf(1, 2, 3, 4)
    val vector2 = listOf(5, 6, 7, 8)
    val range = 0 until min(vector1.size, vector2.size)

    val notifier = Notifier<Int>()

    val producer = Thread {
        range.forEach { notifier.notify(vector1[it] * vector2[it]) }
    }

    val consumer = Thread {
        val sum = range.sumOf { notifier.waitForData() }

        println("The sum is $sum")
    }

    producer.start()
    consumer.start()
}