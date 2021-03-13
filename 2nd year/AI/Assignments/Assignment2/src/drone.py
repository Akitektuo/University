import pygame
from pygame import constants


class Drone:
    def __init__(self, row, column, detected_map):
        self.row = row
        self.column = column
        self.detected_map = detected_map

    def move(self):
        pressed_key = pygame.key.get_pressed()

        if self.row > 0 and pressed_key[constants.K_UP] and self.detected_map.is_empty(self.row - 1, self.column):
            self.row -= 1

        if self.row < self.detected_map.height - 1 and pressed_key[constants.K_DOWN] and (
                self.detected_map.is_empty(self.row + 1, self.column)):
            self.row += 1

        if self.column > 0 and pressed_key[constants.K_LEFT] and self.detected_map.is_empty(self.row, self.column - 1):
            self.column -= 1

        if self.column < self.detected_map.width - 1 and pressed_key[constants.K_RIGHT] and (
                self.detected_map.is_empty(self.row, self.column + 1)):
            self.column += 1

    def append_image(self, map_image):
        drona = pygame.image.load("drona.png")
        map_image.blit(drona, (self.column * 20, self.row * 20))

        return map_image
