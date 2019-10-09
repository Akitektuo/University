import math

# [start, end)
class Range:

    def __init__(self, start = 0, end = 0):
        self.start = start
        self.end = end

    def __str__(self):
        return "[" + str(self.start) + ", " + str(self.end) + ")"
    
    def get_size(self):
        return self.end - self.start

    def update(self, start, end):
        if start > end:
            self.start = end
            self.end = start
            return
        self.start = start
        self.end = end

    def to_range(self):
        return range(self.start, self.end)

class Complex:

    def __init__(self, real = 0, imaginary = 0):
        self.real = real
        self.imaginary = imaginary

    def __str__(self):
        if self.imaginary == 0:
            return self.format_float(self.real)
        if self.real == 0:
            if self.imaginary == -1:
                return "-i"
            return self.format_float(self.imaginary, False) + "i"
        if (self.imaginary < 0):
            return self.format_float(self.real) + "-" + self.format_float(-self.imaginary, False) + "i"
        return self.format_float(self.real) + "+" + self.format_float(self.imaginary, False) + "i"

    def format_float(self, number, show_1 = True):
        number = round(number, 2)
        if (not show_1 and number == 1):
            return ""
        if number == int(number):
            return str(int(number))
        return str(number)

    def is_real(self):
        if self.imaginary == 0:
            return True
        return False

    def get_modulus(self):
        return math.sqrt(self.real**2 + self.imaginary**2)

class Program:

    __complex_numbers = []

    def __init__(self):
        self.__complex_numbers.append(Complex(1, 2))
        self.__complex_numbers.append(Complex(0, 0))
        self.__complex_numbers.append(Complex(3, 0))
        self.__complex_numbers.append(Complex(2.375, 4))
        self.__complex_numbers.append(Complex(5, 8.76666))
        self.__complex_numbers.append(Complex(6.808, 7.420))
        self.__complex_numbers.append(Complex(65423, 7665))
        self.__complex_numbers.append(Complex(23424, 1))
        self.__complex_numbers.append(Complex(75, -25))
        self.__complex_numbers.append(Complex(0, -1))

    def print_menu(self):
        print("\nMenu")
        print("1. Read a list of complex numbers")
        print("2. Display all the numbers")
        print("3. Remove a complex number")
        print("4. Display the longest sequence with real numbers")
        print("5. Display the longest sequence with the modulus in the [0, 10] range")
        print("6. Exit")

    def print_invalid_command(self):
        input("\nInvalid command, pres enter to continue...")

    def get_complex_number_from(self, string):
        if 'i' not in string:
            return
        string = string[: -1]
        if '+' in string:
            parts = string.split("+")
            return Complex(float(parts[0]), float(parts[1]))
        if '-' in string:
            parts = string.split("-")
            return Complex(float(parts[0]), -float(parts[1]))

    def generate_spaces_for(self, length):
        space = ""
        for i in range(length):
            space += " "
        return space

    def read_complex_numbers(self):
        print("\nEnter the numbers below as z=a+bi format, press enter to quit")
        
        count = 0
        user_input = input("z=")
        while user_input != "":
            complex_number = self.get_complex_number_from(user_input)
            if (complex_number == None):
                self.print_invalid_command()
            else:
                self.__complex_numbers.append(complex_number)
                count += 1
            user_input = input("z=")

        if count == 0:
            input("\nNo numbers added, press enter to continue...")
            return
        if count == 1:
            count = str(count) + " number"
        else:
            count = str(count) + " numbers"
        input("\n" + str(count) + " added successfully, press enter to continue...")

    def print_complex_numbers(self):
        print()

        if len(self.__complex_numbers) == 0:
            input("No numbers to show, press enter to continue...")
            return

        for i in range(len(self.__complex_numbers)):
            print(str(i + 1) + ". " + str(self.__complex_numbers[i]))
        
        count = str(len(self.__complex_numbers))
        if count == "1":
            count += " number"
        else:
            count += " numbers"

        input("\nPrinted " + count + ", press enter to continue...")

    def remove_complex_number(self):
        print("\nType below the id of the complex number that you want to delete")
        print("To cancel, press enter")

        while True:
            print("\nList of complex numbers and ids")
            numbers = len(self.__complex_numbers)

            for i in range(0, numbers // 2 * 2, 2):
                left_number = str(i) + ". " + str(self.__complex_numbers[i])
                space = self.generate_spaces_for(50 - len(left_number))
                print(left_number + space + str(i + 1) + ". " + str(self.__complex_numbers[i + 1]))
            if numbers % 2 == 1:
                print(str(numbers - 1) + ". " + str(self.__complex_numbers[-1]))

            user_input = input("> ")
            if user_input == "":
                input("No number removed from the list, press enter to continue...")
                return
            number_id = int(user_input)
            if number_id not in range(numbers):
                self.print_invalid_command()
            else:
                removed_number = self.__complex_numbers[number_id]
                self.__complex_numbers.remove(removed_number)
                input("The complex number " + str(removed_number) + " has been removed, press enter to continue...")
                return
    
    def computeSequence(self, withRule):
        # [start, end)
        maxSequence = Range()
        start = 0 
        end = 0

        for i in range(len(self.__complex_numbers)):
            if(withRule(self.__complex_numbers[i])):
                if (end != i):
                    start = i
                    end = i + 1
                else:
                    end += 1
                if (end - start > maxSequence.get_size()):
                    maxSequence.update(start, end)

        return maxSequence

    def displayRealNumbersSequence(self):
        sequence = self.computeSequence(lambda cn: cn.is_real())
        
        sequenceString = "("
        for i in sequence.to_range():
            sequenceString += str(self.__complex_numbers[i]) + ", "
        sequenceString = sequenceString[:-2]
        sequenceString += ")"

        print("\nThe longest sequence of real numbers is:\n" + sequenceString)
        input("\nPress enter to continue...")

    def displayModulusRangeSequence(self):
        rule = lambda cn: cn.get_modulus() > 0 and cn.get_modulus() < 10
        sequence = self.computeSequence(rule)
        
        sequenceString = "("
        for i in sequence.to_range():
            sequenceString += str(self.__complex_numbers[i]) + ", "
        sequenceString = sequenceString[:-2]
        sequenceString += ")"

        print("\nThe longest sequence of numbers with the modulus in the range [0, 10] is:\n" + sequenceString)
        input("\nPress enter to continue...")

    def start(self):
        while True:
            self.print_menu()
            choice = int(input("> "))
            if choice == 1:
                self.read_complex_numbers()
            elif choice == 2:
                self.print_complex_numbers()
            elif choice == 3:
                self.remove_complex_number()
            elif choice == 4:
                self.displayRealNumbersSequence()
            elif choice == 5:
                self.displayModulusRangeSequence()
            elif choice == 6:
                return
            else:
                self.print_invalid_command()

Program().start()