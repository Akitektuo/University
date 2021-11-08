package token

class UnknownTokenException(token: String) : Exception("Received unknown token '$token'")