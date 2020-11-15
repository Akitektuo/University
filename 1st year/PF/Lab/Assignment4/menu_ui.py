from __future__ import absolute_import
import re
import constants
from action import Action

class MenuUI:
    
    def __init__(self):
        self.TYPE = constants.TYPE_MENU

    def show_help(self):
        print("\nWelcome to Expenses! To see the menu at any time, type 0.")
        print("1. Add a new expense")
        print("2. Show the list of expenses")
        print("3. Keep only the expenses above a value")
        print("4. Undo prevoius change in the list")
        print("5. Exit")

    def get_add_params(self):
        params = []
        params.append(input("Give day: "))
        params.append(input("Give cost: "))
        params.append(input("Give category: "))
        return params

    def get_filter_above_params(self):
        params = []
        params.append(input("Give value: "))
        return params

    def get_action(self):
        command = input("\n> ")
        if command == "0":
            return Action(constants.ACTION_HELP)
        if command == "1":
            params = self.get_add_params()
            return Action(constants.ACTION_ADD, params)
        if command == "2":
            return Action(constants.ACTION_LIST_ALL)
        if command == "3":
            params = self.get_filter_above_params()
            return Action(constants.ACTION_FILTER_ABOVE, params)
        if command == "4":
            return Action(constants.ACTION_UNDO)
        if command == "5":
            return Action(constants.ACTION_EXIT)
        return Action(constants.ACTION_ERROR)

    def on_invalid_command(self):
        print("Invalid command, enter 0 if you want to see the menu")

    def handle_error(self, error):
        print("Error: " + str(error))

    def print_result(self, result):
        if result == "" or result == None:
            print("Operation successful")
            return
        print(result)