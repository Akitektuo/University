'''
    Reads from the console a number of days of a year

    Input: -
    Output: int - representing the number of days
'''
def read_day_number():
    return int(input("Give a day number: "))

'''
    Reads from the console a year

    Input: -
    Output: int - representing the year
'''
def read_year():
    return int(input("Give a year: "))

'''
    Prints to the console a given date

    Input:
        string - representing the concatenated day and month
        int - representing a year
    Output: -
'''
def print_date(day_and_month, year):
    if day_and_month == "":
        print("Day number exceeds the total number of days in the year " + str(year))
    else:
        print(day_and_month + " " + str(year))

'''
    Formats a day by adding an ordinal indicator

    Input: int - representing the day that will be formated
    Output: string - representing the formated day
'''
def format_day(day):
    if day % 10 == 1:
        return str(day) + "st"
    if day % 10 == 2:
        return str(day) + "nd"
    if day % 10 == 3:
        return str(day) + "rd"
    return str(day) + "th"

'''
    Computes a formated date by a given number of days and a year

    Input:
        int - representing the number of days of a year
        int - representing the year
    Output: string - representing the concatenated found day and month
'''
def compute_day_and_month(day_number, year):
    total_number_of_days = 365
    days_in_month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]

    if year % 4 == 0:
        days_in_month[1] += 1
        total_number_of_days += 1
    if day_number > total_number_of_days:
        return ""
    for i in range(len(months)):
        if day_number < days_in_month[i]:
            return months[i] + " " + format_day(day_number)
        day_number -= days_in_month[i]

day_number = read_day_number()
year = read_year()
day_and_month = compute_day_and_month(day_number, year)
print_date(day_and_month, year)