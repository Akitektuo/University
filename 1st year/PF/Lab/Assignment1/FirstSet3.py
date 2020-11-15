'''
    Reads from the console a number and returns it

    Input: -
    Output: int - representing the number 
'''
def read_number():
    return int(input("Give a number: "))

'''
    Prints to the console the given number

    Input: int - representing the number
    Output: -
'''
def print_number(number):
    print("The minimal natural number formed with the same digits is " + str(number))

'''
    Decomposes a number into digits

    Input: int - representing the number to be decomposed
    Output: list - representing the appearance of each digit  
'''
def decompose_number_into_digits(number):
    digits = [0 for i in range(10)]

    while number > 0:
        digits[number % 10] += 1
        number //= 10
    
    return digits

'''
    Returns the minimal number formed from the digits of the given number

    Input: int - representing the number which will be transformed
    Output: int - representing the minimal number  
'''
def get_minimal_number(number):
    digits = decompose_number_into_digits(number)
    minimal_numer = 0

    for i in range(len(digits)):
        while digits[i] > 0:
            minimal_numer = minimal_numer * 10 + i
            digits[i] -= 1
    
    return minimal_numer

number = read_number()
minimal_number = get_minimal_number(number)
print_number(minimal_number)