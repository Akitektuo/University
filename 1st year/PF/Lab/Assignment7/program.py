from singleplayer import Singleplayer
from multiplayer import Multiplayer
import os

class Program:
    
    def __init__(self):
        self.clear = lambda: os.system("cls")

    def print_menu(self):
        self.clear()
        print("Welcome to Battleships!")
        print("1. Singleplayer")
        print("2. Mulltiplayer")
        print("3. Exit")

    def start(self):
        self.print_menu()
        while True:
            cmd = input("> ")
            if cmd == "1":
                Singleplayer().start_setup()
                self.print_menu()
                continue
            if cmd == "2":
                Multiplayer().start_setup()
                self.print_menu()
                continue
            if cmd == "3":
                self.clear()
                return
            print("Invalid command")