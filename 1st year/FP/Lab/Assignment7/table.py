from validator import validate_coordinate_input, validate_coordinates, map_number_to_letter
from domains import Box, Coordinate
import random

class Table:
    DIRECTION_TOP = 0
    DIRECTION_RIGHT = 1
    DIRECTION_BOTTOM = 2
    DIRECTION_LEFT = 3

    def __init__(self):
        self.height = 10
        self.width = 10
        self.init_boxes()
        self.unplaced_boats = [5, 4, 3, 3, 2, 2, 2]

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

    def place_boat(self, start, end):
        if (start.x > end.x):
            start.x ^= end.x
            end.x ^= start.x
            start.x ^= end.x

        if (start.y > end.y):
            start.y ^= end.y
            end.y ^= start.y
            start.y ^= end.y

        if start.x == end.x:
            for y in range(start.y, end.y + 1):
                if (self.boxes[start.x][y].is_boat):
                    raise Exception("Boat already exists at " + str(start.x) + map_number_to_letter[y])
                self.boxes[start.x][y].is_boat = True
            return
        if start.y == end.y:
            for x in range(start.x, end.x + 1):
                if (self.boxes[x][start.y].is_boat):
                    raise Exception("Boat already exists at " + str(x) + map_number_to_letter[start.y])
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

    def hit(self, x, y):
        self.boxes[x][y].is_hit = True

    def draw_boat(self, boat_size):
        boat_str = ""
        for _ in range(boat_size - 1):
            boat_str += '='
        boat_str += '>'
        return boat_str
    
    def show_remaining_boats(self):
        print("You have " + str(len(self.unplaced_boats)) + " boat(s) to place")
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
            
            x = random.randint(0, 9)
            y = random.randint(0, 9)
            direction = random.randint(0, 3)

            try:
                if direction == Table.DIRECTION_TOP:
                    if (y > boat):
                        self.place_boat(Coordinate(x, y - boat + 1), Coordinate(x, y))
                        i += 1
                    continue
                        
                if direction == Table.DIRECTION_RIGHT:
                    if (10 - x >= boat):
                        self.place_boat(Coordinate(x, y), Coordinate(x + boat - 1, y))
                        i += 1
                    continue
                        
                if direction == Table.DIRECTION_BOTTOM:
                    if (10 - y >= boat):
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

# table = Table()
# table.place_boat(Coordinate(0, 0), Coordinate(0, 3))
# table.hit(1, 0)
# table.hit(0, 4)
# table.hit(1, 4)
# table.hit(1, 3)
# table.hit(0, 0)
# table.hit(0, 4)
# table.show_hits()
# table.show_remaining_boats()
