from domains.student import Student
from domains.discipline import Discipline
from domains.grade import Grade
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

        self.repository.copy_from(repository_to_add)

    def add(self, params):
        atype = params[0]
        aid = params[1]
        name = params[2]

        validate_param_length(params, 3)
        validate_type(atype)
        if atype == STUDENT:
            validate_student_id(repository, aid)
        if atype == DISCIPLINE:
            validate_student_id(repository, aid)
        validate_name(name)

        if atype == STUDENT:
            self.repository.add_student(aid, name)
        if atype == DISCIPLINE:
            self.repository.add_discipline(aid, name)

    def list_all_data(self):
        if self.repository.is_empty():
            raise NoDataError("No data in repository")

        data = ""

        if self.repository.has_students():
            data += "\nStudents\n" + generator.generate_chars('-', 50) + "\n"
            for student in self.repository.students:
                data += str(student) + "\n"

        if self.repository.has_disciplines():
            data += "\nDisciplines\n" + generator.generate_chars('-', 50) + "\n"
            for discipline in self.repository.disciplines:
                data += str(discipline) + "\n"

        if self.repository.has_grades():
            data += "\nGrades\n" + generator.generate_chars('-', 50)
            for grade in self.repository.grades:
                data += "\n" + str(grade)
        
        return data
