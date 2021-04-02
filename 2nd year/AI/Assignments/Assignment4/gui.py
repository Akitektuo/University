import time
from copy import deepcopy

import pygame

from domain import Map
from utils import *


def initialize_gui(dimension):
    pygame.init()
    logo = pygame.image.load("logo32x32.png")
    pygame.display.set_icon(logo)
    pygame.display.set_caption("Exploration with sensors")

    screen = pygame.display.set_mode(dimension)
    screen.fill(WHITE)
    return screen


def move_drone(current_map: Map, path, speed=0.5):
    screen = initialize_gui((current_map.m * 20, current_map.n * 20))
    drona = pygame.image.load("drona.png")

    brick = pygame.Surface((20, 20))
    brick.fill(GREEN)

    seen_square = pygame.Surface((20, 20))
    seen_square.fill(RED)

    sensor = pygame.image.load("sensor.png")

    for square_index in range(len(path)):
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                break

        screen.blit(image(current_map), (0, 0))
        for j in range(square_index + 1):
            screen.blit(brick, (path[j][1] * 20, path[j][0] * 20))

        for square in path[:square_index + 1]:
            if current_map.is_tuple_sensor(square):
                i, j = square[0], square[1]
                neighbours = [[i, j]] * 4
                for _ in range(square[2]):
                    for direction_index, direction in enumerate(DIRECTIONS):
                        new_neighbour = deepcopy(neighbours[direction_index])
                        new_neighbour[0] += direction[0]
                        new_neighbour[1] += direction[1]
                        if (current_map.is_tuple_in_bounds(new_neighbour) and
                                not current_map.is_tuple_wall(new_neighbour)):
                            neighbours[direction_index] = new_neighbour
                            screen.blit(seen_square, (new_neighbour[1] * 20, new_neighbour[0] * 20))

        for i in range(current_map.n):
            for j in range(current_map.m):
                if current_map.surface[i][j] == SENSOR:
                    screen.blit(sensor, (j * 20, i * 20))
        screen.blit(drona, (path[square_index][1] * 20, path[square_index][0] * 20))
        pygame.display.flip()
        time.sleep(speed)

    pygame.quit()


def image(current_map, color=BLUE, background=WHITE):
    imagine = pygame.Surface((current_map.m * 20, current_map.n * 20))
    brick = pygame.Surface((20, 20))

    brick.fill(color)
    imagine.fill(background)

    for i in range(current_map.n):
        for j in range(current_map.m):
            if current_map.surface[i][j] == WALL:
                imagine.blit(brick, (j * 20, i * 20))

    return imagine
