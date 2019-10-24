from __future__ import absolute_import
from command_ui import CommandUI
from menu_ui import MenuUI
from tester import run_tests
from function import Function
from action import Action
from validation_error import ValidationError
import constants

class Program:

    def __init__(self):
        self.__ui_manager = CommandUI()
        self.__DEBUGGING_MODE = True
        self.__function = Function()

    def change_ui(self):
        if not self.__ui_manager.switch():
            return
        if self.__ui_manager.TYPE == constants.TYPE_COMMAND:
            self.__ui_manager = MenuUI()
            print("Changed to menu based UI")
        else:
            self.__ui_manager = CommandUI()
            print("Changed to command based UI")

    def start(self):
        if self.__DEBUGGING_MODE:
            run_tests()

        self.__function.preload_list()
        self.change_ui()
        self.__ui_manager.show_help()

        while True:
            action = self.__ui_manager.get_action()
            
            try:
                if action.type == constants.ACTION_ADD:
                    result = self.__function.add(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_CHANGE_UI:
                    self.change_ui()

                elif action.type == constants.ACTION_ERROR:
                    self.__ui_manager.on_invalid_command()

                elif action.type == constants.ACTION_EXIT:
                    return

                elif action.type == constants.ACTION_FILTER_CATEGORY:
                    result = self.__function.filter_category(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_FILTER_CATEGORY_CONDITION:
                    result = self.__function.filter_category_condition(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_HELP:
                    self.__ui_manager.show_help()

                elif action.type == constants.ACTION_INSERT:
                    result = self.__function.insert(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_LIST_ALL:
                    result = self.__function.list_all()
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_LIST_CATEGORY:
                    result = self.__function.list_category(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_LIST_CATEGORY_CONDITION:
                    result = self.__function.list_category_condition(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_MAX_DAY:
                    result = self.__function.max_day(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_REMOVE_CATEGORY:
                    result = self.__function.remove_category(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_REMOVE_DAY:
                    result = self.__function.remove_day(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_REMOVE_RANGE:
                    result = self.__function.remove_range(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_SORT_CATEGORY:
                    result = self.__function.sort_category(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_SORT_DAY:
                    result = self.__function.sort_day(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_SUM_CATEGORY:
                    result = self.__function.sum_category(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_UNDO:
                    result = self.__function.undo_last_action()
                    self.__ui_manager.print_result(result)
            except ValidationError as error:
                self.__ui_manager.handle_error(error)