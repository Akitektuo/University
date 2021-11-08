package container

import readFile
import token.TOKEN_REGEX
import token.Token
import token.UnknownTokenException

class TokenList {
    companion object {
        fun fromFile(fileName: String) = TokenList().apply { read(fileName) }

        fun fromLine(line: String): TokenList {
            val tokens = TokenList()
            var remainingLine = line

            while (remainingLine.isNotBlank()) {
                val match = TOKEN_REGEX.mapNotNull { it.find(remainingLine) }
                    .firstOrNull { it.range.first == 0 }
                    ?: throw UnknownTokenException(remainingLine)

                tokens.add(match.value)
                remainingLine = remainingLine.removeRange(match.range)
            }

            tokens.add("\n")
            return tokens
        }
    }

    private val tokens = ArrayList<Token>()

    fun read(fileName: String) = readFile(fileName) {
        tokens.add(Token.categorize(when (it) {
            "space" -> " "
            "\\n" -> "\n"
            else -> it
        }))
    }

    fun add(token: String) = tokens.add(Token.categorize(token.trimToken()))

    fun forEach(action: (Token) -> Unit) = tokens.forEach(action)

    private fun String.trimToken() = when (this) {
        " " -> " "
        "\n" -> "\n"
        else -> trim()
    }

    override fun toString() = tokens.toString()
}