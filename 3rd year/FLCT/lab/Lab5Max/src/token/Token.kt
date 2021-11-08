package token

abstract class Token(private val value: String) {
    companion object {
        fun categorize(value: String): Token {
            fun <T : Token> Regex.returnClass(factory: (token: String) -> T) =
                if (matches(value)) factory(value) else null

            return RESERVED_WORDS_REGEX.returnClass(::ReservedWordToken) ?:
                BOOL_CONSTANT_REGEX.returnClass(::BoolConstToken) ?:
                STRING_CONSTANT_REGEX.returnClass(::StringConstToken) ?:
                INT_CONSTANT_REGEX.returnClass(::IntConstToken) ?:
                IDENTIFIER_REGEX.returnClass(::IdentifierToken) ?:
                OPERATOR_REGEX.returnClass(::OperatorToken) ?:
                SEPARATOR_REGEX.returnClass(::SeparatorToken) ?:
                throw UnknownTokenException(value)
        }
    }

    override fun toString() = value
}