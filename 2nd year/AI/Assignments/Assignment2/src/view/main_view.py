from threading import Thread

import pygame
from pygame.surface import Surface

from src.util.constants import Color, Dimensions
from src.util.preferences import read_preferences


def sleep(milliseconds: int):
    pygame.time.wait(milliseconds)


class MainView:
    def __init__(self):
        self.running = None
        self.__initialize_window()

    def __initialize_window(self):
        self.running = False
        pygame.init()

        pygame.display.set_icon(
            pygame.image.load(
                read_preferences().get_logo_image()))

        pygame.display.set_caption(
            read_preferences().get_title())

    def __draw_empty_screen(self):
        width = read_preferences().get_height() * Dimensions.BRICK_SIZE
        height = read_preferences().get_width() * Dimensions.BRICK_SIZE

        self.screen = pygame.display.set_mode((width, height))
        self.screen.fill(Color.WHITE)

    def run(self):
        Thread(target=self.__run_async).start()

    def __run_async(self):
        self.__draw_empty_screen()
        self.running = True

        while self.running:
            self.running = not any(event.type == pygame.QUIT for event in pygame.event.get())
            # TODO show drone

        self.__show_result()
        self.__quit()

    def __show_result(self):
        # TODO show path
        pass

    def __set_screen(self, surface: Surface):
        self.screen.blit(surface, (0, 0))
        pygame.display.flip()

    def __quit(self):
        self.running = False
        sleep(5000)
        pygame.quit()
