from domains import Student
from domains import Discipline
from domains import Grade
from exceptions import NoDataError
from services import Services, Group
from randomiser import Randomiser
from validator import *
import generator

class Controller:

    def __init__(self):
        self.services = Services()

    def preload_list(self):
        """
        Adds to the repository a list of predefined values that are randomised

        Input: -
        Output: -
        """
        randomiser = Randomiser()
        data_to_add = Group()

        for i in range(10):
            random_name = randomiser.get_random_first_name() + " " + randomiser.get_random_last_name()

            data_to_add.students.append(i, random_name)
            data_to_add.disciplines.append(i, randomiser.get_random_discipline_name())
        
        for i in range(10):
            data_to_add.grades.append(
                randomiser.get_random(data_to_add.disciplines),
                randomiser.get_random(data_to_add.students),
                randomiser.get_random_grade()
            )

        self.services.add_all(data_to_add)

    def add(self, params):
        """
        Add a student or discipline to the repository

        Input: list - list of params (type, id, name)
        Output: -
        """
        validate_param_length(params, 3)

        atype = params[0]
        aid = params[1]
        name = params[2]

        validate_type(atype)
        if atype == STUDENT:
            validate_new_student_id(self.services, aid)
        if atype == DISCIPLINE:
            validate_new_discipline_id(self.services, aid)
        validate_name(name)

        aid = int(aid)
        if atype == STUDENT:
            self.services.add_student(aid, name)
        if atype == DISCIPLINE:
            self.services.add_discipline(aid, name)

    def remove(self, params):
        """
        Remove a student or discipline from the repository

        Input: list - list of params (type, id)
        Output: -
        """
        validate_param_length(params, 2)

        atype = params[0]
        aid = params[1]

        validate_type(atype)
        if atype == STUDENT:
            validate_existing_student_id(self.services, aid)
        if atype == DISCIPLINE:
            validate_existing_discipline_id(self.services, aid)

        aid = int(aid)
        if atype == STUDENT:
            self.services.remove_student(aid)
        if atype == DISCIPLINE:
            self.services.remove_discipline(aid)

    def update(self, params):
        """
        Update a student or discipline from the repository

        Input: list - list of params (type, id, new_name)
        Output: -
        """
        validate_param_length(params, 3)

        atype = params[0]
        aid = params[1]
        name = params[2]

        validate_type(atype)
        if atype == STUDENT:
            validate_existing_student_id(self.services, aid)
        if atype == DISCIPLINE:
            validate_existing_discipline_id(self.services, aid)
        validate_name(name)

        aid = int(aid)
        if atype == STUDENT:
            self.services.update_student(aid, name)
        if atype == DISCIPLINE:
            self.services.update_discipline(aid, name)

    def build_list(self, repository, header = "All students and diciplines"):
        """
        Parses a repository's data into a string to be printed

        Input: 
            Repository - repository to parse
            string - header to show at the beginning of the generated string
        Output: string - the parsed repository
        """
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

    def list_all(self):
        """
        Builds are returns a list as string representing the repository to be printed

        Input: -
        Output: string - string representing the repository
        """
        return self.build_list(self.services)

    def grade(self, params):
        """
        Gives a grade to a student at a given discipline

        Input: params - list of params (student_id, discipline_id, grade)
        Output: -
        """
        validate_param_length(params, 3)

        sid = params[0]
        did = params[1]
        grade = params[2]

        validate_existing_student_id(self.services, sid)
        validate_existing_discipline_id(self.services, did)
        validate_grade(grade)

        self.services.add_grade(int(did), int(sid), float(grade))

    def search(self, params):
        """
        Searches by a keyword and returns a string with the results

        Input: params - list of params (keyword)
        Output: string - results of search
        """
        validate_param_length(params, 1)

        keyword = params[0]

        validate_keyword(keyword)

        repo_with_search = self.services.search(keyword)
        return self.build_list(repo_with_search, "Results for the keyword '" + keyword + "'")

    def see_failing_students(self):
        """
        Returns a string representing the failing students

        Input: -
        Output: string - all failing students
        """
        faillings = self.services.get_failing_students()

        if len(faillings) < 1:
            raise NoDataError("No students failing at one or more disciplines")

        students_to_show = "\nStudents failing:"
        count = 1
        for f in faillings:
            students_to_show += "\n" + str(count) + ". " + f.student_name + " at " + f.discipline_name + " with the average of " + f.get_formated_average()
            count += 1
        return students_to_show

    def see_best_students(self):
        """
        Returns a string representing all students ordered by their average grades

        Input: -
        Output: string - all students ordered by their average grades
        """
        best_students = self.services.get_best_students()

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
        """
        Returns a string representing all disciplines ordered by their average grades

        Input: -
        Output: string - all disciplines ordered by their average grades
        """
        disciplines_with_grades = self.services.get_disciplines_with_grades()

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
        """
        Undos the last operation that modified the repository

        Input: -
        Output: -
        """
        self.services.undo()

    def redo(self):
        """
        Redos the last operation that modified the repository

        Input: -
        Output: -
        """
        self.services.redo()
