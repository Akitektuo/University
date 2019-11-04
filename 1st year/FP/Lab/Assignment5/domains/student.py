class Student:

    def __init__(self, sid, name):
        self.id = sid
        self.name = name

    def __str__(self):
        return str(self.id) + ". " + self.name