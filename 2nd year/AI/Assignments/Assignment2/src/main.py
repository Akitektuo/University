from random import randint

import pygame

from src.controller.environment_controller import EnvironmentController
from src.old_file import Map
from src.util.constants import Color
from detected_map import DetectedMap
from drone import Drone
from environment import Environment
from src.util.preferences import read_preferences
from src.view.main_view import MainView


def create_objects():
    environment = Environment()
    environment.load_environment("assets/test2.map")

    detected_map = DetectedMap()

    row = randint(0, 19)
    column = randint(0, 19)

    drone = Drone(row, column, detected_map)

    return environment, detected_map, drone


def initialize_pygame():
    pygame.init()

    logo = pygame.image.load("assets/logo32x32.png")

    display = pygame.display
    display.set_icon(logo)
    display.set_caption("Drone exploration")


def create_screen(image):
    screen = pygame.display.set_mode((800, 400))

    screen.fill(Color.WHITE)
    screen.blit(image, (0, 0))

    return screen


def run(environment, detected_map, drone, screen):
    detected_map.mark_detected_walls(environment, drone.row, drone.column)

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            return
        # if event.type == pygame.KEYDOWN:
        #     drone.move()

    drone.move_dfs()
    pygame.time.delay(50)
    if drone.is_done():
        pygame.display.flip()
        screen.blit(detected_map.get_image(drone.row, drone.column), (400, 0))
        pygame.time.wait(2000)
        return

    screen.blit(detected_map.get_image(drone.row, drone.column), (400, 0))
    pygame.display.flip()

    return True


def main():
    MainView().run()


if __name__ == '__main__':
    main()
