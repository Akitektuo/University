package ui

import java.util.*

data class MenuOption(val key: Int, val description: String, val action: (Scanner) -> Unit)