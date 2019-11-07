from domains import Student
from domains import Discipline
from domains import Grade
from exceptions import NoDataError
from repository import Repository
from randomiser import Randomiser
from validator import *
import generator

class Services:

    def __init__(self):
        self.repository = Repository()

    def preload_list(self):
        randomiser = Randomiser()
        repository_to_add = Repository()

        for i in range(10):
            random_name = randomiser.get_random_first_name() + " " + randomiser.get_random_last_name()

            repository_to_add.add_student(i, random_name)
            repository_to_add.add_discipline(i, randomiser.get_random_discipline_name())
        
        for i in range(10):
            repository_to_add.add_grade(
                randomiser.get_random(repository_to_add.disciplines),
                randomiser.get_random(repository_to_add.students),
                randomiser.get_random_grade()
            )

        self.repository.add_all(repository_to_add)

    def add(self, params):
        validate_param_length(params, 3)

        atype = params[0]
        aid = params[1]
        name = params[2]

        validate_type(atype)
        if atype == STUDENT:
            validate_new_student_id(self.repository, aid)
        if atype == DISCIPLINE:
            validate_new_discipline_id(self.repository, aid)
        validate_name(name)

        aid = int(aid)
        if atype == STUDENT:
            self.repository.add_student(aid, name)
        if atype == DISCIPLINE:
            self.repository.add_discipline(aid, name)

    def remove(self, params):
        validate_param_length(params, 2)

        atype = params[0]
        aid = params[1]

        validate_type(atype)
        if atype == STUDENT:
            validate_existing_student_id(self.repository, aid)
        if atype == DISCIPLINE:
            validate_existing_discipline_id(self.repository, aid)

        aid = int(aid)
        if atype == STUDENT:
            self.repository.remove_student(aid)
        if atype == DISCIPLINE:
            self.repository.remove_discipline(aid)

    def update(self, params):
        validate_param_length(params, 3)

        atype = params[0]
        aid = params[1]
        name = params[2]

        validate_type(atype)
        if atype == STUDENT:
            validate_existing_student_id(self.repository, aid)
        if atype == DISCIPLINE:
            validate_existing_discipline_id(self.repository, aid)
        validate_name(name)

        aid = int(aid)
        if atype == STUDENT:
            self.repository.update_student(aid, name)
        if atype == DISCIPLINE:
            self.repository.update_discipline(aid, name)

    def build_list(self, repository, header = "All students and diciplines"):
        if repository.is_empty():
            raise NoDataError("No data to show")

        data_string = "\n"
        data_string += header

        if repository.has_students() and repository.has_disciplines():
            data_string += "\n" + generator.generate_chars('-', 121) + "\n|" + generator.generate_chars(' ', 4) + "Students" + generator.generate_chars(' ', 47) + "|" + generator.generate_chars(' ', 4) + "Disciplines" +  generator.generate_chars(' ', 44) + "|\n" + generator.generate_chars('-', 121)
            
            students_length = len(repository.students)
            disciplines_length = len(repository.disciplines)
            max_length = max(students_length, disciplines_length)

            for i in range(max_length):
                if i < students_length:
                    student_string = str(repository.students[i])
                    data_string += "\n| " + student_string + generator.generate_chars(' ', 57 - len(student_string)) + " | "
                else:
                    data_string += "\n|" + generator.generate_chars(' ', 59) + "| "
                if i < disciplines_length:
                    discipline_string = str(repository.disciplines[i])
                    data_string += discipline_string + generator.generate_chars(' ', 57 - len(discipline_string)) + " |"
                else:
                    data_string += generator.generate_chars(' ', 58) + "|"

            data_string += "\n" + generator.generate_chars('-', 121)
            return data_string

        if repository.has_students():
            data_string += "\n" + generator.generate_chars('-', 60) + "\nStudents\n" + generator.generate_chars('-', 60)

            for student in repository.students:
                data_string += "\n" + str(student)

            return data_string

        if repository.has_disciplines():
            data_string += "\n" + generator.generate_chars('-', 60) + "\nDisciplines\n" + generator.generate_chars('-', 60)

            for discipline in repository.disciplines:
                data_string += "\n" + str(discipline)

            return data_string

    def print_grades(self):
        if self.repository.has_grades():
            count = 1
            data = "\nGrades\n" + generator.generate_chars('-', 60)
            for grade in self.repository.grades:
                data += "\n" + str(count) + ". " + str(grade)
                count += 1
            print(data)
            return
        raise NoDataError("No grades in repository to show")

    def list_all(self):
        return self.build_list(self.repository)

    def grade(self, params):
        validate_param_length(params, 3)

        sid = params[0]
        did = params[1]
        grade = params[2]

        validate_existing_student_id(self.repository, sid)
        validate_existing_discipline_id(self.repository, did)
        validate_grade(grade)

        self.repository.add_grade(int(did), int(sid), float(grade))
        # self.print_grades()

    def search(self, params):
        validate_param_length(params, 1)

        keyword = params[0]

        validate_keyword(keyword)

        repo_with_search = self.repository.search(keyword)
        return self.build_list(repo_with_search, "Results for the keyword '" + keyword + "'")

    def format_float(self, value):
        value = round(value, 2)
        int_value = int(value)
        if value == int_value:
            value = int_value
        return str(value)

    def see_failing_students(self):
        faillings = self.repository.get_failing_students()

        if len(faillings) < 1:
            raise NoDataError("No students failing at one or more disciplines")

        students_to_show = "\nStudents failing:"
        count = 1
        for f in faillings:
            students_to_show += "\n" + str(count) + ". " + f.student_name + " at " + f.discipline_name + " with the average of " + f.get_formated_average()
            count += 1
        return students_to_show

    def see_best_students(self):
        best_students = self.repository.get_best_students()

        if len(best_students) < 1:
            raise NoDataError("No students with grades to show")

        best_students = sorted(best_students, key = lambda b: b.value, reverse = True)

        students_to_show = "\nBest students in descending order of average grades:"
        count = 1
        for b in best_students:
            students_to_show += "\n" + str(count) + ". " + b.student_name + " with the average of " + b.get_formated_average()
            count += 1
        return students_to_show

    def see_grades(self):
        disciplines_with_grades = self.repository.get_disciplines_with_grades()

        if len(disciplines_with_grades) < 1:
            raise NoDataError("No disciplines with grades to show")

        disciplines_with_grades = sorted(disciplines_with_grades, key = lambda d: d.value, reverse = True)

        disciplines_to_show = "\nDisciplines with grades in descending order of average grades:"
        count = 1
        for d in disciplines_with_grades:
            disciplines_to_show += "\n" + str(count) + ". " + d.discipline_name + " with the average of " + d.get_formated_average()
            count += 1
        return disciplines_to_show

    def undo(self):
        self.repository.undo()

    def redo(self):
        self.repository.redo()
