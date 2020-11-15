'''
    Reads from the console a desired sum and returns it

    Input: -
    Output: int - representing the desired sum 
'''
def read_desired_sum():
    return int(input("Give the desired sum: "))

'''
    Prints the given prime numbers through the params

    Input:
        int - representing the first prime number
        int - representing the second prime number
    Output: - 
'''
def print_prime_numbers(prime_number_1, prime_number_2):
    if prime_number_1 < 2 or prime_number_2 < 2:
        print("No prime numbers found to add up to the given sum")
    else:
        print("The prime numbers " + str(prime_number_1) + " + " + str(prime_number_2) + " give the desired sum: " + str(prime_number_1 + prime_number_2))

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
    Returns the two prime numbers that add up to the desired sum

    Input: int - representing the desired sum
    Output: tuple - representing two prime numbers
'''
def determine_prime_numbers_for_sum(desired_sum):
    prime_numbers = get_all_prime_numbers_until(desired_sum)

    for i in range(len(prime_numbers)):
        if prime_numbers[i] > desired_sum // 2:
            break
        wanted_number = desired_sum - prime_numbers[i]
        if wanted_number in prime_numbers:
            return (prime_numbers[i], wanted_number)

    return (0, 0)

desired_sum = read_desired_sum()
prime_numbers = determine_prime_numbers_for_sum(desired_sum)
print_prime_numbers(prime_numbers[0], prime_numbers[1])