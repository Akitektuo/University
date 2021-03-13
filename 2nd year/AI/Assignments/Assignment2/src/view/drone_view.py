from pygame import Surface, image

from src.controller.drone_controller import DroneController
from src.util.constants import Dimension, Color
from src.util.preferences import read_preferences
from src.view.environment_view import EnvironmentView


class DroneView:
    def __init__(self):
        self.environment_view = EnvironmentView()
        self.drone_controller = DroneController(self.environment_view.controller)

    def render_drone(self) -> Surface:
        # self.drone_controller.search_a_star()
        # self.drone_controller.search_greedy()

        surface = self.environment_view.render()

        drone_image = image.load(read_preferences().get_drone_image())
        drone = self.drone_controller.drone

        surface.blit(drone_image, (drone.y * Dimension.BRICK_SIZE, drone.x * Dimension.BRICK_SIZE))

        return surface

    def render_path(self) -> float:
        surface = self.environment_view.render()

        mark = Surface((Dimension.BRICK_SIZE, Dimension.BRICK_SIZE))
        mark.fill(Color.GREEN)

        for move in self.drone_controller.path:
            surface.blit(mark, (move[1] * Dimension.BRICK_SIZE, move[0] * Dimension.BRICK_SIZE))

        return self.drone_controller.computed_time
