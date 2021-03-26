# -*- coding: utf-8 -*-

import time

import pygame

from original.domain import Drone
from utils import *


def initialize_gui(dimension):
    # init the pygame
    pygame.init()
    logo = pygame.image.load("logo32x32.png")
    pygame.display.set_icon(logo)
    pygame.display.set_caption("Drone exploration with AE")

    # create a surface on screen that has the size of 800 x 480
    screen = pygame.display.set_mode(dimension)
    screen.fill(WHITE)
    return screen


def close_gui():
    # closes the pygame
    running = True
    # loop for events
    while running:
        # event handling, gets all event from the event queue
        for event in pygame.event.get():
            # only do something if the event is of type QUIT
            if event.type == pygame.QUIT:
                # change the value to False, to exit the main loop
                running = False
    pygame.quit()


def move_drone(current_map, path, speed=0.5, mark_seen=True):
    # animation of a drone on a path
    screen = initialize_gui((current_map.n * 20, current_map.m * 20))
    drona = pygame.image.load("drona.png")

    for i in range(len(path)):
        screen.blit(image(current_map), (0, 0))

        if mark_seen:
            brick = pygame.Surface((20, 20))
            brick.fill(GREEN)
            for j in range(i + 1):
                for direction in DIRECTIONS:
                    x = path[j][0]
                    y = path[j][1]
                    drone = Drone(direction[0] + x, direction[1] + y)
                    while current_map.is_in_bounds(drone) and not current_map.is_wall(drone):
                        screen.blit(brick, (drone.y * 20, drone.x * 20))

        screen.blit(drona, (path[i][1] * 20, path[i][0] * 20))
        pygame.display.flip()
        time.sleep(speed)
    close_gui()


def render_drone(current_map, drone_position):
    screen = initialize_gui((current_map.n * 20, current_map.m * 20))
    screen.blit(image(current_map), (0, 0))
    drone = pygame.image.load("drona.png")
    screen.blit(drone, (drone_position[1] * 20, drone_position[0] * 20))
    for _ in range(1000):
        pygame.display.flip()
    close_gui()


def image(current_map, color=BLUE, background=WHITE):
    imagine = pygame.Surface((current_map.n * 20, current_map.m * 20))
    brick = pygame.Surface((20, 20))

    brick.fill(color)
    imagine.fill(background)

    for i in range(current_map.n):
        for j in range(current_map.m):
            if current_map.surface[i][j] == WALL:
                imagine.blit(brick, (j * 20, i * 20))

    return imagine
