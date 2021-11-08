package ui

import java.util.*
import kotlin.collections.ArrayList

class ConsoleUI {
    private val menuOptions = ArrayList<MenuOption>()
    private var shouldRun = true
    private val scanner = Scanner(System.`in`)

    fun addMenuOption(key: Int, description: String, action: (Scanner) -> Unit): ConsoleUI {
        menuOptions.add(MenuOption(key, description, action))
        return this
    }

    fun start() {
        menuOptions.add(MenuOption(0, "0. Exit") { shouldRun = false })

        displayMenuOptions()

        run()
    }

    private fun displayMenuOptions() = println(menuOptions.joinToString("\n") { it.description })

    private fun run() {
        while (shouldRun) {
            val optionKey = scanner.nextInt()
            val option = menuOptions.firstOrNull { it.key == optionKey }

            if (option == null) {
                displayInvalidOption()
                continue
            }

            option.action(scanner)
        }
    }

    private fun displayInvalidOption() {
        println("Invalid option, these are the available options:")
        displayMenuOptions()
    }
}