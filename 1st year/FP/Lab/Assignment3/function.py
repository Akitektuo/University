from __future__ import absolute_import
from expense import Expense
from validator import Validator
from undo_actions import UndoActions

class Function:
    
    def __init__(self, expenses):
        self.expenses = expenses
        self.undo_actions = UndoActions(expenses)
        self.validator = Validator()

    def remove_from_list(self, condition):
        expenses_copy = list(self.expenses)

        for e in expenses_copy:
            if condition(e):
                self.undo_actions.removed(e)
                self.expenses.remove(e)

    def build_list(self, condition = lambda e: True):
        list_expenses = ""

        for i in range(len(self.expenses)):
            e = self.expenses[i]
            if condition(e):
                list_expenses += "\n" + str(i + 1) + ". " + str(e)

        if len(list_expenses) < 1:
            list_expenses = "No expenses found"

        return list_expenses

    def get_comparison(self, operator):
        if operator == '<':
            return lambda x, y: x < y

        if operator == '>':
            return lambda x, y: x > y

        return lambda x, y: x == y

    def sum_by(self, condition = lambda e: True):
        total_expenses = 0

        for e in self.expenses:
            if condition(e):
                total_expenses += e.cost

        return total_expenses

    def get_max_by(self, condition = lambda e: True):
        maximum = -1

        for e in self.expenses:
            if condition(e):
                maximum = max(maximum, e.cost)
        
        return maximum

    def format_day(self, day):
        if day % 10 == 1:
            return str(day) + "st"
        if day % 10 == 2:
            return str(day) + "nd"
        if day % 10 == 3:
            return str(day) + "rd"
        return str(day) + "th"

    def add(self, params):
        cost_string = params[0]
        category = params[1]

        error = self.validator.validate_add(cost_string, category)
        if error:
            return error
        cost = int(cost_string)

        expense = Expense(cost, category)
        self.undo_actions.added(expense)
        self.expenses.append(expense)

    def insert(self, params):
        day_string = params[0]
        cost_string = params[1]
        category = params[2]

        error = self.validator.validate_insert(day_string, cost_string, category)
        if error:
            return error
        day = int(day_string)
        cost = int(cost_string)

        expense = Expense(cost, category, day)
        self.undo_actions.added(expense)
        self.expenses.append(expense)

    def remove_day(self, params):
        day_string = params[0]

        error = self.validator.validate_remove_day(day_string)
        if error:
            return error
        day = int(day_string)

        self.remove_from_list(lambda e: e.day == day)

    def remove_range(self, params):
        day_from_string = params[0]
        day_to_string = params[2]

        error = self.validator.validate_remove_range(day_from_string, day_to_string)
        if error:
            return error
        day_from = int(day_from_string)
        day_to = int(day_to_string)

        self.remove_from_list(lambda e: day_from <= e.day and e.day <= day_to)

    def remove_category(self, params):
        category = params[0]

        error = self.validator.validate_remove_category(category)
        if error:
            return error
            
        self.remove_from_list(lambda e: e.category == category)

    def list_all(self):
        return self.build_list()        

    def list_category(self, params):
        category = params[0]

        error = self.validator.validate_list_category(category)
        if error:
            return error
        condition = lambda e: e.category == category

        return self.build_list(condition)

    def list_category_condition(self, params):
        category = params[0]
        operator = params[1]
        value_string = params[2]

        error = self.validator.validate_list_category_condition(category, operator, value_string)
        if error:
            return error
        value = int(value_string)
        comparison = self.get_comparison(operator)
        condition = lambda e: e.category == category and comparison(e.cost, value)

        return self.build_list(condition)

    def sum_category(self, params):
        category = params[0]

        error = self.validator.validate_sum_category(category)
        if error:
            return error
        
        category_sum = self.sum_by(lambda e: e.category == category)
        return "\nThe sum of total expenses of category '" + category + "' is " + str(category_sum)

    def max_day(self, params):
        day_string = params[0]

        error = self.validator.validate_max_day(day_string)
        if error:
            return error
        day = int(day_string)

        max_day = self.get_max_by(lambda e: e.day == day)
        return "\nThe maximum of the " + self.format_day(day) + " day is " + str(max_day)

    def sort_day(self, params):
        return "Not implemented yet"

    def sort_category(self, params):
        return "Not implemented yet"

    def filter_category(self, params):
        return "Not implemented yet"

    def filter_category_condition(self, params):
        return "Not implemented yet"

    def undo_last_action(self):
        if self.undo_actions.nothing_to_undo():
            return "No action available to undo"
        self.undo_actions.undo()