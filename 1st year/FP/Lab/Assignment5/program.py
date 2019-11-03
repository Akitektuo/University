import unittest
from action import *

class Program:

    def __init__(self):
        # self.__services = Services() TODO
        self.__ui_manager = MenuUI()
        self.__DEBUGGING_MODE = True

    def start(self):
        if self.__DEBUGGING_MODE:
            unittest.main()

        # self.__services.preload_list() TODO
        self.__ui_manager.ask_ui_preference()

        while True:
            action = self.__ui_manager.get_action()
            result = ""

            try:
                if action.type == ACTION_ADD:
                    pass

                elif action.type == ACTION_REMOVE:
                    pass

                elif action.type == ACTION_UPDATE:
                    pass

                elif action.type == ACTION_LIST:
                    pass

                elif action.type == ACTION_GRADE:
                    pass

                elif action.type == ACTION_SEARCH:
                    pass

                elif action.type == ACTION_SEE_FAILING_STUDENTS:
                    pass

                elif action.type == ACTION_SEE_BEST_STUDENTS:
                    pass

                elif action.type == ACTION_SEE_GRADES:
                    pass

                elif action.type == ACTION_UNDO:
                    pass

                elif action.type == ACTION_HELP:
                    self.__ui_manager.show_help()

                elif action.type == ACTION_EXIT:
                    return
            
            self.__ui_manager.print_result(result)
            except Exception as error:
                self.__ui_manager.handle_error(error)
