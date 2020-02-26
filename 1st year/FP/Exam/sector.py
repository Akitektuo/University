class Sector:

    def __init__(self, stype):
        self.type = stype

    def __str__(self):
        if self.type == 0:
            return " "
        if self.type == 1:
            return "*"
        if self.type == 2:
            return "E"
        if self.type == 3:
            return "B"