from repository import Repository
from services import Services

class UI:

    def __init__(self):
        self.services = Services()
    
    def print_menu(self):
        print("Welcome to Taxi Manager! Enter 0 for help...")
        print("1. Show all the known addresses")
        print("2. Show all the known drivers")
        print("3. Show clossest drivers from an address")
        print("4. Show clossest drivers")
        print("5. Exit")

    def get_address(self):
        return input("Address name: ")

    def start(self):
        self.print_menu()
        while True:
            try:
                command = input("> ")
                print()

                if command == "0":
                    self.print_menu()
                    continue

                if command == "1":
                    print(self.services.build_addresses())
                    continue

                if command == "2":
                    print(self.services.build_drivers())
                    continue

                if command == "3":
                    address = self.get_address()
                    print(self.services.get_closest_drivers_for_address(address))
                    continue

                if command == "4":
                    print(self.services.get_closest_drivers())
                    continue

                if command == "5":
                    return

                print("Invalid command")
            except Exception as error:
                print("Error: " + str(error))

UI().start()