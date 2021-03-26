import statistics
import time
from random import seed

from matplotlib import pyplot

from original.controller import Controller
from original.domain import Drone
from original.gui import move_drone, render_drone


class Command:
    def __init__(self):
        self.controller = Controller()
        self.loaded_map = False
        self.parameters_set = False
        self.program_solved = False
        self.population_size = None
        self.individual_size = None
        self.number_of_runs = None
        self.battery = None
        self.path = None
        self.fitness_avg = None
        self.fitness_max = None
        self.speed = None

    def handle_create_random_map(self):
        self.loaded_map = True
        fill = float(input("Fill factor: "))
        self.controller.repository.load_random_map(fill)

    def handle_load_map(self):
        self.loaded_map = True
        self.controller.repository.load_map()

    def handle_save_map(self):
        if self.loaded_map:
            self.controller.repository.save_map()
        else:
            print("Map not yet loaded")

    def handle_visualise_map(self):
        if self.loaded_map:
            drone = self.controller.repository.drone
            render_drone(self.controller.repository.map, (drone.x, drone.y))
        else:
            print("Map not yet loaded")

    def handle_setup_parameters(self):
        self.parameters_set = True
        self.population_size = int(input("Population size: "))
        self.individual_size = int(input("Individual size: "))
        self.number_of_runs = int(input("Number of runs: "))
        self.battery = int(input("Battery: "))
        self.speed = float(input("Speed of drone: "))

    def handle_run_solver_once(self):
        if not self.parameters_set:
            print("Parameters not set up")
            return

        if self.program_solved:
            print("Program already solved")
            return

        start = time.time()
        self.path, self.fitness_avg, self.fitness_max, solution_fitness = self.controller.solver(self.population_size,
                                                                                                 self.individual_size,
                                                                                                 self.number_of_runs,
                                                                                                 self.battery)
        end = time.time()
        print(f"Algorithm ran in {end - start} seconds")
        print(f"{len(self.path) - 1} moves and {solution_fitness} cells")
        self.program_solved = True

    def handle_run_solver_with_statistics(self):
        if not self.parameters_set:
            print("Parameters not set up")
            return

        values = []
        for i in range(30):
            seed(i)
            _, _, _, fitness = self.controller.solver(self.population_size, self.individual_size, self.number_of_runs,
                                                      self.battery)
            values.append(fitness)

        avg = statistics.mean(values)
        standard_deviation = statistics.stdev(values)
        print(f"Mean: {avg} and standard deviation: {standard_deviation}")

        pyplot.plot(values)
        pyplot.ylim([0, None])
        pyplot.show()

    def handle_visualise_statistics(self):
        if not self.program_solved:
            print("Program not yet solved")
            return

        pyplot.plot(self.fitness_avg)
        pyplot.plot(self.fitness_max)
        pyplot.show()

    def handle_view_drone(self):
        move_drone(self.controller.repository.map, self.path, self.speed)

    option_handler = {
        "1a": handle_create_random_map,
        "1b": handle_load_map,
        "1c": handle_save_map,
        "1d": handle_visualise_map,
        "2a": handle_setup_parameters,
        "2b": handle_run_solver_once,
        "2c": handle_run_solver_with_statistics,
        "2d": handle_visualise_statistics,
        "2e": handle_view_drone
    }

    def print_and_map_options(self):
        print("Map options:")
        print("\t1a. Create a random map")
        print("\t1b. Load a map")
        print("\t1c. Save a map")
        print("\t1d. Visualise a map")
        print("EA options:")
        print("\t2a. Setup parameters")
        print("\t2b. Run solver once")
        print("\t2c. Run solver with mean and standard deviation")
        print("\t2d. Visualise the statistics")
        print("\t2e. View the drone")

        choice = input("> ")
        return self.option_handler[choice]

    def run(self):
        while True:
            handler = self.print_and_map_options()
            if handler:
                handler(self)
            else:
                print("Invalid command")
