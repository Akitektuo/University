from sector import Sector
from random import randint

class Game:

    def __init__(self):
        self.init_grid()
        self.ship = [0, 0]
        self.cruisers = 3
        self.cheat = False
        self.finished = False
    
    def init_grid(self):
        row = []
        for i in range(0, 9):
            row.append(Sector(0))

        self.grid = []
        for i in range(0, 9):
            self.grid.append(list(row))
    
    def init_board(self):
        self.place_stars()
        self.place_ship()
        self.place_cruisers()

    def is_sector_empty(self, i, j):
        if (i < 1 or j < 1 or i > 8 or j > 8):
            return True
        return self.grid[i][j].type == 0

    def is_sector_ship(self, i, j):
        if (i < 1 or j < 1 or i > 8 or j > 8):
            return False
        return self.grid[i][j].type == 2

    def is_sector_empty_on_row(self, i, j):
        if not self.is_sector_empty(i + 1, j):
            return False
        if not self.is_sector_empty(i - 1, j):
            return False
        return True

    def is_sector_empty_on_column(self, i, j):
        if not self.is_sector_empty(i, j - 1):
            return False
        if not self.is_sector_empty(i, j + 1):
            return False
        return True
        
    def is_sector_empty_on_diagonals(self, i, j):
        if not self.is_sector_empty(i - 1, j - 1):
            return False
        if not self.is_sector_empty(i + 1, j + 1):
            return False
        if not self.is_sector_empty(i + 1, j - 1):
            return False
        if not self.is_sector_empty(i - 1, j + 1):
            return False
        return True

    def can_start_be_placed_at(self, i, j):
        if not self.is_sector_empty(i, j):
            return False
        if not self.is_sector_empty_on_row(i, j):
            return False
        if not self.is_sector_empty_on_column(i, j):
            return False
        if not self.is_sector_empty_on_diagonals(i, j):
            return False
        return True

    def is_ship_near(self, i, j):
        if self.is_sector_ship(i - 1, j):
            return True
        if self.is_sector_ship(i + 1, j):
            return True
        if self.is_sector_ship(i, j - 1):
            return True
        if self.is_sector_ship(i, j + 1):
            return True
        if self.is_sector_ship(i - 1, j - 1):
            return True
        if self.is_sector_ship(i - 1, j + 1):
            return True
        if self.is_sector_ship(i + 1, j - 1):
            return True
        if self.is_sector_ship(i + 1, j + 1):
            return True
        return False

    """
    Places all 10 starts randomly in the grid

    Input: none
    Output: none, but modifies the grid
    """
    def place_stars(self):
        stars_to_place = 10
        while stars_to_place != 0:
            i = randint(1, 8)
            j = randint(1, 8)
            if self.can_start_be_placed_at(i, j):
                self.grid[i][j] = Sector(1)
                stars_to_place -= 1

    def place_ship(self):
        while True:
            i = randint(1, 8)
            j = randint(1, 8)
            if self.is_sector_empty(i, j):
                self.grid[i][j] = Sector(2)
                self.ship = [i, j]
                return

    def reset_cruisers_position(self):
        for i in range(1, 9):
            for j in range(1, 9):
                if self.grid[i][j].type == 3:
                    self.grid[i][j].type = 0

    def place_cruisers(self):
        self.reset_cruisers_position()
        cruisers_to_place = self.cruisers
        while cruisers_to_place != 0:
            i = randint(1, 8)
            j = randint(1, 8)
            if self.is_sector_empty(i, j):
                self.grid[i][j] = Sector(3)
                cruisers_to_place -= 1

    def get_sector(self, i, j):
        sector_type = str(self.grid[i][j])
        
        if not self.cheat and sector_type == "B" and not self.is_ship_near(i, j):
            sector_type = " "

        return sector_type

    def is_direction_good(self, i, j):
        if self.ship[0] == i and self.ship[1] == j:
            return False
        if self.ship[0] == i or self.ship[1] == j:
            return True
        if abs(self.ship[0] - i) != abs(self.ship[1] - j):
            return False
        return True

    def is_obstacle(self, i, j):
        if self.ship[0] == i:
            for s in range(i, self.ship[0]):
                if self.grid[s][j].type == 1:
                    return True
        if self.ship[1] == j:
            for s in range(j, self.ship[1]):
                if self.grid[i][s].type == 1:
                    return True
        for s in range(i, self.ship[0]):
            if self.grid[s][s + i - j].type == 1:
                return True
        return False

    def warp(self, i, j):
        if not self.is_direction_good(i, j):
            raise Exception("The direction is wrong, can only move on row, column and diagonals")
        if self.is_obstacle(i, j):
            raise Exception("A star is in the way")
        if self.grid[i][j].type == 3:
            self.finished = True
            raise Exception("Your ship got destroyed")

        self.grid[self.ship[0]][self.ship[1]].type = 0
        self.ship = [i, j]
        self.grid[1][1].type = 2

    def is_shot_near(self, i, j):
        return abs(self.ship[0] - i) < 2 and abs(self.ship[1] - j) < 2

    def fire(self, i, j):
        if not self.is_shot_near(i, j):
            raise Exception("Shot coordinates are too far")
        if self.grid[i][j].type != 3:
            raise Exception("No Blingon cruiser at coordinate")
        self.grid[i][j].type = 0
        self.cruisers -= 1
        if self.cruisers == 0:
            self.finished = True
        else:
            self.place_cruisers()