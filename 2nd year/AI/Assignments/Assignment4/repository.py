import pickle

from domain import *


class Repository:
    def __init__(self):
        self.population = None
        self.drone = Drone()
        self.map = Map()

    def create_population(self, battery, population_size, individual_size):
        self.drone.battery = battery
        self.population = Population(self.drone, self.map, population_size, individual_size)

    def set_new_population(self, population_list):
        self.population = Population(self.drone, self.map, population=population_list)

    def load_random_map(self, fill_factor):
        self.map.random_map(fill_factor)

    def get_population(self):
        return self.population.population

    def save_map(self):
        with open("file.map", "wb") as file:
            pickle.dump(self.map, file)

    def load_map(self):
        with open("file.map", "rb") as file:
            self.map = pickle.load(file)
