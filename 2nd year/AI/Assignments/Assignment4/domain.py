from copy import deepcopy
from random import *

import numpy

from utils import MAP_SIZE, WALL, MAX_SENSOR_COVERAGE, DIRECTIONS, SENSOR, ternary, fill, selection_with_probabilities


class Drone:
    def __init__(self, battery, x=None, y=None):
        self.x = ternary(x is None, randrange(MAP_SIZE), x)
        self.y = ternary(y is None, randrange(MAP_SIZE), y)
        self.battery = battery

    def get_tuple(self):
        return self.x, self.y


class Map:
    def __init__(self, n=MAP_SIZE, m=MAP_SIZE):
        self.n = n
        self.m = m
        self.surface = numpy.zeros((self.n, self.m))
        self.seen_squares = None

    def update_seen_squares(self):
        self.seen_squares = []
        for i in range(self.n):
            seen_squares_row = []
            self.seen_squares.append(seen_squares_row)

            for j in range(self.m):
                readings = [0] * (MAX_SENSOR_COVERAGE + 1)
                seen_squares_row.append(readings)
                if self.surface[i][j] != SENSOR:
                    continue

                neighbours = [[i, j]] * 4
                squares = 0
                for k in range(1, MAX_SENSOR_COVERAGE + 1):
                    for direction_index, direction in enumerate(DIRECTIONS):
                        new_neighbour = deepcopy(neighbours[direction_index])
                        new_neighbour[0] += direction[0]
                        new_neighbour[1] += direction[1]

                        if self.is_tuple_in_bounds(new_neighbour) and not self.is_tuple_wall(new_neighbour):
                            squares += 1
                            neighbours[direction_index] = new_neighbour

                    readings[k] = squares

    def random_map(self, wall_fill=0.2, sensor_fill=0.07):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= wall_fill:
                    self.surface[i][j] = WALL
                elif random() <= sensor_fill:
                    self.surface[i][j] = SENSOR

    def is_wall(self, drone):
        return self.surface[drone.x][drone.y] == WALL

    def is_tuple_wall(self, pair):
        return self.surface[pair[0]][pair[1]] == WALL

    def is_in_bounds(self, drone):
        return 0 <= drone.x < self.n and 0 <= drone.y < self.m

    def is_tuple_in_bounds(self, pair):
        return 0 <= pair[0] < self.n and 0 <= pair[1] < self.m

    def is_sensor(self, drone):
        return self.surface[drone.x][drone.y] == SENSOR

    def is_tuple_sensor(self, pair):
        return self.surface[pair[0]][pair[1]] == SENSOR


class Ant:
    def __init__(self, drone: Drone, environment: Map):
        sensor_energy = ternary(
            environment.is_sensor(drone),
            min(drone.battery, randint(0, MAX_SENSOR_COVERAGE)),
            0)

        self.path = [(drone.x, drone.y, sensor_energy)]
        self.map = environment
        self.spent_energy = fill(0, environment.m, environment.n)
        self.spent_energy[drone.x][drone.y] = sensor_energy
        self.battery_left = drone.battery - sensor_energy

    def check_coverage(self):
        marked = fill(False, self.map.n, self.map.m)
        for i, j, energy in self.path:
            if not energy:
                continue
            neighbours = [[i, j]] * 4
            for _ in range(energy):
                for direction_index, direction in enumerate(DIRECTIONS):
                    new_neighbour = deepcopy(neighbours[direction_index])
                    new_neighbour[0] += direction[0]
                    new_neighbour[1] += direction[1]
                    if self.map.is_tuple_in_bounds(new_neighbour) and not self.map.is_tuple_wall(new_neighbour):
                        marked[new_neighbour[0]][new_neighbour[1]] = True
                        neighbours[direction_index] = new_neighbour

        return sum([row.count(True) for row in marked])

    def increase_path(self, pheromone_matrix, alpha, beta, q0):
        current_square = (self.path[-1][0], self.path[-1][1])
        possible_next_squares = []

        for direction_index, direction in enumerate(DIRECTIONS):
            for spent_energy in range(min(MAX_SENSOR_COVERAGE + 1, self.battery_left)):
                tau = pheromone_matrix[current_square[0]][current_square[1]][direction_index][spent_energy]

                if tau == 0:
                    continue

                cost = (1 / (spent_energy + 1)) ** beta + tau ** alpha
                sight = current_square[0] + direction[0], current_square[1] + direction[1], spent_energy
                next_square = [sight, cost]

                if self.spent_energy[sight[0]][sight[1]] <= spent_energy:
                    possible_next_squares.append(next_square)
        if not possible_next_squares:
            return

        if random() < q0:
            next_square = max(possible_next_squares, key=lambda x: x[1])[0]  # max by cost
        else:
            probabilities_sum = sum([move[1] for move in possible_next_squares])  # sum by cost
            probabilities = [move[1] / probabilities_sum for move in possible_next_squares]  # cost / total cost

            next_square = selection_with_probabilities(possible_next_squares, probabilities)[0]

        spent_energy = next_square[2]

        self.path.append(next_square)
        self.battery_left -= spent_energy + 1
        self.spent_energy[next_square[0]][next_square[1]] = spent_energy
