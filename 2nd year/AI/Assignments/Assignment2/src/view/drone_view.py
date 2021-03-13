from pygame import Surface, image

from src.controller.drone_controller import DroneController
from src.util.constants import Dimension, Color
from src.util.preferences import read_preferences
from src.view.environment_view import EnvironmentView


def render_image(surface: Surface, coordinates: tuple[int, int], image_name: str):
    loaded_image = image.load(image_name)
    surface.blit(loaded_image, (coordinates[1] * Dimension.BRICK_SIZE, coordinates[0] * Dimension.BRICK_SIZE))


class DroneView:
    def __init__(self):
        self.environment_view = EnvironmentView()
        self.drone_controller = DroneController(self.environment_view.controller)

    def render_path(self) -> tuple[Surface, float]:
        surface = self.environment_view.render()

        mark = Surface((Dimension.BRICK_SIZE, Dimension.BRICK_SIZE))
        mark.fill(Color.GREEN)

        for move in self.drone_controller.get_path():
            surface.blit(mark, (move[1] * Dimension.BRICK_SIZE, move[0] * Dimension.BRICK_SIZE))

        self.__render_start_and_finish_on(surface)

        return surface, self.drone_controller.get_computed_time()

    def render_drone(self) -> tuple[Surface, float]:
        should_continue = self.drone_controller.search_a_star()
        # self.drone_controller.search_greedy()

        surface, _ = self.render_path()

        drone_image = image.load(read_preferences().get_drone_image())
        x = self.drone_controller.get_drone_x()
        y = self.drone_controller.get_drone_y()

        surface.blit(drone_image, (y * Dimension.BRICK_SIZE, x * Dimension.BRICK_SIZE))

        return surface, should_continue

    def __render_start_and_finish_on(self, surface: Surface):
        start, finish = self.drone_controller.get_start_and_finish_coordinates()
        if start is None or finish is None:
            return

        render_image(surface, start, read_preferences().get_start_image())
        render_image(surface, finish, read_preferences().get_finish_image())
