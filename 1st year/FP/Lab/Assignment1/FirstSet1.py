import math

'''
    Reads from the console a natural number and returns it

    Input: -
    Output: int - representing the natural number 
'''
def read_number():
    return int(input("Give a natural number: "))

'''
    Prints the number found as the solution in console

    Input: int - representing the number found as solution
    Output: -
'''
def print_solution(number):
    print("The first prime number larger than the given one is " + str(number))

'''
    Checks if a number is prime or not

    Input: int - representing the number that is being checked
    Output: bool - true if it the given number is prime, false otherwise
'''
def is_prime(number):
    if number <= 1:
        return False
    if number <= 3:
        return True
    if number % 2 == 0 or number % 3 == 0:
        return False
    for i in range(5, int(math.sqrt(number)), 6):
        if number % i == 0 or number % (i + 2) == 0:
            return False
    return True

'''
    Searches for the closest prime number after the given one

    Input: int - representing the number from which the search starts
    Output: int - representing the prime number after the given one
'''
def find_next_prime_number(number):
    number += 1
    while(not is_prime(number)):
        number += 1
    return number

num = read_number()
res = find_next_prime_number(num)
print_solution(res)