from __future__ import absolute_import
import re
import constants
from action import Action

class CommandUI:

    def __init__(self):
        self.TYPE = constants.TYPE_COMMAND

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
        command = input("> ")
        if re.search("^help", command):
            return Action(constants.ACTION_HELP)
        elif re.search("^ui", command):
            return Action(constants.ACTION_CHANGE_UI)
        elif re.search("^exit", command):
            return Action(constants.ACTION_EXIT)
        return Action(constants.ACTION_ERROR)

    def on_invalid_command(self):
        print("Invalid command, type \"help\" if you want to see the commands")
