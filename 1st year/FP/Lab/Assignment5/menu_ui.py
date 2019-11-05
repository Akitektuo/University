from action import Action
from exceptions.invalid_command_error import InvalidCommandError
from validator import is_type_valid

class MenuUI:
    TYPE = "menu_ui"

    def __init__(self):
        self.type = MenuUI.TYPE

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
        print("11. Change to graphical UI")
        print("12. Run all tests and stop the program")
        print("13. Exit")

    def get_add_params(self):
        atype = input("What do you want to add? (student/discipline): ")
        while not is_type_valid(atype):
            print("Invalid type, try again...\n")
            atype = input("What do you want to add? (student/discipline): ")
        aid = input("Give an unique ID: ")
        name = input("Give name: ")
        return [atype, aid, name]

    def get_action(self):
        command = input("\n> ")
        if command == "0":
            return Action(Action.HELP)
        if command == "1":
            return Action(Action.ADD, self.get_add_params())
        if command == "2":
            return Action(Action.REMOVE)
        if command == "3":
            return Action(Action.UPDATE)
        if command == "4":
            return Action(Action.LIST)
        if command == "5":
            return Action(Action.GRADE)
        if command == "6":
            return Action(Action.SEARCH)
        if command == "7":
            return Action(Action.SEE_FAILING_STUDENTS)
        if command == "8":
            return Action(Action.SEE_BEST_STUDENTS)
        if command == "9":
            return Action(Action.SEE_GRADES)
        if command == "10":
            return Action(Action.UNDO)
        if command == "11":
            return Action(Action.UI)
        if command == "12":
            return Action(Action.TEST)
        if command == "13":
            return Action(Action.EXIT)
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
