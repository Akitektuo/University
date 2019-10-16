from __future__ import absolute_import
from range import Range
from complex import Complex

# Main class to run the menu and functionalities
class Program:

    # The list of complex numbers of the program
    __complex_numbers = []

    # Init block
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

    '''
        Prints the menu to the console

        Input: -
        Output: -
    '''
    def print_menu(self):
        print("\nMenu")
        print("1. Read a list of complex numbers")
        print("2. Display all the numbers")
        print("3. Remove a complex number")
        print("4. Display the longest sequence with real numbers")
        print("5. Display the longest sequence with the modulus in the [0, 10] range")
        print("6. Exit")

    '''
        Prints a warning that user entered an invalid command

        Input: -
        Output: -
    '''
    def print_invalid_command(self):
        input("\nInvalid command, pres enter to continue...")

    '''
        Converts a string to a Complex object

        Input: string - representing the complex number in the string form 
        Output: Complex - representing the converted complex number
    '''
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

    '''
        Returns characters as spaces for a given length

        Input: int - representing the wanted length of spaces
        Output: string - representing the spaces
    '''
    def generate_spaces_for(self, length):
        space = ""
        for i in range(length):
            space += " "
        return space

    '''
        Adds complex numbers from console and returns the number of added numbers

        Input: -
        Output: int - representing how many numbers were added
    '''
    def add_complex_numbers_from_console(self):
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
        return count

    '''
        Prints the how many numbers were added as a result

        Input: int - representing how many numbers were added
        Output: -
    '''
    def print_add_complex_numbers_result(self, count):
        if count == 0:
            input("\nNo numbers added, press enter to continue...")
            return
        if count == 1:
            count = str(count) + " number"
        else:
            count = str(count) + " numbers"
        input("\n" + str(count) + " added successfully, press enter to continue...")

    '''
        Starts menu functionality for adding complex numbers into the list

        Input: -
        Output: -
    '''
    def menu_read_complex_numbers(self):
        print("\nEnter the numbers below as z=a+bi format, press enter to quit")
        
        count = self.add_complex_numbers_from_console()

        self.print_add_complex_numbers_result(count)

    '''
        Prints how many complex numbers are stored in the program's list

        Input: -
        Output: - 
    '''
    def print_complex_numbers_total(self):
        count = str(len(self.__complex_numbers))
        if count == "1":
            count += " number"
        else:
            count += " numbers"
        input("\nPrinted " + count + ", press enter to continue...")

    '''
        Starts menu functionality for showing all the stored complex numbers

        Input: -
        Output: -
    '''
    def menu_print_complex_numbers(self):
        print()

        if len(self.__complex_numbers) == 0:
            input("No numbers to show, press enter to continue...")
            return
        for i in range(len(self.__complex_numbers)):
            print(str(i + 1) + ". " + str(self.__complex_numbers[i]))
        
        self.print_complex_numbers_total()

    '''
        Prints a list with each complex number and their respectiv id (index in list)

        Input: -
        Output: int - representing the number of printed ids
    '''
    def print_list_of_ids(self):
        print("\nList of complex numbers and ids")
        numbers = len(self.__complex_numbers)

        for i in range(0, numbers // 2 * 2, 2):
            left_number = str(i) + ". " + str(self.__complex_numbers[i])
            space = self.generate_spaces_for(50 - len(left_number))
            print(left_number + space + str(i + 1) + ". " + str(self.__complex_numbers[i + 1]))
        if numbers % 2 == 1:
            print(str(numbers - 1) + ". " + str(self.__complex_numbers[-1]))
        return numbers

    '''
        Removes a complex number by a given id from the console

        Input: int - representing the bound of the last id
        Output: bool - representing if the number was removed or not
    '''
    def remove_complex_number(self, id_limit):
        user_input = input("> ")

        if user_input == "":
            input("No number removed from the list, press enter to continue...")
            return True

        number_id = int(user_input)
        if number_id not in range(id_limit):
            self.print_invalid_command()
            return False
        
        removed_number = self.__complex_numbers[number_id]
        self.__complex_numbers.remove(removed_number)
        input("The complex number " + str(removed_number) + " has been removed, press enter to continue...")
        return True
        
    '''
        Starts menu functionality for removing a number

        Input: -
        Output: -
    '''
    def menu_remove_complex_number(self):
        print("\nType below the id of the complex number that you want to delete")
        print("To cancel, press enter")

        while True:
            numbers = self.print_list_of_ids()
            if self.remove_complex_number(numbers):
                return
    
    '''
        Computes the maximum sequence with a rule and returns a Range

        Input: lambda - receives a Complex object to filter and return a boolean value
        Output: range - representing the maximum sequence with the given rule
    '''
    def compute_sequence(self, with_rule):
        # [start, end)
        max_sequence = Range()
        start = 0 
        end = 0

        for i in range(len(self.__complex_numbers)):
            if(with_rule(self.__complex_numbers[i])):
                if (end != i):
                    start = i
                    end = i + 1
                else:
                    end += 1
                
                if (end - start > max_sequence.get_size()):
                    max_sequence.update(start, end)

        return max_sequence.to_range()

    '''
        Builds a visual representation for a sequence as string

        Input: range - representing the sequence
        Output: string - representing the visual representation
    '''
    def build_string_sequence(self, sequence):
        sequence_string = "("
        for i in sequence:
            sequence_string += str(self.__complex_numbers[i]) + ", "
        sequence_string = sequence_string[:-2]
        sequence_string += ")"
        
        return sequence_string

    '''
        Starts menu functionality for displaying the longest sequence of real numbers

        Input: -
        Output: -
    '''
    def menu_display_real_numbers_sequence(self):
        sequence = self.compute_sequence(lambda cn: cn.is_real())
        
        sequenceString = self.build_string_sequence(sequence)

        print("\nThe longest sequence of real numbers is:\n" + sequenceString)
        input("\nPress enter to continue...")

    '''
        Starts menu functionality for displaying the longest sequence of complex numbers with their modulus in the range of [0, 10]

        Input: -
        Output: -
    '''
    def menu_display_modulus_range_sequence(self):
        rule = lambda cn: cn.get_modulus() >= 0 and cn.get_modulus() <= 10
        sequence = self.compute_sequence(rule)
        
        sequenceString = self.build_string_sequence(sequence)

        print("\nThe longest sequence of numbers with the modulus in the range [0, 10] is:\n" + sequenceString)
        input("\nPress enter to continue...")

    '''
        Starts the program

        Input: -
        Output: -
    '''
    def start(self):
        while True:
            self.print_menu()
            choice = int(input("> "))
            if choice == 1:
                self.menu_read_complex_numbers()
            elif choice == 2:
                self.menu_print_complex_numbers()
            elif choice == 3:
                self.menu_remove_complex_number()
            elif choice == 4:
                self.menu_display_real_numbers_sequence()
            elif choice == 5:
                self.menu_display_modulus_range_sequence()
            elif choice == 6:
                return
            else:
                self.print_invalid_command()