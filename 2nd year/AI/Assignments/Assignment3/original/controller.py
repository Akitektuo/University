import statistics

from original.domain import Drone
from original.repository import Repository, deepcopy, DIRECTIONS


class Controller:
    def __init__(self):
        self.repository = Repository()

    def iteration(self, population_size):
        new_population = []

        for _ in range(population_size):
            parent1 = self.repository.population.selection()
            parent2 = self.repository.population.selection()

            offspring, _ = parent1.crossover(parent2)
            offspring.mutate()

            new_population.append(offspring)

        self.repository.set_new_population(new_population)

    def run(self, population_size, number_of_runs, battery):
        fitness_avg = []
        fitness_max = []
        best_solution = None
        population = self.repository.get_population()

        for _ in range(number_of_runs):
            self.iteration(population_size)
            fitness_avg.append(statistics.mean([individual.fitness for individual in population]))
            fitness_max.append(max([individual.fitness for individual in population]))

            for individual in population:
                if not best_solution or best_solution.fitness < individual.fitness:
                    best_solution = deepcopy(individual)

        drone = self.repository.drone.get_tulip()
        environment = self.repository.map
        path = [drone]
        moves = 0
        for chromosome in best_solution.gene.chromosome:
            direction = DIRECTIONS[chromosome]
            next_drone = Drone(drone[0] + direction[0], drone[1] + direction[1])

            if not environment.is_in_bounds(next_drone) or environment.is_wall(next_drone) or battery < moves + 1:
                continue

            moves += 1
            drone = next_drone.get_tulip()
            path.append(drone)

        return path, fitness_avg, fitness_max, best_solution.fitness

    def solver(self, population_size, individual_size, number_of_runs, battery):
        self.repository.create_population(battery, population_size, individual_size)

        return self.run(population_size, number_of_runs, battery)
