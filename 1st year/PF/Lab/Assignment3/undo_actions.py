from __future__ import absolute_import
from action import Action
from expense import Expense
import constants

class UndoActions:

    def __init__(self, expenses):
        self.actions = []
        self.expenses = expenses

    def nothing_to_undo(self):
        return len(self.actions) < 1

    def added(self, expense):
        self.actions.append(Action(constants.ACTION_REMOVE, expense))

    def removed(self, expense):
        self.actions.append(Action(constants.ACTION_ADD, expense))

    def removed_list(self, expenses):
        self.actions.append(Action(constants.ACTION_ADD_LIST, expenses))

    def __add_back(self, expense):
        self.expenses.append(expense)

    def __add_back_list(self, expenses):
        self.expenses.extend(expenses)

    def __revert_add(self, expense):
        self.expenses.remove(expense)

    def undo(self):
        if len(self.actions) == 0:
            return
        last_action = self.actions.pop()
        if last_action.type == constants.ACTION_ADD:
            self.__add_back(last_action.params)
            return
        if last_action.type == constants.ACTION_ADD_LIST:
            self.__add_back_list(last_action.params)
            return
        if last_action.type == constants.ACTION_REMOVE:
            self.__revert_add(last_action.params)
            return