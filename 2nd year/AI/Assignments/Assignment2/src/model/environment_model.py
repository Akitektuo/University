import numpy


class EnvironmentModel:
    def __init__(self, height=0, width=0):
        self.height = height  # = n
        self.width = width  # = m
        self.surface = numpy.zeros((self.height, self.width))

    def __copy__(self):
        copy = type(self)()

        copy.height = self.height
        copy.width = self.width
        copy.surface = self.surface

        return copy

    def __str__(self):
        text = ""

        for i in range(self.height):
            for j in range(self.width):
                text += str(int(self.surface[i][j]))
            text += "\n"

        return text
