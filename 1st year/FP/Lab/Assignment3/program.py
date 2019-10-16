from __future__ import absolute_import
from command_ui import CommandUI
from menu_ui import MenuUI
from tester import Tester
from function import Function
from action import Action
import constants

class Program:

    __ui_manager = CommandUI()
    __DEBUGGING_MODE = True
    __expenses = []

    def change_ui(self):
        if self.__ui_manager.is_prefered():
            return
        if self.__ui_manager.TYPE == constants.TYPE_COMMAND:
            self.__ui_manager = MenuUI()
        else:
            self.__ui_manager = CommandUI()

    def start(self):
        self.change_ui()
        self.__ui_manager.show_help()
        while True:
            action = self.__ui_manager.get_action()
            if action.type == constants.ACTION_ADD:
                pass
            elif action.type == constants.ACTION_CHANGE_UI:
                self.change_ui()
            elif action.type == constants.ACTION_ERROR:
                self.__ui_manager.on_invalid_command()
            elif action.type == constants.ACTION_EXIT:
                return
            elif action.type == constants.ACTION_FILTER_CATEGORY:
                pass
            elif action.type == constants.ACTION_FILTER_CATEGORY_CONDITION:
                pass
            elif action.type == constants.ACTION_HELP:
                self.__ui_manager.show_help()
            elif action.type == constants.ACTION_INSERT:
                pass
            elif action.type == constants.ACTION_LIST_ALL:
                pass
            elif action.type == constants.ACTION_LIST_CATEGORY:
                pass
            elif action.type == constants.ACTION_LIST_CATEGORY_CONDITION:
                pass
            elif action.type == constants.ACTION_MAX_DAY:
                pass
            elif action.type == constants.ACTION_REMOVE_CATEGORY:
                pass
            elif action.type == constants.ACTION_REMOVE_DAY:
                pass
            elif action.type == constants.ACTION_REMOVE_RANGE:
                pass
            elif action.type == constants.ACTION_SORT_CATEGORY:
                pass
            elif action.type == constants.ACTION_SORT_DAY:
                pass
            elif action.type == constants.ACTION_SUM_CATEGORY:
                pass
            elif action.type == constants.ACTION_UNDO:
                pass