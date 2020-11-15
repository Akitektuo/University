import unittest
from controller import Controller
from menu_ui import MenuUI
from graphical_ui import GraphicalUI
from action import Action

class Program:

    def __init__(self):
        self.__controller = Controller()
        self.__ui_manager = MenuUI()

    def change_ui(self):
         if self.__ui_manager.switch_ui_prefernece():
            if self.__ui_manager.TYPE == MenuUI.TYPE:
                self.__ui_manager = GraphicalUI()
            else:
                self.__ui_manager = MenuUI()

    def start(self):
        self.__controller.preload_list()
        self.change_ui()

        while True:
            result = ""

            try:
                action = self.__ui_manager.get_action()

                if action.type == Action.ADD:
                    self.__controller.add(action.params)

                elif action.type == Action.REMOVE:
                    self.__controller.remove(action.params)

                elif action.type == Action.UPDATE:
                    self.__controller.update(action.params)

                elif action.type == Action.LIST:
                    result = self.__controller.list_all()

                elif action.type == Action.GRADE:
                    self.__controller.grade(action.params)

                elif action.type == Action.SEARCH:
                    result = self.__controller.search(action.params)

                elif action.type == Action.SEE_FAILING_STUDENTS:
                    result = self.__controller.see_failing_students()

                elif action.type == Action.SEE_BEST_STUDENTS:
                    result = self.__controller.see_best_students()

                elif action.type == Action.SEE_GRADES:
                    result = self.__controller.see_grades()

                elif action.type == Action.UNDO:
                    self.__controller.undo()

                elif action.type == Action.REDO:
                    self.__controller.redo()

                elif action.type == Action.HELP:
                    self.__ui_manager.show_help()
                    continue

                elif action.type == Action.UI:
                    self.change_ui()
                    continue

                elif action.type == Action.TEST:
                    unittest.main(module = "controller_test", exit = False) 
                    continue

                elif action.type == Action.EXIT:
                    return
                
                self.__ui_manager.print_result(result)
            except Exception as error:
                self.__ui_manager.handle_error(error)
