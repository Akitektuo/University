class Address:

    def __init__(self, aid, name, x, y):
        self.id = aid
        self.name = name
        self.x = x
        self.y = y

    def __str__(self):
        return "ID: " + str(self.id) + ", Street: " + self.name + ", Coordinates: (" + str(self.x) + ", " + str(self.y) + ")"

class Driver:

    def __init__(self, name, x, y):
        self.name = name
        self.x = x
        self.y = y

    def __str__(self):
        return "Name: " + self.name + ", Coordinates: (" + str(self.x) + ", " + str(self.y) + ")"