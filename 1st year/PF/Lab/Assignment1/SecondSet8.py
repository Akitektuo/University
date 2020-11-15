'''
    Reads from the console a natural number and returns it

    Input: -
    Output: int - representing the natural number 
'''
def read_number():
    return int(input("Give a natural number: "))

'''
    Prints the largest Fibonacci number after the given one in console

    Input: int - representing the Fibonacci number
    Output: -
'''
def print_solution(fibonacci_number):
    print("The Fibonacci number larger than the given one is " + str(fibonacci_number))

'''
    Return the largest Fibonacci number after the given one

    Input: int - representing the number used for searching
    Output: int - representing the found Fibonacci number
'''
def get_fibonacci_number_after(number):
    fibonacci_numbers = [1, 1]

    while fibonacci_numbers[-1] <= number:
        fibonacci_numbers.append(fibonacci_numbers[-1] + fibonacci_numbers[-2])

    return fibonacci_numbers[-1]

number = read_number()
fibonacci_number = get_fibonacci_number_after(number)
print_solution(fibonacci_number)