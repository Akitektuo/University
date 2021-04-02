from copy import deepcopy
from random import *

import numpy

from utils import MAP_SIZE, WALL, random_list, DIRECTIONS, fill


class Gene:
    def __init__(self, size):
        self.chromosome = random_list(0, 4, size)
        self.size = size

    def swap(self, i, j):
        self.chromosome[i], self.chromosome[j] = self.chromosome[j], self.chromosome[i]

    def set(self, i, data):
        self.chromosome[i] = data

    def get(self, i):
        return self.chromosome[i]

    def get_crossover(self, with_gene, cut):
        gene = Gene(self.size)

        for i in range(self.size):
            gene.set(i, (with_gene, self)[i < cut].get(i))

        return gene


class Individual:
    def __init__(self, drone, environment, size=0, gene=None):
        self.drone = drone
        self.map = environment
        self.gene = (gene, Gene(size))[gene is None]
        self.fitness = None

    def compute_fitness(self):
        drone = deepcopy(self.drone)
        marked = fill(False, self.map.n, self.map.m)
        moves = 0

        for chromosome in self.gene.chromosome:
            marked[drone.x][drone.y] = True

            for direction in DIRECTIONS:
                simulation = deepcopy(drone)

                while True:
                    simulation.x += direction[0]
                    simulation.y += direction[1]
                    if not self.map.is_in_bounds(simulation) or self.map.is_wall(simulation):
                        break
                    marked[simulation.x][simulation.y] = True

            direction = DIRECTIONS[chromosome]
            new_drone = Drone(drone.x + direction[0], drone.y + direction[1], drone.battery)
            if not self.map.is_in_bounds(new_drone) or self.map.is_wall(new_drone) or self.drone.battery < moves + 1:
                continue
            moves += 1
            drone = new_drone
        self.fitness = sum([row.count(True) for row in marked])

    def mutate(self, probability=0.04):
        if random() >= probability or self.gene.size < 2:
            return
        i = j = 0
        while i == j:
            i = randrange(0, self.gene.size)
            j = randrange(0, self.gene.size)
        self.gene.swap(i, j)

    def get_crossover_gene(self, other, cut):
        return self.gene.get_crossover(other.gene, cut)

    def crossover(self, other_individual, probability=0.8):
        if random() >= probability:
            return Individual(self.drone, self.map, self.gene.size), Individual(self.drone, self.map, self.gene.size)

        cut = randint(0, self.gene.size)
        return (Individual(self.drone, self.map, gene=self.get_crossover_gene(other_individual, cut)),
                Individual(self.drone, self.map, gene=other_individual.get_crossover_gene(self, cut)))

    def __str__(self):
        return f"fitness: {self.fitness}"


class Population:
    def __init__(self, drone, environment, population_size=10, individual_size=10, population=None):
        self.population_size = population_size
        self.population = (population, [
            Individual(drone, environment, individual_size) for _ in range(population_size)
        ])[population is None]
        self.evaluate()

    def evaluate(self):
        for individual in self.population:
            individual.compute_fitness()

    def selection(self, k=2) -> Individual:
        random_individuals = sample(self.population, k)
        top_individuals = sorted(random_individuals, key=lambda x: x.fitness, reverse=True)

        return top_individuals[0]  # returning most fit individual


class Drone:
    def __init__(self, x=None, y=None, battery=10):
        self.x = (x, randrange(MAP_SIZE))[x is None]
        self.y = (y, randrange(MAP_SIZE))[y is None]
        self.battery = battery

    def get_tulip(self):
        return self.x, self.y


class Map:
    def __init__(self, n=MAP_SIZE, m=MAP_SIZE):
        self.n = n
        self.m = m
        self.surface = numpy.zeros((self.n, self.m))

    def random_map(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = WALL

    def is_wall(self, drone):
        return self.surface[drone.x][drone.y] == WALL

    def is_in_bounds(self, drone):
        return 0 <= drone.x < self.n and 0 <= drone.y < self.m
