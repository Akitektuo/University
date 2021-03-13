from random import randrange
from time import time

from src.controller.environment_controller import EnvironmentController
from src.model.drone_model import DroneModel


class DroneController:
    def __init__(self, environment_controller: EnvironmentController):
        environment = environment_controller.environment_model

        x = randrange(environment.height)
        y = randrange(environment.width)

        self.drone = DroneModel(x, y)
        self.environment_controller = environment_controller
        self.path: list[tuple[int, int]] = []
        self.computed_time = .0

    def search_a_star(self, start: tuple[int, int], end: tuple[int, int]) -> [tuple[int, int]]:
        start_time = time()
        # TODO write algorithm
        self.computed_time = time() - start_time
        return self.path

    def search_greedy(self, start: tuple[int, int], end: tuple[int, int]) -> [tuple[int, int]]:
        start_time = time()
        # TODO write algorithm
        self.computed_time = time() - start_time
        return self.path
