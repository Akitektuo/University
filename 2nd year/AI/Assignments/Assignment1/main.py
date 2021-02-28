from random import randint

import pygame

from constants import Color
from detected_map import DetectedMap
from drone import Drone
from environment import Environment


def create_objects():
    environment = Environment()
    environment.load_environment("assets/test2.map")

    detected_map = DetectedMap()

    row = randint(0, 19)
    column = randint(0, 19)

    drone = Drone(row, column)

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
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            return False
        if event.type == pygame.KEYDOWN:
            drone.move(detected_map)

    detected_map.mark_detected_walls(environment, drone.row, drone.column)
    screen.blit(detected_map.get_image(drone.row, drone.column), (400, 0))
    pygame.display.flip()

    return True


def main():
    environment, detected_map, drone = create_objects()
    initialize_pygame()
    screen = create_screen(environment.get_image())

    while run(environment, detected_map, drone, screen):
        pass
    pygame.quit()


if __name__ == '__main__':
    main()
