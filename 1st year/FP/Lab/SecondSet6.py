def readDayNumber():
    return int(input("Give a day number: "))

def readYear():
    return int(input("Give a year: "))

def printDate(dayAndMonth, year):
    if dayAndMonth == "":
        print("Day number exceeds the total number of days in the year " + str(year))
    else:
        print(dayAndMonth + " " + str(year))

def formatDay(day):
    if day % 10 == 1:
        return str(day) + "st"
    if day % 10 == 2:
        return str(day) + "nd"
    if day % 10 == 3:
        return str(day) + "rd"
    return str(day) + "th"

def computeDayAndMonth(dayNumber, year):
    totalNumberOfDays = 365
    daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    if year % 4 == 0:
        daysInMonth[1] += 1
        totalNumberOfDays += 1
    if dayNumber > totalNumberOfDays:
        return ""
    for i in range(len(months)):
        if dayNumber < daysInMonth[i]:
            return months[i] + " " + formatDay(dayNumber)
        dayNumber -= daysInMonth[i]

dayNumber = readDayNumber()
year = readYear()
dayAndMonth = computeDayAndMonth(dayNumber, year)
printDate(dayAndMonth, year)