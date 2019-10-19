from __future__ import absolute_import
from expense import Expense
from validator import Validator
from undo_actions import UndoActions
import constants

class Function:
    
    def __init__(self, expenses):
        self.expenses = expenses
        self.undo_actions = UndoActions(expenses)
        self.validator = Validator()

    def remove_from_list(self, condition):
        expenses_copy = list(self.expenses)
        removed_expenses = []

        for e in expenses_copy:
            if condition(e):
                removed_expenses.append(e)
                self.expenses.remove(e)
        
        self.undo_actions.removed_list(removed_expenses)

    def build_list(self, expenses, condition = lambda e: True):
        list_expenses = ""
        prints = 0

        for i in range(len(expenses)):
            e = expenses[i]
            if condition(e):
                prints += 1
                list_expenses += "\n" + str(prints) + ". " + str(e)

        if prints < 1:
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

    def add_expense(self, expense):
        self.undo_actions.added(expense)
        self.expenses.append(expense)

    def preload_list(self):
        self.add_expense(Expense(5, constants.CATEGORY_CLOATHING, 1))
        self.add_expense(Expense(10, constants.CATEGORY_FOOD, 31))
        self.add_expense(Expense(15, constants.CATEGORY_HOUSEKEEPING))
        self.add_expense(Expense(20, constants.CATEGORY_INTERNET, 28))
        self.add_expense(Expense(25, constants.CATEGORY_OTHERS, 29))
        self.add_expense(Expense(30, constants.CATEGORY_TRANSPORT))
        self.add_expense(Expense(35, constants.CATEGORY_OTHERS, 30))
        self.add_expense(Expense(40, constants.CATEGORY_INTERNET, 12))
        self.add_expense(Expense(45, constants.CATEGORY_HOUSEKEEPING))
        self.add_expense(Expense(50, constants.CATEGORY_FOOD, 4))

    def add(self, params):
        cost_string = params[0]
        category = params[1]

        error = self.validator.validate_add(cost_string, category)
        if error:
            return error
        cost = int(cost_string)

        self.add_expense(Expense(cost, category))

    def insert(self, params):
        day_string = params[0]
        cost_string = params[1]
        category = params[2]

        error = self.validator.validate_insert(day_string, cost_string, category)
        if error:
            return error
        day = int(day_string)
        cost = int(cost_string)

        self.add_expense(Expense(cost, category, day))

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
        return self.build_list(self.expenses)        

    def list_category(self, params):
        category = params[0]

        error = self.validator.validate_list_category(category)
        if error:
            return error
        condition = lambda e: e.category == category

        return self.build_list(self.expenses, condition)

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

        return self.build_list(self.expenses, condition)

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
        if max_day < 1:
            return "No expenses found in the " + self.format_day(day) + " day"
        return "\nThe maximum of the " + self.format_day(day) + " day is " + str(max_day)

    def sort_day(self, params):
        day_string = params[0]

        error = self.validator.validate_sort_day(day_string)
        if error:
            return error
        day = int(day_string)

        ordered_list = sorted(self.expenses, key = lambda e: e.cost)
        return self.build_list(ordered_list, lambda e: e.day == day)

    def sort_category(self, params):
        category = params[0]

        error = self.validator.validate_sort_category(category)
        if error:
            return error

        ordered_list = sorted(self.expenses, key = lambda e: e.cost)
        return self.build_list(ordered_list, lambda e: e.category == category)

    def filter_category(self, params):
        category = params[0]

        error = self.validator.validate_filter_category(category)
        if error:
            return error
            
        self.remove_from_list(lambda e: e.category != category)

    def filter_category_condition(self, params):
        category = params[0]
        operator = params[1]
        value_string = params[2]

        error = self.validator.validate_filter_category_condition(category, operator, value_string)
        if error:
            return error
        value = int(value_string)
            
        comparison = self.get_comparison(operator)
        self.remove_from_list(lambda e: not (e.category == category and comparison(e.cost, value)))

    def undo_last_action(self):
        if self.undo_actions.nothing_to_undo():
            return "No action available to undo"
        self.undo_actions.undo()