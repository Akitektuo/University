package token

val INT_CONSTANT_REGEX = Regex("\\b(0|([+\\-]?[1-9]\\d*))\\b")
val BOOL_CONSTANT_REGEX = Regex("\\b(false|true)\\b")
val STRING_CONSTANT_REGEX = Regex("\"(\\\\\"|[^\"])*\"")

val IDENTIFIER_REGEX = Regex("[a-zA-Z]\\w*")

val OPERATOR_REGEX = Regex("==|=|\\+\\+|\\+=|\\+|--|-=|-|\\*=|\\*|/=|/|%=|%|!=|<=|<|>=|>|!|&=|\\|=|&|\\|")

val SEPARATOR_REGEX = Regex("[\\n()}{\\]\\[ \"]")

val RESERVED_WORDS_REGEX = Regex("\\b(int|bool|string|input|print|when|otherwise|in|while|each)\\b")

val TOKEN_REGEX = arrayListOf(
    RESERVED_WORDS_REGEX,
    BOOL_CONSTANT_REGEX,
    STRING_CONSTANT_REGEX,
    INT_CONSTANT_REGEX,
    IDENTIFIER_REGEX,
    OPERATOR_REGEX,
    SEPARATOR_REGEX
)