'''
    Reads from the console a natural number and returns it

    Input: -
    Output: int - representing the natural number 
'''
def read_number():
    return int(input("Give a natural number: "))

'''
    Prints the found twin prime numbers in console

    Input:
        int - representing the first prime number
        int - representing the second prime number
    Output: -
'''
def print_twin_prime_numbers(prime_number_1, prime_number_2):
    print("The twin prime numbers following the given one are " + str(prime_number_1) + " and " + str(prime_number_2))

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
    Returns two twin prime numbers larger than a given number

    Input: int - representing the number
    Output: tuple - representing two prime numbers which are twins
'''
def get_twin_prime_numbers_after(number):
    prime_numbers = get_all_prime_numbers_until(number * 10 + 1)

    for i in range(1, len(prime_numbers)):
        if (prime_numbers[i] - prime_numbers[i - 1] == 2 and prime_numbers[i - 1] > number):
            return (prime_numbers[i - 1], prime_numbers[i])

    return (0, 0)

number = read_number()
prime_numbers = get_twin_prime_numbers_after(number)
print_twin_prime_numbers(prime_numbers[0], prime_numbers[1])