import pickle
from random import random

from src.model.environment_model import EnvironmentModel
from src.util.constants import Status
from src.util.preferences import read_preferences


class EnvironmentController:
    def __init__(self):
        height = read_preferences().get_height()
        width = read_preferences().get_width()

        self.__environment_model = EnvironmentModel(height, width)

    def random_map(self, fill_rate=0.2):
        for i in range(self.__environment_model.height):
            for j in range(self.__environment_model.width):
                if random() <= fill_rate:
                    self.__environment_model.surface[i][j] = Status.WALL

    def save_environment(self):
        with open(read_preferences().get_map_file(), "wb") as file:
            pickle.dump(self.__environment_model, file)

    def load_environment(self) -> EnvironmentModel:
        with open(read_preferences().get_map_file(), "rb") as file:
            self.__environment_model = pickle.load(file)
            return self.__environment_model

    def get_width(self) -> int:
        return self.__environment_model.width

    def get_height(self) -> int:
        return self.__environment_model.height

    def get_surface(self):
        return self.__environment_model.surface

    def is_wall(self, x: int, y: int) -> int:
        return self.get_surface()[x][y] == Status.WALL
