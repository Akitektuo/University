from table import Table
import os

class Multiplayer:

    def __init__(self):
        self.clear = lambda: os.system("cls")
        self.player1 = Table()
        self.player2 = Table()

    def place_boats_player(self, player):
        while player.has_boats_to_place():
            self.clear()
            player.show_remaining_boats()
            print()
            player.show_boats()

            start = input("From: ")
            if start == "":
                return False
            if start == "random":
                player.place_random_boats()
                return True
            end = input("To: ")
            if end == "":
                return False
            player.place_boat_input(start, end)

        return True

    def start_setup(self):
        input("Player 1, set your boats, press enter to continue...")
        if not self.place_boats_player(self.player1):
            return
        self.clear()
        self.player1.show_boats()
        input("These are your placed boats, press enter to continue...")

        self.clear()
        input("Player 2, set your boats, press enter to continue...")
        if not self.place_boats_player(self.player2):
            return
        self.clear()
        self.player2.show_boats()
        input("These are your placed boats, press enter to continue...")

        input("Game ready, press enter to continue...")
        
        self.start_game()

    def print_board(self, player):
        self.clear()
        player.show_hits()
        print("Player 1 has " + str(self.player1.hitpoints) + " HP")
        print("Player 2 has " + str(self.player2.hitpoints) + " HP")
        print()

    def confirm(self):
        responce = input("Are  you sure you want to quit the game? (Y/N) ")
        return responce.lower() == "y"

    def ask_player_hit(self, fires, at):
        coord = input("Fire at: ")
        if coord == "" and self.confirm():
            raise Exception("Exiting...")

        fires.hit_input(at, coord)

        self.print_board(at)
        if fires.last_hit_successful():
            input("Hit!")
        else:
            input("Missed...")

    def start_game(self):
        player_turn = True
        while self.player1.is_alive() and self.player2.is_alive():
            try:

                if player_turn:
                    self.print_board(self.player2)
                    print("Player 1's turn...")
                    self.ask_player_hit(self.player1, self.player2)
                else:
                    self.print_board(self.player1)
                    print("Player 2's turn...")
                    self.ask_player_hit(self.player2, self.player1)

                player_turn = not player_turn
            except Exception as ex:
                if str(ex) == "Exiting...":
                    print("Exiting...")
                    return
        
        print()
        if player_turn:
            print("Player 2 won! ＼(＾O＾)／")
        else:
            print("Player 1 won! ＼(＾O＾)／")
        print()
        input("Press enter to continue...")

