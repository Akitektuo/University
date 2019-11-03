from action import *

class MenuUI:

    def show_help(self):
        print("\nWelcome to Student Register! To see the menu at any time, type 0.")
        print("11. Exit")

    def get_action(self):
        command = input("\n> ")
        if command == "0":
            return Action(ACTION_HELP)
        if command == "5":
            return Action(ACTION_EXIT)
        print("Invalid command, enter 0 if you want to see the menu")

    def handle_error(self, error):
        print("Error: " + str(error))

    def print_result(self, result):
        if result == "" or result == None:
            print("Operation successful")
            return
        print(result)
