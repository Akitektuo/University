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


class Average:

    def __init__(self, value, student_name = "", discipline_name = ""):
        self.value = value
        self.student_name = student_name
        self.discipline_name = discipline_name

    def get_formated_average(self):
        value = round(self.value, 2)
        int_value = int(value)
        if value == int_value:
            value = int_value
        return str(value)