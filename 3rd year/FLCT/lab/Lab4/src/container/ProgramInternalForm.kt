package container

import writeFile

class ProgramInternalForm {
    private val values = ArrayList<Pair<String, Pair<Int, Int>?>>()

    fun add(token: String, position: Pair<Int, Int>?) {
        values.add(token to position)
    }

    fun write(fileName: String = "./assets/output/PIF.out") = writeFile(fileName) { write ->
        values.forEach { write("$it\n") }
    }
}