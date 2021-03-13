from random import randrange
from time import time

from src.controller.environment_controller import EnvironmentController
from src.model.drone_model import DroneModel


class DroneController:
    def __init__(self, environment_controller: EnvironmentController):
        x = randrange(environment_controller.get_height())
        y = randrange(environment_controller.get_width())

        self.__drone = DroneModel(x, y)
        self.__environment_controller = environment_controller
        self.__path: list[tuple[int, int]] = []
        self.__computed_time = .0

    def search_a_star(self, start: tuple[int, int], end: tuple[int, int]) -> [tuple[int, int]]:
        start_time = time()
        # TODO write algorithm
        self.__computed_time = time() - start_time
        return self.__path

    def search_greedy(self, start: tuple[int, int], end: tuple[int, int]) -> [tuple[int, int]]:
        start_time = time()
        # TODO write algorithm
        self.__computed_time = time() - start_time
        return self.__path

    def get_drone_x(self) -> int:
        return self.__drone.x

    def get_drone_y(self) -> int:
        return self.__drone.y

    def get_path(self) -> list[tuple[int, int]]:
        return self.__path

    def get_computed_time(self) -> float:
        return self.__computed_time
