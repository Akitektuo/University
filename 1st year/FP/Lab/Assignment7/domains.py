class Box:

    def __init__(self, is_boat = False, is_hit = False):
        self.is_boat = is_boat
        self.is_hit = is_hit


class Coordinate:

    def __init__(self, x = 0, y = 0):
        self.x = x
        self.y = y

    def equals(self, other):
        return self.x == other.x and self.y == other.y


class HitEvent:

    def __init__(self, coordinates, successful):
        self.coordinates = coordinates
        self.successful = successful