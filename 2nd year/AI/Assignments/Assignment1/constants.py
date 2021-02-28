class Color:
    BLUE = (0, 0, 255)
    GRAY_BLUE = (50, 120, 120)
    RED = (255, 0, 0)
    GREEN = (0, 255, 0)
    BLACK = (0, 0, 0)
    WHITE = (255, 255, 255)


class Direction:
    UP = 0
    LEFT = 1
    DOWN = 2
    RIGHT = 3
    DIRECTIONS = [[-1, 0], [1, 0], [0, 1], [0, -1]]


class Status:
    UNKNOWN = -1
    EMPTY = 0
    WALL = 1
