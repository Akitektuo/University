'''
    Reads from the console a natural number and returns it

    Input: -
    Output: int - representing the natural number 
'''
def read_number():
    return int(input("Give a natural number: "))

'''
    Prints the product of proper factors of the given number in console

    Input: int - representing the product of proper factors
    Output: -
'''
def print_solution(product):
    print("The product of proper factors of the given number is " + str(product))

'''
    Returns all the prime numbers until a given limit

    Input:
        int - representing the limit
        (optional) bool - decides whether or not the limit is included, default is "True"
    Output: list - including all the prime numbers until the given limit
'''
def get_all_prime_numbers_until(limit, inclusive = True):
    prime_numbers = []

    if inclusive:
        limit += 1
    prime = [True for i in range(limit)]

    for potential_prime_number in range(2, limit):
        if prime[potential_prime_number]:
            prime_numbers.append(potential_prime_number)
            for i in range(potential_prime_number * potential_prime_number, limit, potential_prime_number):
                prime[i] = False

    return prime_numbers

'''
    Computes and returns the product of proper factors of the given number

    Input: int - representing the number
    Output: int - representing the product of proper factors
'''
def get_proper_factors_product(number):
    prime_numbers = get_all_prime_numbers_until(number)

    product = 1
    for prime_number in prime_numbers:
        if number % prime_number == 0:
            product *= prime_number
    if product < 2:
        product = 0

    return product

number = read_number()
product = get_proper_factors_product(number)
print_solution(product)