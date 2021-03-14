from collections import Callable
from time import time
from typing import Optional

from numpy import ndarray

from src.algorithm.a_star import execute_a_star
from src.algorithm.greedy import execute_greedy
from src.controller.environment_controller import EnvironmentController
from src.model.drone_model import DroneModel
from src.util.constants import Time
from src.util.generator import get_random_range_by_width, get_random_range_by_height, get_random_range_tuple


class DroneController:
    def __init__(self, environment_controller: EnvironmentController):
        self.__initial_x = get_random_range_by_height(environment_controller.get_height())
        self.__initial_y = get_random_range_by_width(environment_controller.get_width())

        self.__drone = DroneModel(self.__initial_x, self.__initial_y)
        self.__environment_controller = environment_controller
        self.__path: list[tuple[int, int]] = []
        self.__timer_start = .0
        self.__computed_time = .0
        self.__start = self.__end = None

    def __start_timer_if_not_running(self):
        if not self.__timer_start:
            self.__timer_start = time()
        else:
            self.__timer_start += Time.SLEEP_MILLISECONDS / Time.SECOND_IN_MILLISECONDS

    def __compute_execution_time(self):
        self.__computed_time = time() - self.__timer_start
        self.__timer_start = 0

    def search_a_star(self, start: tuple[int, int] = None, end: tuple[int, int] = None) -> bool:
        return self.__run_algorithm(execute_a_star, start, end)

    def search_greedy(self, start: tuple[int, int] = None, end: tuple[int, int] = None) -> bool:
        return self.__run_algorithm(execute_greedy, start, end)

    def __run_algorithm(self,
                        algorithm_function: Callable[
                            [ndarray, tuple[int, int], tuple[int, int]],
                            Optional[tuple[int, int]]],
                        start: tuple[int, int] = None,
                        end: tuple[int, int] = None) -> bool:
        if not self.__timer_start:
            self.__start = (start, (self.__initial_x, self.__initial_y))[start is None]
            self.__end = (end, get_random_range_tuple())[end is None]
            self.__path = []

        self.__start_timer_if_not_running()

        new_position = algorithm_function(self.__environment_controller.get_surface(), self.__start, self.__end)
        self.__drone.set_coordinates(new_position)

        if new_position:
            self.__path.append(new_position)
        else:
            self.__compute_execution_time()

        return new_position is not None

    def get_drone_x(self) -> int:
        return self.__drone.x

    def get_drone_y(self) -> int:
        return self.__drone.y

    def get_start_and_finish_coordinates(self) -> tuple[tuple[int, int], tuple[int, int]]:
        return self.__start, self.__end

    def get_path(self) -> list[tuple[int, int]]:
        return self.__path

    def get_computed_time(self) -> float:
        return self.__computed_time
