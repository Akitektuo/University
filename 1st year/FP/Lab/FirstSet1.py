import math

def readNumber():
    return int(input("Give a natural number: "))

def printSolution(number):
    print("The first prime number larger than the given one is " + str(number))

def isPrime(number):
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

def findNextPrimeNumber(number):
    number += 1
    while(not isPrime(number)):
        number += 1
    return number

num = readNumber()
res = findNextPrimeNumber(num)
printSolution(res)