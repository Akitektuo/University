from datetime import date

class Date:
    day = 0
    month = 0
    year = 0

'''
    Reads from the console the day, month and year of a birth date and returns it as a Date object

    Input: -
    Output: Date - contains the day, month and year
'''
def read_birth_date():
    birth_date = Date()

    birth_date.day = int(input("Give the day of birth: "))
    birth_date.month = int(input("Give the month of birth: "))
    birth_date.year = int(input("Give the year of birth: "))

    return birth_date

'''
    Prints to the console the age in Days

    Input: int - ageInDays representing the age in days
    Output: -
'''
def print_age_in_days(age_in_days):
    if age_in_days < 0:
        print("Birth date can't be in the future")
    else:
        print("Age represented in days is " + str(age_in_days))

'''
    Checks if the birth date is valid or not

    Input: Date - representing the birth date
    Output: bool - true if it is valid, false otherwise
'''
def is_birth_date_valid(birth_date):
    now = date.today()

    if (now.year < birth_date.year):
        return False
    if (now.year == birth_date.year and now.month < birth_date.month):
        return False
    if (now.year == birth_date.year and now.month == birth_date.month and now.day < birth_date.day):
        return False
    
    return True

'''
    Returns the difference of years between the current date and the birth date in days

    Input: Date - representing the birth date
    Output: int - representing the difference of years in days
'''
def get_difference_of_years_in_days(birth_date):
    now = date.today()
    years_in_days = 0

    while birth_date.year != now.year:
        if birth_date.year % 4 == 0:
            years_in_days += 366
        else:
            years_in_days += 365
        birth_date.year += 1
    
    return years_in_days

'''
    Returns the difference of months between the current date and the birth date in days

    Input: Date - representing the birth date
    Output: int - representing the difference of months in days
'''
def get_difference_of_months_in_days(birth_date):
    now = date.today()
    months_in_days = 0
    days_in_month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

    while birth_date.month != now.month:
        months_in_days += days_in_month[birth_date.month - 1]
        birth_date.month += 1
    
    return months_in_days

'''
    Returns the difference of days between the current date and the birth date

    Input: Date - representing the birth date
    Output: int - representing the difference of days
'''
def get_difference_of_days(birth_date):
    now = date.today()

    return now.day - birth_date.day

'''
    Returns the age of a person in days for a given birth date until the present

    Input: Date - representing the birth date
    Output: int - representing the age in days
'''
def get_age_in_days_for_birth_day(birth_date):
    age_in_days = 0

    if not is_birth_date_valid(birth_date):
        return -1
    
    age_in_days += get_difference_of_years_in_days(birth_date)
    age_in_days += get_difference_of_months_in_days(birth_date)
    age_in_days += get_difference_of_days(birth_date)
    
    return age_in_days

birth_date = read_birth_date()
age_in_days = get_age_in_days_for_birth_day(birth_date)
print_age_in_days(age_in_days)