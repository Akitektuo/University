from validator import validate_coordinate_input, validate_coordinates, map_number_to_letter
from domains import Box, Coordinate, HitEvent
import random

class Table:
    DIRECTION_TOP = 0
    DIRECTION_RIGHT = 1
    DIRECTION_BOTTOM = 2
    DIRECTION_LEFT = 3

    DIFFICULTY_EASY = "easy"
    DIFFICULTY_NORMAL = "normal"
    DIFFICULTY_HARD = "hard"
    DIFFICULTY_EXTREME = "extreme"

    def __init__(self, difficulty = DIFFICULTY_NORMAL):
        self.height = 10
        self.width = 10
        self.init_boxes()
        self.unplaced_boats = [5, 4, 3, 3, 2, 2, 2]
        self.hitpoints = sum(self.unplaced_boats)
        self.hit_history = []
        self.difficulty = difficulty

    def init_boxes(self):
        self.boxes = []
        for _ in range(self.height):
            row = []
            for _ in range(self.width):
                row.append(Box())
            self.boxes.append(row)

    def generate_header(self):
        header = "     "
        for i in range(self.width):
            header += "|  " + map_number_to_letter[i] + "  "
        header += "|     "
        return header

    def generate_row_divider(self):
        divider = ""
        for _ in range(self.width + 2):
            divider += "-----+"
        return divider[:-1]

    def get_symbol(self, i, j):
        box = self.boxes[i][j]
        
        if box.is_hit and box.is_boat:
            return 'X'
        if box.is_boat:
            return '#'
        if box.is_hit:
            return '*'
        return ' '

    def get_hit_symbol(self, i, j):
        box = self.boxes[i][j]
        if not box.is_hit:
            return ' '
        if box.is_boat:
            return 'X'
        return '*'

    def get_boat_symbol(self, i, j):
        box = self.boxes[i][j]
        if box.is_boat:
            return '#'
        return ' '

    def check_boats(self, start, end):
        if start.x == end.x:
            for y in range(start.y, end.y + 1):
                if (self.boxes[start.x][y].is_boat):
                    return Coordinate(start.x, y)
            return
        if start.y == end.y:
            for x in range(start.x, end.x + 1):
                if (self.boxes[x][start.y].is_boat):
                    return Coordinate(x, start.y)
            return

    def place_boat(self, start, end):
        if (start.x > end.x):
            start.x ^= end.x
            end.x ^= start.x
            start.x ^= end.x

        if (start.y > end.y):
            start.y ^= end.y
            end.y ^= start.y
            start.y ^= end.y

        overlap = self.check_boats(start, end)
        if overlap:
            raise Exception("Boat is overlapping at " + map_number_to_letter[overlap.y] + str(overlap.x))

        if start.x == end.x:
            for y in range(start.y, end.y + 1):
                self.boxes[start.x][y].is_boat = True
            return
    
        if start.y == end.y:
            for x in range(start.x, end.x + 1):
                self.boxes[x][start.y].is_boat = True

    def show_hits(self):
        table_str = self.generate_header() + "\n"
        table_str += self.generate_row_divider() + "\n"
        for i in range(self.height):
            table_str += "  " + str(i) + "  "
            for j in range(self.width):
                table_str += "|  " + self.get_hit_symbol(i, j) + "  "
            table_str += "|  " + str(i) + "  \n"
            table_str += self.generate_row_divider() + "\n"
        table_str += self.generate_header() + "\n"
        print(table_str)

    def show_boats(self):
        table_str = self.generate_header() + "\n"
        table_str += self.generate_row_divider() + "\n"
        for i in range(self.height):
            table_str += "  " + str(i) + "  "
            for j in range(self.width):
                table_str += "|  " + self.get_boat_symbol(i, j) + "  "
            table_str += "|  " + str(i) + "  \n"
            table_str += self.generate_row_divider() + "\n"
        table_str += self.generate_header() + "\n"
        print(table_str)

    def show_boats_with_hits(self):
        table_str = self.generate_header() + "\n"
        table_str += self.generate_row_divider() + "\n"
        for i in range(self.height):
            table_str += "  " + str(i) + "  "
            for j in range(self.width):
                table_str += "|  " + self.get_symbol(i, j) + "  "
            table_str += "|  " + str(i) + "  \n"
            table_str += self.generate_row_divider() + "\n"
        table_str += self.generate_header() + "\n"
        print(table_str)

    def is_alive(self):
        return self.hitpoints > 0

    def record_hit(self, coordinates):
        x = coordinates.x
        y = coordinates.y

        self.boxes[x][y].is_hit = True
        if not self.boxes[x][y].is_boat:
            return False
        self.hitpoints -= 1
        return True

    def hit(self, other, coordinates):
        self.hit_history.append(HitEvent(coordinates, other.record_hit(coordinates)))

    def is_hit_at(self, coordinates):
        for hit in self.hit_history:
            if hit.coordinates.equals(coordinates):
                return hit

    def last_hit_successful(self):
        return self.hit_history[-1].successful

    def check_explored(self, hit):
        coordinates = hit.coordinates
        x = coordinates.x
        y = coordinates.y

        top_coord = Coordinate(x, y - 1)
        right_coord = Coordinate(x + 1, y)
        bottom_coord = Coordinate(x, y + 1)
        left_coord = Coordinate(x - 1, y)
        
        if top_coord.y > -1 and not self.is_hit_at(top_coord):
            return top_coord
        
        if right_coord.x < self.width and not self.is_hit_at(right_coord):
            return right_coord
        
        if bottom_coord.y < self.height and not self.is_hit_at(bottom_coord):
            return bottom_coord
        
        if left_coord.x > -1 and not self.is_hit_at(left_coord):
            return left_coord

    def get_first_unexplored_hit(self):
        for hit in self.hit_history:
            if not hit.successful:
                continue
            next_position = self.check_explored(hit)
            if next_position:
                return next_position

    def get_random_coordinate(self):
        x = random.randrange(0, self.width)
        y = random.randrange(0, self.height)
        return Coordinate(x, y)

    def coordinate_to_str(self, coordinate):
        return map_number_to_letter[coordinate.y] + str(coordinate.x)

    def get_strategic_coordinate(self, other):
        for x in range(self.height):
            for y in range(self.width):
                if x % 2 == y % 2 or other.boxes[x][y].is_hit:
                    continue
                return Coordinate(x, y)

    def get_exact_coordinate(self, other):
        for x in range(self.height):
            for y in range(self.width):
                if other.boxes[x][y].is_boat and not other.boxes[x][y].is_hit:
                    return Coordinate(x, y)

    def compute_easy_hit(self, other):
        coordinate = self.get_random_coordinate()
        while self.is_hit_at(coordinate):
            coordinate = self.get_random_coordinate()

        self.hit(other, coordinate)
        return self.coordinate_to_str(coordinate)

    def compute_normal_hit(self, other):
        computed_hit = self.get_first_unexplored_hit()
        if computed_hit:
            self.hit(other, computed_hit)
            return self.coordinate_to_str(computed_hit)
        
        coordinate = self.get_random_coordinate()
        while self.is_hit_at(coordinate):
            coordinate = self.get_random_coordinate()

        self.hit(other, coordinate)
        return self.coordinate_to_str(coordinate)

    def compute_hard_hit(self, other):
        computed_hit = self.get_first_unexplored_hit()
        if computed_hit:
            self.hit(other, computed_hit)
            return self.coordinate_to_str(computed_hit)
        
        coordinate = self.get_strategic_coordinate(other)

        self.hit(other, coordinate)
        return self.coordinate_to_str(coordinate)

    def compute_extreme_hit(self, other):
        coordinate = self.get_exact_coordinate(other)

        self.hit(other, coordinate)
        return self.coordinate_to_str(coordinate)

    def compute_hit(self, other):
        if self.difficulty == Table.DIFFICULTY_EASY:
            return self.compute_easy_hit(other)
        if self.difficulty == Table.DIFFICULTY_NORMAL:
            return self.compute_normal_hit(other)
        if self.difficulty == Table.DIFFICULTY_HARD:
            return self.compute_hard_hit(other)
        if self.difficulty == Table.DIFFICULTY_EXTREME:
            return self.compute_extreme_hit(other)

    def hit_input(self, other, coord):
        coord = validate_coordinate_input(coord)
        if self.is_hit_at(coord):
            raise Exception("You already fired at this coordinate")
        self.hit(other, coord)

    def draw_boat(self, boat_size):
        boat_str = ""
        for _ in range(boat_size - 1):
            boat_str += '='
        boat_str += '>'
        return boat_str
    
    def show_remaining_boats(self):
        print("You have " + str(len(self.unplaced_boats)) + " boat(s) to place, to quit the game at anytime press enter")
        for boat in self.unplaced_boats:
            print(self.draw_boat(boat))

    def has_boats_to_place(self):
        return len(self.unplaced_boats) > 0

    def place_boat_input(self, start, end):
        try:
            start = validate_coordinate_input(start)
            end = validate_coordinate_input(end)
            boat = validate_coordinates(start, end, self.unplaced_boats)

            self.place_boat(start, end)
            self.unplaced_boats.remove(boat)
        except Exception as error:
            input("Error: " + str(error) + ", press enter to continue...")

    def place_random_boats(self):
        i = 0
        while i < len(self.unplaced_boats):
            boat = self.unplaced_boats[i]
            
            x = random.randrange(0, self.width)
            y = random.randrange(0, self.height)
            direction = random.randint(0, 3)

            try:
                if direction == Table.DIRECTION_TOP:
                    if (y > boat):
                        self.place_boat(Coordinate(x, y - boat + 1), Coordinate(x, y))
                        i += 1
                    continue
                        
                if direction == Table.DIRECTION_RIGHT:
                    if (self.width - x >= boat):
                        self.place_boat(Coordinate(x, y), Coordinate(x + boat - 1, y))
                        i += 1
                    continue
                        
                if direction == Table.DIRECTION_BOTTOM:
                    if (self.height - y >= boat):
                        self.place_boat(Coordinate(x, y), Coordinate(x, y + boat - 1))
                        i += 1
                    continue
                        
                if direction == Table.DIRECTION_LEFT:
                    if (x > boat):
                        self.place_boat(Coordinate(x - boat + 1, y), Coordinate(x, y))
                        i += 1
                    continue
            except:
                continue
