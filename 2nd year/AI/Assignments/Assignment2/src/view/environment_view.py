from pygame import Surface

from src.controller.environment_controller import EnvironmentController
from src.util.constants import Dimension, Color, Status
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

        environment = self.controller.environment_model

        for i in range(environment.height):
            for j in range(environment.width):
                if environment.surface[i][j] == Status.WALL:
                    surface.blit(brick, (j * Dimension.BRICK_SIZE, i * Dimension.BRICK_SIZE))

        return surface
