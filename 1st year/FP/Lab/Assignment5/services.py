from domains.student import Student
from domains.discipline import Discipline
from domains.grade import Grade
from repository import Repository
from randomiser import Randomiser
from exceptions.no_data_error import NoDataError
import generator

class Services:

    def __init__(self):
        self.repository = Repository()

    def preload_list(self):
        randomiser = Randomiser()
        repository_to_add = Repository()

        for i in range(10):
            random_name = randomiser.get_random_first_name() + " " + randomiser.get_random_last_name()

            new_student = Student(i, random_name)
            new_discipline = Discipline(i, randomiser.get_random_discipline_name())

            repository_to_add.students.append(new_student)
            repository_to_add.disciplines.append(new_discipline)
        
        for i in range(10):
            repository_to_add.grades.append(Grade(randomiser.get_random(repository_to_add.disciplines), randomiser.get_random(repository_to_add.students), randomiser.get_random_grade()))

        self.repository.copy_from(repository_to_add)

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
            data += "\nGrades\n" + generator.generate_chars('-', 50) + "\n"
            for grade in self.repository.grades:
                data += str(grade) + "\n"
        
        return data
