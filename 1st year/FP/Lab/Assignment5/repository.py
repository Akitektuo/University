from domains.student import Student
from domains.discipline import Discipline
from domains.grade import Grade
from validator import is_int

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

    def clear(self):
        self.students.clear()
        self.disciplines.clear()
        self.grades.clear()

    def copy_from(self, other):
        self.students.extend(other.students)
        self.disciplines.extend(other.disciplines)
        self.grades.extend(other.grades)

    def is_student_id(self, sid):
        for s in self.students:
            if s.id == sid:
                return True
        return False

    def is_discipline_id(self, did):
        for d in self.disciplines:
            if d.id == did:
                return True
        return False

    def add_student(self, sid, name):
        self.students.append(Student(sid, name))

    def add_discipline(self, did, name):
        self.disciplines.append(Discipline(did, name))

    def get_discipline(self, did):
        for d in self.disciplines:
            if d.id == did:
                return d
        raise Exception("Invalid discipline ID, no discipline found with ID " + str(did))

    def get_student(self, sid):
        for s in self.students:
            if s.id == sid:
                return s
        raise Exception("Invalid student ID, no student found with ID " + str(sid))

    def add_grade(self, discipline, student, grade):
        if is_int(discipline):
            discipline = self.get_discipline(discipline)

        if is_int(student):
            student = self.get_student(student)

        self.grades.append(Grade(discipline, student, grade))