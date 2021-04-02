from random import randrange, random

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
SENSOR = 2

MAX_SENSOR_COVERAGE = 5

PARAM_ANTS = 80
PARAM_ITERATIONS = 200
PARAM_ALPHA = 0.8
PARAM_BETA = 1.5
PARAM_RHO = 0.1
PARAM_Q0 = 0.3
PARAM_BATTERY = 70


def random_list(start, until, size):
    return [randrange(start, until) for _ in range(size)]


def fill(value, n, m):
    return [[value for _ in range(n)] for _ in range(m)]


def ternary(condition, true, false):
    return true if condition else false


def selection_with_probabilities(iterable, probabilities):
    while True:
        for i in range(len(iterable)):
            if random() <= probabilities[i]:
                return iterable[i]
