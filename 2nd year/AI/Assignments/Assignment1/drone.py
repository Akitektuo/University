import pygame
from pygame import constants


class Drone:
    def __init__(self, row, column):
        self.row = row
        self.column = column

    def move(self, detected_map):
        pressed_key = pygame.key.get_pressed()

        if self.row > 0 and pressed_key[constants.K_UP] and detected_map.is_empty(self.row - 1, self.column):
            self.row -= 1

        if self.row < 19 and pressed_key[constants.K_DOWN] and detected_map.is_empty(self.row + 1, self.column):
            self.row += 1

        if self.column > 0 and pressed_key[constants.K_LEFT] and detected_map.is_empty(self.row, self.column - 1):
            self.column -= 1

        if self.column < 19 and pressed_key[constants.K_RIGHT] and detected_map.is_empty(self.row, self.column + 1):
            self.column += 1
