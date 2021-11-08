import automata.FiniteAutomata
import container.ProgramInternalForm
import container.SymbolTable
import container.TokenList
import token.ConstantToken
import token.IdentifierToken
import token.IntConstToken
import token.UnknownTokenException

class Scanner {
    private val symbolTable = SymbolTable()
    private val pif = ProgramInternalForm()

    private val faIdentifier = FiniteAutomata("./assets/input/fa-identifier.in")
    private val faConstant = FiniteAutomata("./assets/input/fa-constant.in")

    fun validateTokens() = TokenList.fromFile("./assets/input/token.in")

    fun scan(fileName: String) {
        readFileIndexed(fileName) { index, line ->
            try {
                TokenList.fromLine(line.trim()).forEach {
                    when (it) {
                        is IdentifierToken -> {
                            if (!faIdentifier.isAccepted(it.toString()))
                                throw UnknownTokenException("Invalid identifier token '$it'")
                            pif.add("identifier", symbolTable.add(it.toString()))
                        }
                        is ConstantToken -> {
                            if (it is IntConstToken && !faConstant.isAccepted(it.toString()))
                                throw UnknownTokenException("Invalid integer constant token '$it'")
                            pif.add("constant", symbolTable.add(it.toString()))
                        }
                        else -> pif.add(it.toString(), null)
                    }
                }
            } catch (exception: UnknownTokenException) {
                error("Lexical error in '$fileName' on line $index! $exception")
            }
        }

        println("Program has been tokenized successfully!")
    }

    fun writeContainersToFiles() {
        pif.write()
        writeFile("./assets/output/ST.out") { write ->
            write(symbolTable.toString())
        }
    }
}