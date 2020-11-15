from __future__ import absolute_import
from datetime import date

class Expense:

    def __init__(self, day, cost, category):
        self.day = day
        self.cost = cost
        self.category = category

    def format_day(self):
        if self.day % 10 == 1:
            return str(self.day) + "st"
        if self.day % 10 == 2:
            return str(self.day) + "nd"
        if self.day % 10 == 3:
            return str(self.day) + "rd"
        return str(self.day) + "th"

    def __str__(self):
        return "In the " + self.format_day() + " day, the expense of " + str(self.cost) + " RON was added to the '" + self.category + "' category"