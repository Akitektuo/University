from action import Action
from exceptions.invalid_command_error import InvalidCommandError

class MenuUI:
    TYPE = "menu_ui"

    def __init__(self):
        self.type = MenuUI.TYPE
        self.actions = {
            "0": Action(Action.HELP),
            "1": Action(Action.ADD),
            "2": Action(Action.REMOVE),
            "3": Action(Action.UPDATE),
            "4": Action(Action.LIST),
            "5": Action(Action.GRADE),
            "6": Action(Action.SEARCH),
            "7": Action(Action.SEE_FAILING_STUDENTS),
            "8": Action(Action.SEE_BEST_STUDENTS),
            "9": Action(Action.SEE_GRADES),
            "10": Action(Action.UNDO),
            "11": Action(Action.HELP),
            "12": Action(Action.UI),
            "13": Action(Action.TEST),
            "14": Action(Action.EXIT)
        }

    def show_help(self):
        print("\nWelcome to Student Register! To see the menu at any time, type 0.")
        print("1. Add a student or discipline")
        print("2. Remove a student or discipline by ID")
        print("3. Update a student or discipline")
        print("4. List all the students and/or disciplines")
        print("5. Grade students at a given discipline")
        print("6. Seacrh for disciplines and students")
        print("7. Show all students failing at one or more disciplines")
        print("8. Show the students with the best school situation")
        print("9. Show all disciplines with grades")
        print("10. Undo the previous operation")
        print("11. Show the help menu")
        print("12. Change to graphical UI")
        print("13. Run all tests and stop the program")
        print("14. Exit")

    def get_action(self):
        command = input("\n> ")
        if command in self.actions:
            return self.actions[command]
        raise InvalidCommandError("Invalid command, enter 0 if you want to see the menu")

    def switch_ui_prefernece(self):
        command = input("Do you wish to switch to the graphical UI? (1/0)\n> ")
        if (command.startswith('1')):
            print("Starting GUI")
            return True
        return False

    def handle_error(self, error):
        print("Error: " + str(error))

    def print_result(self, result):
        if result == "" or result == None:
            print("Operation successful")
            return
        print(result)
