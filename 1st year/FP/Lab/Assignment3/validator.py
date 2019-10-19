from __future__ import absolute_import
import constants

class Validator:

    def is_int(self, param):
        try:
            int(param)
        except ValueError:
            return False
        return True

    def is_category_valid(self, category):
        return category == constants.CATEGORY_CLOATHING or category == constants.CATEGORY_FOOD or category == constants.CATEGORY_HOUSEKEEPING or category == constants.CATEGORY_INTERNET or category == constants.CATEGORY_OTHERS or category == constants.CATEGORY_TRANSPORT

    def is_operator(self, operator):
        return operator == '<' or operator == '=' or operator == '>'

    def validate_add(self, cost, category):
        if not self.is_int(cost):
            return "Sum must be a natural number"
        cost = int(cost)
        if cost < 1:
            return "Sum cannot be 0 or negative"
            
        if not self.is_category_valid(category):
            return "Category is not valid"
    
    def validate_insert(self, day, cost, category):
        if not self.is_int(day):
            return "Day must be a natural number"
        day = int(day)
        if day < 1:
            return "Day cannot be less than 1"
        if day > 31:
            return "Day cannot be more than 31"

        if not self.is_int(cost):
            return "Sum must be a natural number"
        cost = int(cost)
        if cost < 1:
            return "Sum cannot be 0 or negative"

        if not self.is_category_valid(category):
            return "Category is not valid"

    def validate_remove_day(self, day):
        if not self.is_int(day):
            return "Day must be a natural number"
        day = int(day)
        if day < 1:
            return "Day cannot be less than 1"
        if day > 31:
            return "Day cannot be more than 31"

    def validate_remove_range(self, day_from, day_to):
        if not self.is_int(day_from):
            return "The starting day must be a natural number"
        day_from = int(day_from)
        if day_from < 1:
            return "The starting day cannot be less than 1"
        if day_from > 31:
            return "The starting day cannot be more than 31"
        
        if not self.is_int(day_to):
            return "The eding day must be a natural number"
        day_to = int(day_to)
        if day_to < 1:
            return "The eding day cannot be less than 1"
        if day_to > 31:
            return "The eding day cannot be more than 31"

        if day_from > day_to:
            return "The starting day cannot be after the ending day"

    def validate_remove_category(self, category):
        if not self.is_category_valid(category):
            return "Category is not valid"

    def validate_list_category(self, category):
        if not self.is_category_valid(category):
            return "Category is not valid"
    
    def validate_list_category_condition(self, category, operator, value):
        if not self.is_category_valid(category):
            return "Category is not valid"
        if not self.is_operator(operator):
            return "Operator is not valid, use only '<', '=' or '>'"
        if not self.is_int(value):
            return "The value must be a natural number"

    def validate_sum_category(self, category):
        if not self.is_category_valid(category):
            return "Category is not valid"
        
    def validate_max_day(self, day):
        if not self.is_int(day):
            return "Day must be a natural number"
        day = int(day)
        if day < 1:
            return "Day cannot be less than 1"
        if day > 31:
            return "Day cannot be more than 31"

    def validate_sort_day(self, day):
        if not self.is_int(day):
            return "Day must be a natural number"
        day = int(day)
        if day < 1:
            return "Day cannot be less than 1"
        if day > 31:
            return "Day cannot be more than 31"

    def validate_sort_category(self, category):
        if not self.is_category_valid(category):
            return "Category is not valid"

    def validate_filter_category(self, category):
        if not self.is_category_valid(category):
            return "Category is not valid"

    def validate_filter_category_condition(self, category, operator, value):
        if not self.is_category_valid(category):
            return "Category is not valid"
        if not self.is_operator(operator):
            return "Operator is not valid, use only '<', '=' or '>'"
        if not self.is_int(value):
            return "The value must be a natural number"