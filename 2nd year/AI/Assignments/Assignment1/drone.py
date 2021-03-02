import pygame
from pygame import constants


class Drone:
    def __init__(self, row, column, detected_map):
        self.row = row
        self.column = column
        self.detected_map = detected_map

        self.__visited = {self.get_tuple()}
        self.__stack = []

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

    def check_unvisited(self, unvisited, coordinates, condition):
        if condition() and self.detected_map.is_empty_tuple(coordinates) and coordinates not in self.__visited:
            unvisited.append(coordinates)

    def get_unvisited(self):
        unvisited = []

        self.check_unvisited(unvisited, (self.row - 1, self.column), lambda: self.row > 0)
        self.check_unvisited(unvisited, (self.row + 1, self.column), lambda: self.row < self.detected_map.height - 1)
        self.check_unvisited(unvisited, (self.row, self.column - 1), lambda: self.column > 0)
        self.check_unvisited(unvisited, (self.row, self.column + 1), lambda: self.column < self.detected_map.width - 1)

        return unvisited

    def move_dfs(self):
        unvisited = self.get_unvisited()

        if len(unvisited) == 0:
            if len(self.__stack) == 0:
                self.row, self.column = None, None
            else:
                self.row, self.column = self.__stack.pop()
            return

        self.__stack.append(self.get_tuple())
        self.__visited.add(self.set_tuple(unvisited.pop()))

    def get_tuple(self):
        return self.row, self.column

    def set_tuple(self, pair):
        self.row, self.column = pair

        return pair

    def is_done(self):
        return self.row is None or self.column is None
