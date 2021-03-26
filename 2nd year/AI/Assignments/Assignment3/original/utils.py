from random import randrange

BLUE = (0, 0, 255)
GRAY_BLUE = (50, 120, 120)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

UP = 0
RIGHT = 1
DOWN = 2
LEFT = 3

DIRECTIONS = [[0, -1], [0, 1], [1, 0], [-1, 0]]

MAP_SIZE = 20

UNKNOWN = -1
EMPTY = 0
WALL = 1


def random_list(start, until, size):
    return [randrange(start, until) for _ in range(size)]
