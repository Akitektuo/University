from action import Action
from exceptions import InvalidCommandError
from validator import is_type_valid
from validator import validate_existing_discipline_id

class MenuUI:
    TYPE = "menu_ui"

    def __init__(self):
        self.type = MenuUI.TYPE

    def show_help(self):
        print("\nWelcome to Student Register! To see the menu at any time, type 0.")
        print("1. Add a student or discipline")
        print("2. Remove a student or discipline by ID")
        print("3. Update a student or discipline")
        print("4. List all the students and disciplines")
        print("5. Grade students at a given discipline")
        print("6. Seacrh for disciplines and students")
        print("7. Show all students failing at one or more disciplines")
        print("8. Show the students with the best school situation")
        print("9. Show all disciplines with grades")
        print("10. Undo the previous operation")
        print("11. Redo the previous undo")
        print("12. Change to graphical UI")
        print("13. Run all tests")
        print("14. Exit")

    def get_add_params(self):
        atype = input("What do you want to add? (student/discipline): ")
        while not is_type_valid(atype):
            print("Invalid type, try again...\n")
            atype = input("What do you want to add? (student/discipline): ")
        aid = input("Give an unique ID: ")
        name = input("Give name: ")
        return [atype, aid, name]

    def get_remove_params(self):
        atype = input("What do you want to remove? (student/discipline): ")
        while not is_type_valid(atype):
            print("Invalid type, try again...\n")
            atype = input("What do you want to remove? (student/discipline): ")
        aid = input("Give an existing ID: ")
        return [atype, aid]

    def get_update_params(self):
        atype = input("What do you want to update? (student/discipline): ")
        while not is_type_valid(atype):
            print("Invalid type, try again...\n")
            atype = input("What do you want to update? (student/discipline): ")
        aid = input("Give an existing ID: ")
        name = input("Give new name: ")
        return [atype, aid, name]

    def get_grade_params(self):
        sid = input("Grade student (ID): ")
        did = input("At discipline (ID): ")
        grade = input("With (grade): ")
        return [sid, did, grade]

    def get_search_params(self):
        keyword = input("Give keyword: ")
        return [keyword]

    def get_action(self):
        command = input("\n> ")
        if command == "0":
            return Action(Action.HELP)
        if command == "1":
            return Action(Action.ADD, self.get_add_params())
        if command == "2":
            return Action(Action.REMOVE, self.get_remove_params())
        if command == "3":
            return Action(Action.UPDATE, self.get_update_params())
        if command == "4":
            return Action(Action.LIST)
        if command == "5":
            return Action(Action.GRADE, self.get_grade_params())
        if command == "6":
            return Action(Action.SEARCH, self.get_search_params())
        if command == "7":
            return Action(Action.SEE_FAILING_STUDENTS)
        if command == "8":
            return Action(Action.SEE_BEST_STUDENTS)
        if command == "9":
            return Action(Action.SEE_GRADES)
        if command == "10":
            return Action(Action.UNDO)
        if command == "11":
            return Action(Action.REDO)
        if command == "12":
            return Action(Action.UI)
        if command == "13":
            return Action(Action.TEST)
        if command == "14":
            return Action(Action.EXIT)
        raise InvalidCommandError("Invalid command, enter 0 if you want to see the menu")

    def switch_ui_prefernece(self):
        command = input("Do you wish to switch to the graphical UI? (1/0)\n> ")
        if (command.startswith('1')):
            print("Starting GUI")
            return True
        self.show_help()
        return False

    def handle_error(self, error):
        print("Error: " + str(error))

    def print_result(self, result):
        if result == "" or result == None:
            print("Operation successful")
            return
        print(result)
