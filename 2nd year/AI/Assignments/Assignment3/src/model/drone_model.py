from typing import Optional


class DroneModel:
    def __init__(self, x=None, y=None):
        self.x = x
        self.y = y

    def set_coordinates(self, coordinates: Optional[tuple[int, int]]):
        if not coordinates:
            return
        self.x, self.y = coordinates
