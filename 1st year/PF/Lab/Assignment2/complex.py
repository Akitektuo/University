import math

class Complex:

    # Init block
    def __init__(self, real = 0, imaginary = 0):
        self.real = real
        self.imaginary = imaginary

    # Override str()
    def __str__(self):
        if self.imaginary == 0:
            return self.format_float(self.real)
        if self.real == 0:
            if self.imaginary == -1:
                return "-i"
            return self.format_float(self.imaginary, False) + "i"
        if (self.imaginary < 0):
            return self.format_float(self.real) + "-" + self.format_float(-self.imaginary, False) + "i"
        return self.format_float(self.real) + "+" + self.format_float(self.imaginary, False) + "i"

    '''
        Formats a float number to have only 2 decimals and if it is a int, to be shown as a int

        Input:
            int - representing the number to be formated
            (optional) bool - true to format the number if it's 1, false to return an empty string
        Output: string - representing the formated number
    '''
    def format_float(self, number, show_1 = True):
        number = round(number, 2)
        if (not show_1 and number == 1):
            return ""
        if number == int(number):
            return str(int(number))
        return str(number)

    '''
        Checks if the instance is a real number

        Input: -
        Output: bool - true either the instance is a real number or not
    '''
    def is_real(self):
        if self.imaginary == 0:
            return True
        return False

    '''
        Returns the modulus of the instance

        Input: -
        Output: float - representing the modulus
    '''
    def get_modulus(self):
        return math.sqrt(self.real**2 + self.imaginary**2)