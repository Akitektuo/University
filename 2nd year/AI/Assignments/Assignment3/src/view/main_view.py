import pygame
from pygame import init, display, image, event, time
from pygame.surface import Surface

from src.util.constants import Color, Dimension, Time, Algorithm
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
        self.__run_with_algorithm(Algorithm.A_STAR)
        self.__run_with_algorithm(Algorithm.GREEDY)
        self.__run_with_algorithm(Algorithm.HILL_CLIMBING)
        self.__quit()

    def __run_with_algorithm(self, algorithm: Algorithm):
        self.__draw_empty_screen()
        self.running = True

        while self.running:
            sleep(Time.SLEEP_MILLISECONDS)
            scene, keep_running = self.drone_view.render_drone(algorithm)
            self.__set_screen(scene)
            self.running = keep_running and not any(e.type == pygame.QUIT for e in event.get())

        self.__show_result(algorithm)

    def __show_result(self, algorithm: Algorithm):
        scene, timer = self.drone_view.render_path()
        self.__set_screen(scene)
        print(f"Time taken with {algorithm}: {timer} seconds")
        sleep(Time.TREE_SECONDS)

    def __set_screen(self, surface: Surface):
        self.screen.blit(surface, (0, 0))
        display.flip()

    def __quit(self):
        self.running = False
        pygame.quit()
