class Grade:

    def __init__(self, discipline, student, value):
        self.discipline = discipline
        self.student = student
        self.value = value

    def __str__(self):
        return self.student.name + " received the grade " + str(self.value) + " at " + self.discipline.name 