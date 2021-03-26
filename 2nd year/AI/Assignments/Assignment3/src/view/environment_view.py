from pygame import Surface

from src.controller.environment_controller import EnvironmentController
from src.util.constants import Dimension, Color
from src.util.preferences import read_preferences


class EnvironmentView:
    def __init__(self):
        self.controller = EnvironmentController()
        self.controller.load_environment()

    def render(self, color=Color.BLUE, background=Color.WHITE) -> Surface:
        width = read_preferences().get_width() * Dimension.BRICK_SIZE
        height = read_preferences().get_height() * Dimension.BRICK_SIZE

        surface = Surface((width, height))
        surface.fill(background)

        brick = Surface((Dimension.BRICK_SIZE, Dimension.BRICK_SIZE))
        brick.fill(color)

        for i in range(self.controller.get_height()):
            for j in range(self.controller.get_width()):
                if self.controller.is_wall(i, j):
                    surface.blit(brick, (j * Dimension.BRICK_SIZE, i * Dimension.BRICK_SIZE))

        return surface
