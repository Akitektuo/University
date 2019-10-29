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

    def validate_add(self, day, cost, category):
        if not self.is_int(day):
            raise ValidationError("Day must be a natural number")
        day = int(day)
        if day < 1:
            raise ValidationError("Day cannot be less than 1")
        if day > 30:
            raise ValidationError("Day cannot be more than 30")

        if not self.is_int(cost):
            raise ValidationError("Sum must be a natural number")
        cost = int(cost)
        if cost < 1:
            raise ValidationError("Sum cannot be 0 or negative")
            
        if len(category) < 1:
            raise ValidationError("Category must have at least 1 character")
    
    def validate_filter_above(self, value):
        if not self.is_int(value):
            raise ValidationError("The value must be a natural number")