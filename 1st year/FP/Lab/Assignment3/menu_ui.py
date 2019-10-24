from __future__ import absolute_import
import re
import constants
from action import Action

class MenuUI:
    
    def __init__(self):
        self.TYPE = constants.TYPE_MENU

    def switch(self):
        print("\nDo you want to use command based UI? (1/0)")
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
        print("13. Keep only the expenses from a category")
        print("14. Keep only the expenses from a category with a condition")
        print("15. Undo prevoius change in the list")
        print("16. Change UI")
        print("17. Exit")

    def get_add_params(self):
        params = []
        params.append(input("Give cost: "))
        params.append(input("Give category: "))
        return params

    def get_insert_params(self):
        params = []
        params.append(input("Give day: "))
        params.append(input("Give cost: "))
        params.append(input("Give category: "))
        return params

    def get_remove_day_params(self):
        params = []
        params.append(input("Give day: "))
        return params

    def get_remove_range_params(self):
        params = []
        params.append(input("Give start day: "))
        params.append("")
        params.append(input("Give end day: "))
        return params

    def get_remove_category_params(self):
        params = []
        params.append(input("Give category: "))
        return params

    def get_list_category_params(self):
        params = []
        params.append(input("Give category: "))
        return params

    def get_list_category_condition_params(self):
        params = []
        params.append(input("Give category: "))
        params.append(input("Give operator (< = >): "))
        params.append(input("Give value: "))
        return params

    def get_sum_category_params(self):
        params = []
        params.append(input("Give category: "))
        return params

    def get_max_day_params(self):
        params = []
        params.append(input("Give day: "))
        return params

    def get_sort_day_params(self):
        params = []
        params.append(input("Give day: "))
        return params

    def get_sort_category_params(self):
        params = []
        params.append(input("Give category: "))
        return params

    def get_filter_category_params(self):
        params = []
        params.append(input("Give category: "))
        return params

    def get_filter_category_condition_params(self):
        params = []
        params.append(input("Give category: "))
        params.append(input("Give operator (< = >): "))
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
            params = self.get_insert_params()
            return Action(constants.ACTION_INSERT, params)
        if command == "3":
            params = self.get_remove_day_params()
            return Action(constants.ACTION_REMOVE_DAY, params)
        if command == "4":
            params = self.get_remove_range_params()
            return Action(constants.ACTION_REMOVE_RANGE, params)
        if command == "5":
            params = self.get_remove_category_params()
            return Action(constants.ACTION_REMOVE_CATEGORY, params)
        if command == "6":
            return Action(constants.ACTION_LIST_ALL)
        if command == "7":
            params = self.get_list_category_params()
            return Action(constants.ACTION_LIST_CATEGORY, params)
        if command == "8":
            params = self.get_list_category_condition_params()
            return Action(constants.ACTION_LIST_CATEGORY_CONDITION, params)
        if command == "9":
            params = self.get_sum_category_params()
            return Action(constants.ACTION_SUM_CATEGORY, params)
        if command == "10":
            params = self.get_max_day_params()
            return Action(constants.ACTION_MAX_DAY, params)
        if command == "11":
            params = self.get_sort_day_params()
            return Action(constants.ACTION_SORT_DAY, params)
        if command == "12":
            params = self.get_sort_category_params()
            return Action(constants.ACTION_SORT_CATEGORY, params)
        if command == "13":
            params = self.get_filter_category_params()
            return Action(constants.ACTION_FILTER_CATEGORY, params)
        if command == "14":
            params = self.get_filter_category_condition_params()
            return Action(constants.ACTION_FILTER_CATEGORY_CONDITION, params)
        if command == "15":
            return Action(constants.ACTION_UNDO)
        if command == "16":
            return Action(constants.ACTION_CHANGE_UI)
        if command == "17":
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