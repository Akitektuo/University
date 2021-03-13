import pickle
from random import random

from src.model.environment_model import EnvironmentModel
from src.util.constants import Status
from src.util.preferences import read_preferences


class EnvironmentController:
    def __init__(self):
        height = read_preferences().get_height()
        width = read_preferences().get_width()

        self.environment_model = EnvironmentModel(height, width)

    def random_map(self, fill_rate=0.2):
        for i in range(self.environment_model.height):
            for j in range(self.environment_model.width):
                if random() <= fill_rate:
                    self.environment_model.surface[i][j] = Status.WALL

    def save_environment(self):
        with open(read_preferences().get_map_file(), "wb") as file:
            pickle.dump(self.environment_model, file)

    def load_environment(self):
        with open(read_preferences().get_map_file(), "rb") as file:
            self.environment_model = pickle.load(file)
            return self.environment_model
