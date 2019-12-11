from table import Table
import os

class Program:

    def __init__(self):
        self.clear = lambda: os.system("cls")
    
    def print_menu(self):
        print("Welcome to Battleships!")
        print("1. Singleplayer")
        print("2. Mulltiplayer")
        print("3. Exit")

    def start_singleplayer(self):
        player_table = Table()
        # while player_table.has_boats_to_place():
        #     self.clear()
        #     player_table.show_remaining_boats()
        #     print()
        #     player_table.show_boats()

        #     start = input("From: ")
        #     end = input("To: ")
        #     player_table.place_boat_input(start, end)

        computer_table = Table()
        computer_table.place_random_boats()
        computer_table.show_boats()
        

    def start(self):
        self.print_menu()
        while True:
            cmd = input("> ")
            if cmd == "1":
                self.start_singleplayer()
                return
            if cmd == "2":
                return
            if cmd == "3":
                return
            print("Invalid command")