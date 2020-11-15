import os
from game import Game

class Program:

    map_letter_to_digit = {
        "A": 1,
        "B": 2,
        "C": 3,
        "D": 4,
        "E": 5,
        "F": 6,
        "G": 7,
        "H": 8,
    }

    map_digit_to_letter = {
       1: "A",
       2: "B",
       3: "C",
       4: "D",
       5: "E",
       6: "F",
       7: "G",
       8: "H",
    }

    def __init__(self):
        self.game = Game()

    def clear_console(self):
        os.system("cls")

    def build_header(self):
        return " 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 \n===+===+===+===+===+===+===+===+===\n" 

    def build_intermidiate_line(self):
        return "---+---+---+---+---+---+---+---+---\n"

    def build_row(self, row):
        row_string = " " + Program.map_digit_to_letter[row] + " "
        for i in range(1, 9):
            row_string += "| " + self.game.get_sector(row, i) + " "
        row_string += "\n" + self.build_intermidiate_line()
        return row_string

    def print_board(self):
        self.clear_console()
        board = self.build_header()
        for i in range(1, 9):
            board += self.build_row(i)
        print(board)

    def start(self):
        self.game.init_board()

        self.run()

    def get_command(self):
        raw = input("> ").strip()
        if raw == "":
            raise Exception("Invalid command")

        words = raw.split(" ")
        if len(words) > 2:
            raise Exception("Invalid command")

        if len(words) < 1:
            raise Exception("Invalid command")

        key = words[0].strip()
        if key != "warp" and key != "fire" and key != "cheat" and key != "exit":
            raise Exception("Invalid command")

        if key == "cheat" or key == "exit":
            return key, None

        if len(words) != 2:
            raise Exception("Invalid command")

        coordinate = words[1].strip()
        if len(coordinate) != 2 or coordinate[0] < "A" or coordinate[0] > "H" or coordinate[1] < "1" or coordinate[1] > "8":
            raise Exception("Invalid command")

        return key, coordinate


    def run(self):
        while not self.game.finished:
            try:
                self.print_board()
                command = self.get_command()
                if command[0] == "exit":
                    return

                if command[0] == "cheat":
                    self.game.cheat = not self.game.cheat
                    continue

                if command[0] == "warp":
                    param = command[1]
                    self.game.warp(Program.map_letter_to_digit[param[0]], int(param[1]))
                    continue

                if command[0] == "fire":
                    param = command[1]
                    self.game.fire(Program.map_letter_to_digit[param[0]], int(param[1]))
                    continue

            except Exception as error:
                input("Error: " + str(error))

