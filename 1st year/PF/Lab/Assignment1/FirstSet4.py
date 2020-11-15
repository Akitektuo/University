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
    print("The largest natural number formed with the same digits is " + str(number))

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
    Returns the largest number formed from the digits of the given number

    Input: int - representing the number which will be transformed
    Output: int - representing the largest number  
'''
def get_largest_number(number):
    digits = decompose_number_into_digits(number)
    largest_numer = 0

    for i in reversed(range(len(digits))):
        while digits[i] > 0:
            largest_numer = largest_numer * 10 + i
            digits[i] -= 1
    
    return largest_numer
    
number = read_number()
largest_number = get_largest_number(number)
print_number(largest_number)
