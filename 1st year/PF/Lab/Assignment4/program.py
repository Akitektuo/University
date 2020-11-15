from __future__ import absolute_import
from menu_ui import MenuUI
from tester import run_tests
from services import Services
from action import Action
from validation_error import ValidationError
import constants

class Program:

    def __init__(self):
        self.__ui_manager = MenuUI()
        self.__DEBUGGING_MODE = True
        self.__services = Services()

    def start(self):
        if self.__DEBUGGING_MODE:
            run_tests()

        self.__services.preload_list()
        self.__ui_manager.show_help()

        while True:
            action = self.__ui_manager.get_action()
            
            try:
                if action.type == constants.ACTION_ADD:
                    result = self.__services.add(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_ERROR:
                    self.__ui_manager.on_invalid_command()

                elif action.type == constants.ACTION_EXIT:
                    return

                elif action.type == constants.ACTION_FILTER_ABOVE:
                    result = self.__services.filter_above(action.params)
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_HELP:
                    self.__ui_manager.show_help()

                elif action.type == constants.ACTION_LIST_ALL:
                    result = self.__services.list_all()
                    self.__ui_manager.print_result(result)

                elif action.type == constants.ACTION_UNDO:
                    result = self.__services.undo_last_action()
                    self.__ui_manager.print_result(result)
            except ValidationError as error:
                self.__ui_manager.handle_error(error)