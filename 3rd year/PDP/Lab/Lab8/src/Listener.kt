class Listener(private val distributedSharedMemory: DistributedSharedMemory) : Runnable {
    override fun run() {
        while (true) {
            val processRank = getProcessRank()
            println("Rank $processRank waiting...")

            val messages = arrayOfNulls<Message>(1)
            receive(messages)

            when (val message = messages[0] as Message) {
                is SubscribeMessage -> with(message) {
                    println("Rank $processRank received subscribe message: rank $rank subscribes to $variable")
                    distributedSharedMemory.syncSubscription(variable, rank)
                }
                is UpdateMessage -> with(message) {
                    println("Rank $processRank received update message: $variable -> $value")
                    distributedSharedMemory.setVariable(variable, value)
                }
                is CloseMessage -> return println("Rank $processRank stopped listening.")
                else -> error("Rank $processRank received unknown message: $messages")
            }

            writeAll()
        }
    }

    private fun writeAll() = with(distributedSharedMemory) {
        val builder = StringBuilder("\n*** Write all ***\n")
        builder.append("Rank ${getProcessRank()} -> a = $a, b = $b, c = $c\n")
        builder.append("Subscribers:\n")
        subscribers.forEach { (variable, subscriberSet) ->
            builder.append("$variable -> $subscriberSet\n")
        }
        builder.append("\n")
        println(builder)
    }
}