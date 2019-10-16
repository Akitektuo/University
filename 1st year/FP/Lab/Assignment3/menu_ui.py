from __future__ import absolute_import
import re
import constants
from action import Action

class MenuUI:
    
    def __init__(self):
        self.TYPE = constants.TYPE_MENU

    def is_prefered(self):
        print("\nDo you want to use menu based UI? (1/0)")
        user_input = input("> ")
        return re.search("1+", user_input)

    def show_help(self):
        print("\nWelcome to Family Expenses! To see the menu at any time, type 0.")
        print("1. Add to the current day an expense")
        print("2. Insert to a day an expense")
        print("3. Remove all expenses from a day")
        print("4. Remove all expenses between two days")
        print("5. Remove all the expenses from a category")
        print("6. List all the expenses")
        print("7. List all the expenses from a category")
        print("8. List all the expenses from a category with a condition")
        print("9. Sum up all the expenses from a category")
        print("10. Print the maximm expense in a day")
        print("11. Print a day's expenses in ascending order")
        print("12. Print expenses from a category in ascending order")
        print("13. Show only the expenses from a category")
        print("14. Show only the expenses from a category with a condition")
        print("15. Undo prevoius change in the list")
        print("16. Change UI")
        print("17. Exit")

    def get_action(self):
        command = input("> ")
        if command == "0":
            return Action(constants.ACTION_HELP)
        elif command == "16":
            return Action(constants.ACTION_CHANGE_UI)
        elif command == "17":
            return Action(constants.ACTION_EXIT)
        return Action(constants.ACTION_ERROR)

    def on_invalid_command(self):
        print("Invalid command, enter 0 if you want to see the menu")