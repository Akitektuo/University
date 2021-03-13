from threading import Thread

import pygame
from pygame import time, init, display, image, event
from pygame.surface import Surface

from src.util.constants import Color, Dimension
from src.util.preferences import read_preferences
from src.view.drone_view import DroneView


def sleep(milliseconds: int):
    time.wait(milliseconds)


class MainView:
    def __init__(self):
        self.running = None
        self.drone_view = DroneView()
        self.__initialize_window()

    def __initialize_window(self):
        self.running = False
        init()

        display.set_icon(
            image.load(
                read_preferences().get_logo_image()))

        display.set_caption(
            read_preferences().get_title())

    def __draw_empty_screen(self):
        width = read_preferences().get_height() * Dimension.BRICK_SIZE
        height = read_preferences().get_width() * Dimension.BRICK_SIZE

        self.screen = display.set_mode((width, height))
        self.screen.fill(Color.WHITE)

    def run(self):
        Thread(target=self.__run_async).start()

    def __run_async(self):
        self.__draw_empty_screen()
        self.running = True

        while self.running:
            self.running = not any(e.type == pygame.QUIT for e in event.get())
            self.drone_view.render_drone()

        self.__show_result()
        self.__quit()

    def __show_result(self):
        print("Time taken: %s seconds" % self.drone_view.render_path())

    def __set_screen(self, surface: Surface):
        self.screen.blit(surface, (0, 0))
        display.flip()

    def __quit(self):
        self.running = False
        sleep(5000)
        pygame.quit()
