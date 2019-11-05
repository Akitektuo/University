from domains import Student
from domains import Discipline
from domains import Grade
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

    def remove_student(self, sid):
        student = self.get_student(sid)

        grades_copy = list(self.grades)
        for g in grades_copy:
            if g.student == student:
                self.grades.remove(g)

        self.students.remove(student)

    def remove_discipline(self, did):
        discipline = self.get_discipline(did)

        grades_copy = list(self.grades)
        for g in grades_copy:
            if g.discipline == discipline:
                self.grades.remove(g)
                
        self.disciplines.remove(discipline)

    def update_student(self, sid, name):
        student = self.get_student(sid)
        student.name = name

    def update_discipline(self, did, name):
        discipline = self.get_discipline(did)
        discipline.name = name

    def search(self, keyword):
        repo_with_search = Repository()
        keyword = keyword.upper()

        for s in self.students:
            if keyword in str(s.id).upper() or keyword in s.name.upper():
                repo_with_search.students.append(s)
        for d in self.disciplines:
            if keyword in str(d.id).upper() or keyword in d.name.upper():
                repo_with_search.disciplines.append(d)
    
        return repo_with_search

    def get_failing_students(self):
        students_with_grades = []

        for s in self.students:
            for d in self.disciplines:
                count = 0
                average = 0

                for g in self.grades:
                    if g.student == s and g.discipline == d:
                        count += 1
                        average += g.value

                if count == 0:
                    continue

                average /= count

                if average < 5:
                    students_with_grades.append([s.name, d.name, average])

        return students_with_grades

    def get_best_students(self):
        students_with_grades = []

        for s in self.students:
            total_count = 0
            total_average = 0

            for d in self.disciplines:
                count = 0
                average = 0

                for g in self.grades:
                    if g.student == s and g.discipline == d:
                        count += 1
                        average += g.value

                if count == 0:
                    continue

                total_count += 1
                total_average += average / count

            if total_count == 0:
                continue

            total_average /= total_count
            students_with_grades.append([s.name, total_average])

        return students_with_grades

    def get_disciplines_with_grades(self):
        disciplines_with_grades = []

        for d in self.disciplines:
            count = 0
            average = 0

            for g in self.grades:
                if g.discipline == d:
                    count += 1
                    average += g.value

            if count == 0:
                continue

            average /= count
            disciplines_with_grades.append([d.name, average])
        
        return disciplines_with_grades