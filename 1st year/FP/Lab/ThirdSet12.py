from datetime import date

class Date:
    day = 0
    month = 0
    year = 0

'''
    Reads from the console the day, month and year of a birth date and returns it as a Date object

    Input: -
    Output: a Date object containing the day, month and year
'''
def readBirthDate():
    birthDate = Date()

    birthDate.day = int(input("Give the day of birth: "))
    birthDate.month = int(input("Give the month of birth: "))
    birthDate.year = int(input("Give the year of birth: "))

    return birthDate

'''
    Prints to the console the age in Days

    Input: ageInDays representing the age in days
    Output: -
'''
def printAgeInDays(ageInDays):
    if ageInDays < 0:
        print("Birth date can't be in the future")
    else:
        print("Age represented in days is " + str(ageInDays))

'''
    Checks if the birth date is valid or not

    Input: birthDate representing a Date object
    Output: True if it is valid, false if it is not valid
'''
def isBirthDateValid(birthDate):
    now = date.today()

    if (now.year < birthDate.year):
        return False
    if (now.year == birthDate.year and now.month < birthDate.month):
        return False
    if (now.year == birthDate.year and now.month == birthDate.month and now.day < birthDate.day):
        return False
    
    return True


'''
    Returns the difference of years between the current date and the birth date in days
'''
def getDifferenceOfYearsInDays(birthDate):
    now = date.today()
    yearsInDays = 0

    while birthDate.year != now.year:
        if birthDate.year % 4 == 0:
            yearsInDays += 366
        else:
            yearsInDays += 365
        birthDate.year += 1
    
    return yearsInDays

def getDifferenceOfMonthsInDays(birthDate):
    now = date.today()
    monthsInDays = 0
    daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

    while birthDate.month != now.month:
        monthsInDays += daysInMonth[birthDate.month - 1]
        birthDate.month += 1
    
    return monthsInDays

def getDifferenceOfDays(birthDate):
    now = date.today()

    return now.day - birthDate.day

def getAgeInDaysForBirthDay(birthDate):
    ageInDays = 0

    if not isBirthDateValid(birthDate):
        return -1
    
    ageInDays += getDifferenceOfYearsInDays(birthDate)
    ageInDays += getDifferenceOfMonthsInDays(birthDate)
    ageInDays += getDifferenceOfDays(birthDate)
    
    return ageInDays

birthDate = readBirthDate()
ageInDays = getAgeInDaysForBirthDay(birthDate)
printAgeInDays(ageInDays)