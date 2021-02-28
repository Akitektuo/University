import numpy
import pygame

from constants import Status, Color, Direction


class DetectedMap:
    def __init__(self):
        self.__height = 20  # = n
        self.__width = 20  # = m
        self.surface = numpy.full((self.__height, self.__width), Status.UNKNOWN)

    def mark_detected_walls(self, environment, row, column):
        # Todo mark on this map the walls that you detect
        readings = environment.read_udm_sensors(row, column)

        self.__mark_direction(readings[Direction.UP], -1, row, lambda index: index >= 0,
                              lambda index, reading: index >= row - reading,
                              lambda index, status: self.surface[index].__setitem__(column, status))

        self.__mark_direction(readings[Direction.DOWN], 1, row, lambda index: index < self.__height,
                              lambda index, reading: index <= row + reading,
                              lambda index, status: self.surface[index].__setitem__(column, status))

        self.__mark_direction(readings[Direction.RIGHT], 1, column, lambda index: index < self.__width,
                              lambda index, reading: index <= column + reading,
                              lambda index, status: self.surface[row].__setitem__(index, status))

        self.__mark_direction(readings[Direction.LEFT], -1, column, lambda index: index >= 0,
                              lambda index, reading: index >= column - reading,
                              lambda index, status: self.surface[row].__setitem__(index, status))

    @staticmethod
    def __mark_direction(reading, increment, based_on, margin_condition, remaining_condition, set_status):
        index = based_on + increment

        if reading > 0:
            while margin_condition(index) and remaining_condition(index, reading):
                set_status(index, Status.EMPTY)
                index += increment

        if margin_condition(index):
            set_status(index, Status.WALL)

    def get_image(self, row, column):
        brick_size = 20

        canvas = pygame.Surface((420, 420))
        canvas.fill(Color.GRAY_BLUE)

        brick = pygame.Surface((brick_size, brick_size))
        brick.fill(Color.BLACK)

        empty = pygame.Surface((brick_size, brick_size))
        empty.fill(Color.WHITE)

        for i in range(self.__height):
            for j in range(self.__width):
                if self.surface[i][j] == Status.WALL:
                    canvas.blit(brick, (j * brick_size, i * brick_size))
                elif self.surface[i][j] == Status.EMPTY:
                    canvas.blit(empty, (j * brick_size, i * brick_size))

        drone = pygame.image.load("assets/drona.png")
        canvas.blit(drone, (column * brick_size, row * brick_size))

        return canvas

    def is_empty(self, row, column):
        return self.surface[row][column] == Status.EMPTY
