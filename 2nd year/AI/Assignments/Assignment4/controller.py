from copy import deepcopy

from domain import Drone, Map, Ant
from utils import DIRECTIONS, MAX_SENSOR_COVERAGE, ternary, PARAM_ANTS, PARAM_ALPHA, PARAM_BETA, PARAM_Q0, PARAM_RHO


class Controller:
    def __init__(self, drone: Drone, environment: Map):
        self.drone = drone
        self.map = environment

        self.pheromone_matrix = []
        for i in range(self.map.n):
            pheromone_row = []
            self.pheromone_matrix.append(pheromone_row)

            for j in range(self.map.m):
                pheromone_square = []
                pheromone_row.append(pheromone_square)

                for direction in DIRECTIONS:
                    neighbour = [i + direction[0], j + direction[1]]

                    if self.map.is_tuple_in_bounds(neighbour) and not self.map.is_tuple_wall(neighbour):
                        pheromone_square.append(ternary(
                            self.map.is_tuple_sensor(neighbour),
                            [1] * (MAX_SENSOR_COVERAGE + 1),
                            [1, 0, 0, 0, 0, 0]))
                    else:
                        pheromone_square.append([0] * (MAX_SENSOR_COVERAGE + 1))

        self.initial_pheromone_matrix = deepcopy(self.pheromone_matrix)

    def iterate(self) -> Ant:
        population = []

        for _ in range(PARAM_ANTS):
            population.append(Ant(self.drone, self.map))

        for _ in range(self.drone.battery):
            for ant in population:
                ant.increase_path(self.pheromone_matrix, PARAM_ALPHA, PARAM_BETA, PARAM_Q0)

        for i in range(self.map.n):
            for j in range(self.map.m):
                for direction_index in range(len(DIRECTIONS)):
                    for spent_energy in range(MAX_SENSOR_COVERAGE + 1):
                        self.compute_pheromone_matrix(i, j, direction_index, spent_energy)

        best_coverage, best_solution = max([(ant.check_coverage(), ant) for ant in population],
                                           key=lambda pair: pair[0])
        for ant in population:

            ant_coverage = ant.check_coverage()
            for i in range(len(ant.path) - 1):

                x = ant.path[i]
                y = ant.path[i + 1]
                direction_index = 0
                for index, direction in enumerate(DIRECTIONS):
                    if (x[0] + direction[0], x[1] + direction[1]) == (y[0], y[1]):
                        direction_index = index
                        break

                self.pheromone_matrix[x[0]][x[1]][direction_index][y[2]] += (ant_coverage + 1) / (
                        (best_coverage + 1) * len(ant.path))

        return best_solution

    def compute_pheromone_matrix(self, i, j, direction_index, spent_energy):
        pheromone_matrix_for_params = self.pheromone_matrix[i][j][direction_index][spent_energy]

        self.pheromone_matrix[i][j][direction_index][spent_energy] = (1 - PARAM_RHO) * pheromone_matrix_for_params + \
                                                                     PARAM_RHO * pheromone_matrix_for_params
