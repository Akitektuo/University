class Discipline:

    def __init__(self, did, name):
        self.id = did
        self.name = name

    def __str__(self):
        return str(self.id) + ". " + self.name