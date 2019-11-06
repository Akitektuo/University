from domains import Student
from domains import Discipline
from domains import Grade
from domains import Average
from validator import is_int
from action_tracker import ActionTracker
import copy

class Repository:

    def __init__(self):
        self.students = []
        self.disciplines = []
        self.grades = []
        self.tracker = ActionTracker(self)

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

    def add_all(self, other, track = True):
        self.students.extend(other.students)
        self.disciplines.extend(other.disciplines)
        self.grades.extend(other.grades)
        if track:
            self.tracker.add_action(ActionTracker.ADD_MULTIPLE, other)

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

    def add_student(self, sid, name, track = True):
        student = Student(sid, name)
        self.students.append(student)
        if track:
            self.tracker.add_action(ActionTracker.ADD, student, ActionTracker.STUDENT)

    def add_discipline(self, did, name, track = True):
        discipline = Discipline(did, name)
        self.disciplines.append(discipline)
        if track:
            self.tracker.add_action(ActionTracker.ADD, discipline, ActionTracker.DISCIPLINE)

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

    def add_grade(self, discipline, student, value, track = True):
        if is_int(discipline):
            discipline = self.get_discipline(discipline)

        if is_int(student):
            student = self.get_student(student)

        grade = Grade(discipline, student, value)
        self.grades.append(grade)
        if track:
            self.tracker.add_action(ActionTracker.ADD, grade, ActionTracker.GRADE)

    def remove_student(self, sid, track = True):
        deleted = Repository()
        student = self.get_student(sid)

        grades_copy = list(self.grades)
        for g in grades_copy:
            if g.student == student:
                deleted.grades.append(g)
                self.grades.remove(g)

        deleted.students.append(student)
        self.students.remove(student)
        if track:
            self.tracker.add_action(ActionTracker.REMOVE_MULTIPLE, deleted)

    def remove_discipline(self, did, track = True):
        deleted = Repository()
        discipline = self.get_discipline(did)

        grades_copy = list(self.grades)
        for g in grades_copy:
            if g.discipline == discipline:
                deleted.grades.append(g)
                self.grades.remove(g)
                
        deleted.students.append(discipline)
        self.disciplines.remove(discipline)
        if track:
            self.tracker.add_action(ActionTracker.REMOVE_MULTIPLE, deleted)

    def update_student(self, sid, name, track = True):
        student = self.get_student(sid)
        if track:
            self.tracker.add_action(ActionTracker.EDIT, copy.copy(student), ActionTracker.STUDENT)
        student.name = name

    def update_discipline(self, did, name, track = True):
        discipline = self.get_discipline(did)
        if track:
            self.tracker.add_action(ActionTracker.EDIT, copy.copy(discipline), ActionTracker.DISCIPLINE)
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
                    students_with_grades.append(Average(average, s.name, d.name))

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
            students_with_grades.append(Average(total_average, student_name = s.name))

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
            disciplines_with_grades.append(Average(average, discipline_name = d.name))
        
        return disciplines_with_grades

    def restore(self):
        self.tracker.revert()