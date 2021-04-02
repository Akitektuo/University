from typing import Optional

from controller import Controller
from domain import Map, Drone, Ant
from gui import move_drone, PARAM_BATTERY, PARAM_ITERATIONS


class Command:
    def __init__(self):
        self.map = Map()
        self.map.random_map()

        self.controller = Controller(Drone(PARAM_BATTERY), self.map)

    def run(self):
        solution: Optional[Ant] = None

        for _ in range(PARAM_ITERATIONS):
            current_solution = self.controller.iterate()
            if not solution or solution.check_coverage() < current_solution.check_coverage():
                solution = current_solution

        print(solution.check_coverage())
        move_drone(self.map, solution.path)
