from __future__ import absolute_import
import constants
from validation_error import ValidationError

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
            raise ValidationError("Sum must be a natural number")
        cost = int(cost)
        if cost < 1:
            raise ValidationError("Sum cannot be 0 or negative")
            
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")
    
    def validate_insert(self, day, cost, category):
        if not self.is_int(day):
            raise ValidationError("Day must be a natural number")
        day = int(day)
        if day < 1:
            raise ValidationError("Day cannot be less than 1")
        if day > 31:
            raise ValidationError("Day cannot be more than 31")

        if not self.is_int(cost):
            raise ValidationError("Sum must be a natural number")
        cost = int(cost)
        if cost < 1:
            raise ValidationError("Sum cannot be 0 or negative")

        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

    def validate_remove_day(self, day):
        if not self.is_int(day):
            raise ValidationError("Day must be a natural number")
        day = int(day)
        if day < 1:
            raise ValidationError("Day cannot be less than 1")
        if day > 31:
            raise ValidationError("Day cannot be more than 31")

    def validate_remove_range(self, day_from, day_to):
        if not self.is_int(day_from):
            raise ValidationError("The starting day must be a natural number")
        day_from = int(day_from)
        if day_from < 1:
            raise ValidationError("The starting day cannot be less than 1")
        if day_from > 31:
            raise ValidationError("The starting day cannot be more than 31")
        
        if not self.is_int(day_to):
            raise ValidationError("The eding day must be a natural number")
        day_to = int(day_to)
        if day_to < 1:
            raise ValidationError("The eding day cannot be less than 1")
        if day_to > 31:
            raise ValidationError("The eding day cannot be more than 31")

        if day_from > day_to:
            raise ValidationError("The starting day cannot be after the ending day")

    def validate_remove_category(self, category):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

    def validate_list_category(self, category):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")
    
    def validate_list_category_condition(self, category, operator, value):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

        if not self.is_operator(operator):
            raise ValidationError("Operator is not valid, use only '<', '=' or '>'")

        if not self.is_int(value):
            raise ValidationError("The value must be a natural number")

    def validate_sum_category(self, category):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")
        
    def validate_max_day(self, day):
        if not self.is_int(day):
            raise ValidationError("Day must be a natural number")
        day = int(day)
        if day < 1:
            raise ValidationError("Day cannot be less than 1")
        if day > 31:
            raise ValidationError("Day cannot be more than 31")

    def validate_sort_day(self, day):
        if not self.is_int(day):
            raise ValidationError("Day must be a natural number")
        day = int(day)
        if day < 1:
            raise ValidationError("Day cannot be less than 1")
        if day > 31:
            raise ValidationError("Day cannot be more than 31")

    def validate_sort_category(self, category):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

    def validate_filter_category(self, category):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

    def validate_filter_category_condition(self, category, operator, value):
        if not self.is_category_valid(category):
            raise ValidationError("Category is not valid")

        if not self.is_operator(operator):
            raise ValidationError("Operator is not valid, use only '<', '=' or '>'")
        
        if not self.is_int(value):
            raise ValidationError("The value must be a natural number")