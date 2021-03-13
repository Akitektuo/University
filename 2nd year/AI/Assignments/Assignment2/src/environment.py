import pickle
from random import random

import numpy
import pygame

from src.util.constants import Color, Status


class Environment:
    def __init__(self):
        self.height = 20  # = n
        self.width = 20  # = m
        self.surface = numpy.zeros((self.height, self.width))

    def random_map(self, fill=0.2):
        for i in range(self.height):
            for j in range(self.width):
                if random() <= fill:
                    self.surface[i][j] = Status.WALL

    def __str__(self):
        text = ""

        for i in range(self.height):
            for j in range(self.width):
                text += str(int(self.surface[i][j]))
            text += "\n"

        return text

    def save_environment(self, file_name):
        with open(file_name, "wb") as file:
            pickle.dump(self, file)

    def load_environment(self, file_name):
        with open(file_name, "rb") as file:
            self.copy_from(pickle.load(file))

    def get_image(self, color=Color.BLUE, background=Color.WHITE):
        brick_size = 20

        canvas = pygame.Surface((400, 400))
        canvas.fill(background)

        brick = pygame.Surface((brick_size, brick_size))
        brick.fill(color)

        for i in range(self.height):
            for j in range(self.width):
                if self.surface[i][j] == Status.WALL:
                    canvas.blit(brick, (j * brick_size, i * brick_size))

        return canvas

    def copy_from(self, from_environment):
        self.height = from_environment.height
        self.width = from_environment.width
        self.surface = from_environment.surface
