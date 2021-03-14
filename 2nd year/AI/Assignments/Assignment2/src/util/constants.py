class Color:
    BLUE = (0, 0, 255)
    GRAY_BLUE = (50, 120, 120)
    RED = (255, 0, 0)
    GREEN = (0, 255, 0)
    BLACK = (0, 0, 0)
    WHITE = (255, 255, 255)


class Direction:
    UP = 0
    RIGHT = 1
    DOWN = 2
    LEFT = 3
    DIRECTIONS = ((-1, 0), (0, 1), (1, 0), (0, -1))


class Status:
    UNKNOWN = -1
    EMPTY = 0
    WALL = 1


class Dimension:
    BRICK_SIZE = 20


class Property:
    LOGO_IMAGE = "logo_image"
    DRONE_IMAGE = "drone_image"
    TITLE = "title"
    WIDTH = "width"
    HEIGHT = "height"
    MAP_FILE = "map_file"
    FINISH_IMAGE = "finish_image"
    START_IMAGE = "start_image"


class Time:
    SECOND_IN_MILLISECONDS = 1000
    SLEEP_MILLISECONDS = 100
    TREE_SECONDS = 3000


class Algorithm:
    A_STAR = "A*"
    GREEDY = "Greedy"
