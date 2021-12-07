import mpi.MPI

fun main(args: Array<String>) {
    MPI.Init(args)
    val distributedSharedMemory = DistributedSharedMemory()

    when (getProcessRank()) {
        0 -> distributedSharedMemory.threadedListener {
            subscribeTo("a")
            subscribeTo("b")
            subscribeTo("c")

            checkAndReplace("a", 0, 111)
            checkAndReplace("c", 2, 333)
            checkAndReplace("b", 100, 222)

            close()
        }
        1 -> distributedSharedMemory.threadedListener {
            subscribeTo("a")
            subscribeTo("c")
        }
        2 -> distributedSharedMemory.threadedListener {
            subscribeTo("b")
            checkAndReplace("b", 1, 100)
        }
    }
    MPI.Finalize()
}