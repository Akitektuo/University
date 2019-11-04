class Repository:

    def __init__(self):
        self.students = []
        self.disciplines = []
        self.grades = []

    def is_empty(self):
        return len(self.students) < 1 and len(self.disciplines) < 1 and len(self.grades) < 1

    def has_students(self):
        return len(self.students) > 0

    def has_disciplines(self):
        return len(self.disciplines) > 0

    def has_grades(self):
        return len(self.grades) > 0

    def copy_from(self, other):
        self.students = other.students
        self.disciplines = other.disciplines
        self.grades = other.grades