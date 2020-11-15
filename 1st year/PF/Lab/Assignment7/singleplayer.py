from table import Table
import os

class Singleplayer:

    def __init__(self):
        self.clear = lambda: os.system("cls")
        self.player = Table()

    def place_boats_player(self):
        while self.player.has_boats_to_place():
            self.clear()
            self.player.show_remaining_boats()
            print()
            self.player.show_boats()

            start = input("From: ")
            if start == "":
                return False
            if start == "random":
                self.player.place_random_boats()
                return True
            end = input("To: ")
            if end == "":
                return False
            self.player.place_boat_input(start, end)

        return True

    def select_difficulty(self):
        self.clear()
        print("Select the difficulty")
        print("1. Easy")
        print("2. Normal")
        print("3. Hard")
        print("4. Extreme")

        difficulty = Table.DIFFICULTY_NORMAL
        command = input("> ")
        if command == "1":
            difficulty = Table.DIFFICULTY_EASY
        if command == "3":
            difficulty = Table.DIFFICULTY_HARD
        if command == "4":
            difficulty = Table.DIFFICULTY_EXTREME
        
        self.computer = Table(difficulty)

        input("\nDifficulty set to " + difficulty + ", press enter to continue...")

    def start_setup(self):
        self.select_difficulty()

        if not self.place_boats_player():
            return

        self.clear()
        self.player.show_boats()
        self.computer.place_random_boats()
        input("Game ready, press enter to continue...")
        
        self.start_game()

    def print_boards(self):
        self.clear()
        print("Your board:")
        self.player.show_boats_with_hits()
        print("You have " + str(self.player.hitpoints) + " HP")
        print()
        print("Enemy's board:")
        self.computer.show_hits()
        print("Enemy has " + str(self.computer.hitpoints) + " HP")
        print()

    def confirm(self):
        responce = input("Are  you sure you want to quit the game? (Y/N) ")
        return responce.lower() == "y"

    def ask_player_hit(self):
        print("Your turn...")
        coord = input("Fire at: ")
        if coord == "" and self.confirm():
            raise Exception("Exiting...")

        self.player.hit_input(self.computer, coord)

        self.print_boards()
        print("Your turn...")
        if self.player.last_hit_successful():
            input("Hit!")
        else:
            input("Missed...")

    def computer_hit(self):
        print("Enemy's turn...")
        coordinates = self.computer.compute_hit(self.player)

        self.print_boards()
        print("Enemy's turn...")
        if self.computer.last_hit_successful():
            input("Hit at " + coordinates + "...")
        else:
            input("Missed at " + coordinates + "!")

    def start_game(self):
        player_turn = True
        while self.player.is_alive() and self.computer.is_alive():
            try:
                self.print_boards()

                if player_turn:
                    self.ask_player_hit()
                else:
                    self.computer_hit()
                player_turn = not player_turn
            except Exception as ex:
                if str(ex) == "Exiting...":
                    print("Exiting...")
                    return
        
        print()
        if player_turn:
            print("You lost... ╮( ╯_╰)╭")
        else:
            print("You won! ＼(＾O＾)／")
        print()
        input("Press enter to continue...")

