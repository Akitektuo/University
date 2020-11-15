from __future__ import absolute_import
from expense import Expense
from validator import Validator
from undo_actions import UndoActions
from validation_error import ValidationError

class Services:
    
    # Constructor
    def __init__(self, expenses = []):
        self.expenses = expenses
        self.undo_actions = UndoActions(expenses)
        self.validator = Validator()

    def remove_from_list(self, condition):
        '''
        Removes all elements which fulfill the condition 

        Input: lambda - receives an expense object and must return a bool value
        Output: -
        '''
        expenses_copy = list(self.expenses)
        removed_expenses = []

        for e in expenses_copy:
            if condition(e):
                removed_expenses.append(e)
                self.expenses.remove(e)
        
        self.undo_actions.removed_list(removed_expenses)

    def build_list(self, expenses, condition = lambda e: True):
        '''
        Builds a string representing the list of expenses 

        Input: 
            list - expenses list to be converted into string
            lambda (optional) - receives an expense object and must return a bool value
        Output: string - the converted list
        '''
        list_expenses = ""
        prints = 0

        for e in expenses:
            if condition(e):
                prints += 1
                list_expenses += "\n" + str(prints) + ". " + str(e)

        if prints < 1:
            raise ValidationError("No expenses found")

        return list_expenses

    def add_expense(self, expense):
        '''
        Adds given expense to the list of expenses

        Input: Expense - the expense to be stored
        Output: -
        '''
        self.undo_actions.added(expense)
        self.expenses.append(expense)

    def preload_list(self):
        '''
        Preloads the liist with 10 predefined expenses

        Input: -
        Output: -
        '''
        self.add_expense(Expense(1, 5, "cloathing"))
        self.add_expense(Expense(30, 10, "food"))
        self.add_expense(Expense(2, 15, "housekeeping"))
        self.add_expense(Expense(29, 20, "internet"))
        self.add_expense(Expense(3, 25, "others"))
        self.add_expense(Expense(28, 30, "transport"))
        self.add_expense(Expense(4, 35, "others"))
        self.add_expense(Expense(27, 40, "internet"))
        self.add_expense(Expense(5, 45, "housekeeping"))
        self.add_expense(Expense(26, 50, "food"))

    def add(self, params):
        '''
        Adds to list an expense

        Input: list - params from input
        Output: Error - in case of error
        '''
        day_string = params[0]
        cost_string = params[1]
        category = params[2]

        self.validator.validate_add(day_string, cost_string, category)
        day = int(day_string)
        cost = int(cost_string)

        self.add_expense(Expense(day, cost, category))

    def list_all(self):
        '''
        Turns all expenses into a string

        Input: -
        Output: string - the list or error
        '''
        return self.build_list(self.expenses)        

    def filter_above(self, params):
        '''
        Filters the expenses and keeps only the ones with the cost above the given value

        Input: list - params from input
        Output: -
        '''
        value_string = params[0]

        self.validator.validate_filter_above(value_string)
        value = int(value_string)
            
        self.remove_from_list(lambda e: e.cost <= value)

    def undo_last_action(self):
        '''
        Undoes the last action that modified the list of expenses

        Input: list - params from input
        Output: -
        '''
        if self.undo_actions.nothing_to_undo():
            raise ValidationError("No action available to undo")
        self.undo_actions.undo()