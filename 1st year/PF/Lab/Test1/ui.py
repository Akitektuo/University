from services import *

def show_menu():
    print("Welcome to Airport app. Press 0 at anytime to show the menu")
    print("1. Add a new flight")
    print("2. Modify the duration of a flight")
    print("3. Reroute a flight")
    print("4. Show all flights") # This is wrong, should only show flights with a specific departure city
    print("5. Exit")

def print_error(error):
    print("Error: " + str(error))

def get_add_params():
    code = input("Give code: ")
    duration = input("Give duration: ")
    departure_city = input("Give departure city: ")
    destination_city = input("Give destination city: ")
    return [code, duration, departure_city, destination_city]

def get_modify_params():
    code = input("Give existing code: ")
    duration = input("Give new duration: ")
    return [code, duration]

def get_reroute_params():
    old_destination = input("Give old destination: ")
    new_destination = input("Give new destination: ")
    return [old_destination, new_destination]

def start():
    show_menu()
    flights = preload_flights()

    while True:
        try:
            command = input("\n> ")

            if command == "0":
                show_menu()
                continue

            if command == "1":
                params = get_add_params()
                add(flights, params[0], params[1], params[2], params[3])
                print("Operation successful")
                continue

            if command == "2":
                params = get_modify_params()
                modify_duration(flights, params[0], params[1])
                print("Operation successful")
                continue

            if command == "3":
                params = get_reroute_params()
                reroute(flights, params[0], params[1])
                print("Operation successful")
                continue

            if command == "4":
                print(get_all_flights(flights))
                continue

            if command == "5":
                return
        except Exception as error:
            print_error(error)
    