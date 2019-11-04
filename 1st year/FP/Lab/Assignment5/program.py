import unittest
from services import Services
from menu_ui import MenuUI
from action import Action

class Program:

    def __init__(self):
        self.__services = Services()
        self.__ui_manager = MenuUI()

    def change_ui(self):
         if self.__ui_manager.switch_ui_prefernece():
            if self.__ui_manager.TYPE == MenuUI.TYPE:
                pass # self.__ui_manager = GraficalUI()
            else:
                self.__ui_manager = MenuUI()

    def start(self):
        self.__services.preload_list()
        self.change_ui()

        while True:
            result = ""

            try:
                action = self.__ui_manager.get_action()

                if action.type == Action.ADD:
                    pass

                elif action.type == Action.REMOVE:
                    pass

                elif action.type == Action.UPDATE:
                    pass

                elif action.type == Action.LIST:
                    result = self.__services.list_all_data()

                elif action.type == Action.GRADE:
                    pass

                elif action.type == Action.SEARCH:
                    pass

                elif action.type == Action.SEE_FAILING_STUDENTS:
                    pass

                elif action.type == Action.SEE_BEST_STUDENTS:
                    pass

                elif action.type == Action.SEE_GRADES:
                    pass

                elif action.type == Action.UNDO:
                    pass

                elif action.type == Action.HELP:
                    self.__ui_manager.show_help()
                    continue

                elif action.type == Action.UI:
                    self.change_ui()
                    continue

                elif action.type == Action.TEST:
                    unittest.main()

                elif action.type == Action.EXIT:
                    return
                
                self.__ui_manager.print_result(result)
            except Exception as error:
                self.__ui_manager.handle_error(error)
