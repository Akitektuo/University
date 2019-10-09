from decimal import Decimal

class Complex:

    def __init__(self, real = 0, imaginary = 0):
        self.real = real
        self.imaginary = imaginary
    # use __str()
    def to_string(self):
        if self.imaginary == 0:
            return self.format_float(self.real)
        if self.real == 0:
            return self.format_float(self.imaginary, False) + "i"
        if (self.imaginary < 0):
            return self.format_float(self.real) + " - " + self.format_float(self.imaginary, False) + "i"
        return self.format_float(self.real) + " + " + self.format_float(self.imaginary, False) + "i"

    def format_float(self, number, show_1 = True):
        number = round(number, 2)
        if (show_1 and number == 1):
            return ""
        if number == int(number):
            return str(int(number))
        return str(number)

class Program:

    __complex_numbers = []

    def print_menu(self):
        print("\nMenu")
        print("1. Read a list of complex numbers")
        print("2. Display all the numbers")
        print("3. Display the longest sequence with real numbers")
        print("4. Display the longest sequence with the modulus in the [0, 10] range")
        print("5. Exit")

    def print_invalid_command(self):
        input("\nInvalid command, pres enter to continue...")

    def get_complex_number_from(self, string):
        if 'i' not in string:
            return
        string = string[: -1]
        if '+' in string:
            parts = string.split(" + ")
            return Complex(float(parts[0]), float(parts[1]))
        if '-' in string:
            parts = string.split(" - ")
            return Complex(float(parts[0]), -float(parts[1]))

    def read_complex_numbers(self):
        print("\nEnter the numbers below as z = a + bi format, press enter to quit")
        
        count = 0
        user_input = input("z = ")
        while user_input != "":
            complex_number = self.get_complex_number_from(user_input)
            if (complex_number == None):
                self.print_invalid_command()
            else:
                self.__complex_numbers.append(complex_number)
                count += 1
            user_input = input("z = ")

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
            print(str(i + 1) + ". " + self.__complex_numbers[i].to_string())
        
        count = str(len(self.__complex_numbers))
        if count == "1":
            count += " number"
        else:
            count += " numbers"

        input("\nPrinted " + count + ", press enter to continue...")

    def start(self):
        while True:
            self.print_menu()
            choice = int(input("> "))
            if choice == 1:
                self.read_complex_numbers()
            elif choice == 2:
                self.print_complex_numbers()
                pass
            elif choice == 3:
                pass
            elif choice == 4:
                pass
            elif choice == 5:
                return
            else:
                self.print_invalid_command()

Program().start()