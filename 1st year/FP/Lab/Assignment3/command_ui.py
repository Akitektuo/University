from __future__ import absolute_import
import re
import constants
from action import Action
from validator import Validator

class CommandUI:

    def __init__(self):
        self.TYPE = constants.TYPE_COMMAND
        self.validator = Validator()

    def is_prefered(self):
        print("\nDo you want to use command based UI? (yes/no)")
        user_input = input("> ")
        return re.search("^y|^Y", user_input)

    def show_help(self):
        print("\nWelcome to Family Expenses! To see all the commands at any time, type \"help\".")
        print("The commands are the following:")
        print("Adding")
        print("   - add <sum> <category>")
        print("   - add <day> <sum> <category>")
        print("Removing")
        print("   - remove <day>")
        print("   - remove <start day> to <end day>")
        print("   - remove <category>")
        print("Listing")
        print("   - list")
        print("   - list <category>")
        print("   - list <category> [<|=|>] <value>")
        print("Stats")
        print("   - sum <category>")
        print("   - max <day>")
        print("   - sort <day>")
        print("   - sort <category>")
        print("Filter")
        print("   - filter <category>")
        print("   - filter <category> [<|=|>] <value>")
        print("Undo last action")
        print("   - undo")
        print("Change UI")
        print("   - ui")
        print("Exit")
        print("   - exit")

    def get_action(self):
        command = input("\n> ")
        if re.search("^h", command):
            return Action(constants.ACTION_HELP)
        if re.search("^a", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 2:
                return Action(constants.ACTION_ADD, params)
            if len(params) == 3:
                return Action(constants.ACTION_INSERT, params)
        if re.search("^r", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                if self.validator.is_int(params[0]):
                    return Action(constants.ACTION_REMOVE_DAY, params)
                return Action(constants.ACTION_REMOVE_CATEGORY, params)
            if len(params) == 3:
                return Action(constants.ACTION_REMOVE_RANGE, params)
        if re.search("^l", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                return Action(constants.ACTION_LIST_CATEGORY, params)
            if len(params) == 3:
                return Action(constants.ACTION_LIST_CATEGORY_CONDITION, params)
            return Action(constants.ACTION_LIST_ALL)
        if re.search("^su", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                return Action(constants.ACTION_SUM_CATEGORY, params)
        if re.search("^m", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                return Action(constants.ACTION_MAX_DAY, params)
        if re.search("^so", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                if self.validator.is_int(params[0]):
                    return Action(constants.ACTION_SORT_DAY, params)
                return Action(constants.ACTION_SORT_CATEGORY, params)
        if re.search("^f", command):
            params = re.split(" ", command)
            params.pop(0)
            if len(params) == 1:
                return Action(constants.ACTION_FILTER_CATEGORY, params)
            if len(params) == 3:
                return Action(constants.ACTION_FILTER_CATEGORY_CONDITION, params)
        if re.search("^un", command):
            return Action(constants.ACTION_UNDO)
        if re.search("^ui", command):
            return Action(constants.ACTION_CHANGE_UI)
        if re.search("^e", command):
            return Action(constants.ACTION_EXIT)
        return Action(constants.ACTION_ERROR)

    def on_invalid_command(self):
        print("Invalid command, type \"help\" if you want to see the commands")

    def handle_result(self, result):
        if result:
            print("Error: " + result)
            return
        print("Operation successful")

    def print_result(self, result):
        if (result == "" or '\n' not in result):
            return self.handle_result(result)
        print(result)
