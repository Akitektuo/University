class Discipline:

    def __init__(self, did, name):
        self.id = did
        self.name = name

    def __str__(self):
        return str(self.id) + ". " + self.name


class Grade:

    def __init__(self, discipline, student, value):
        self.discipline = discipline
        self.student = student
        self.value = value

    def __str__(self):
        return self.student.name + " received the grade " + self.format_float(self.value) + " at " + self.discipline.name 

    def format_float(self, grade):
        grade = round(grade, 2)
        int_grade = int(grade)
        if grade == int_grade:
            grade = int_grade
        return str(grade)


class Student:

    def __init__(self, sid, name):
        self.id = sid
        self.name = name

    def __str__(self):
        return str(self.id) + ". " + self.name