from domains import Student, Discipline, Grade
from pickle import Unpickler, Pickler

class Repository:
    STORE_MEMORY = "inmemory"
    STORE_TEXT = "textfiles"
    STORE_BINARY = "binaryfiles"

    TYPE_STUDENTS = "students"
    TYPE_DISCIPLINES = "disciplines"
    TYPE_GRADES = "grades"

    FILE_TXT = ".txt"
    FILE_PICKLE = ".pickle"

    def __init__(self, dtype):
        self.store_type = None
        self.path_students = ""
        self.path_discipliness = ""
        self.path_grades = ""

        self.data = []
        self.type = dtype

        self.__init_repo()
        if self.store_type != Repository.STORE_MEMORY:
            self.__parse_from_file()

    def __init_repo(self):
        settings = open("settings.properties", "r")
        properties = settings.readlines()
        for prop in properties:
            if '=' not in prop:
                continue
            attr = prop.split('=')

            if "repository" in attr[0]:
                if Repository.STORE_MEMORY in attr[1]:
                    self.store_type = Repository.STORE_MEMORY
                    continue
                if Repository.STORE_TEXT in attr[1]:
                    self.store_type = Repository.STORE_TEXT
                    continue
                if Repository.STORE_BINARY in attr[1]:
                    self.store_type = Repository.STORE_BINARY
                    continue
                self.store_type = Repository.STORE_MEMORY
                print("Failed parsing repository type from 'settings.properties', using default: in memory")
                return

            if Repository.TYPE_STUDENTS in attr[0]:
                if Repository.FILE_TXT in attr[1] or Repository.FILE_PICKLE in attr[1]:
                    self.path_students = attr[1].strip()
                    continue
                self.store_type = Repository.STORE_MEMORY
                print("No students file found in 'settings.properties', using default: in memory")
                return

            if Repository.TYPE_DISCIPLINES in attr[0]:
                if Repository.FILE_TXT in attr[1] or Repository.FILE_PICKLE in attr[1]:
                    self.path_disciplines = attr[1].strip()
                    continue
                self.store_type = Repository.STORE_MEMORY
                print("No disciplines file found in 'settings.properties', using default: in memory")
                return

            if Repository.TYPE_GRADES in attr[0]:
                if Repository.FILE_TXT in attr[1] or Repository.FILE_PICKLE in attr[1]:
                    self.path_grades = attr[1].strip()
                    continue
                self.store_type = Repository.STORE_MEMORY
                print("No grades file found in 'settings.properties', using default: in memory")
                return

            if not self.store_type:
                self.store_type = Repository.STORE_MEMORY
                print("Failed parsing data from 'settings.properties', using default: in memory")
                return

    def __parse_from_file(self):
        if self.type == Repository.TYPE_STUDENTS:
            self.__parse_students_from_file()
            return
        
        if self.type == Repository.TYPE_DISCIPLINES:
            self.__parse_disciplines_from_file()
            return

        if self.type == Repository.TYPE_GRADES:
            self.__parse_grades_from_file()
            return

    def __parse_students_from_file(self):
        if Repository.FILE_TXT in self.path_students:
            file_students = open(self.path_students, "r")
            lines = file_students.readlines()
            for line in lines:
                data = line.split(';')
                self.data.append(Student(int(data[0]), data[1]))
            file_students.close()
            return

        if Repository.FILE_TXT in self.path_students:
            file_students = Unpickler(self.path_students)
            self.data = file_students.load()
            return

    def __parse_disciplines_from_file(self):
        if Repository.FILE_TXT in self.path_disciplines:
            file_disciplines = open(self.path_disciplines, "r")
            lines = file_disciplines.readlines()
            for line in lines:
                data = line.split(';')
                self.data.append(Discipline(int(data[0]), data[1]))
            file_disciplines.close()
            return

        if Repository.FILE_TXT in self.path_disciplines:
            file_disciplines = Unpickler(self.path_disciplines)
            self.data = file_disciplines.load()
            return

    def __parse_grades_from_file(self):
        if Repository.FILE_TXT in self.path_grades:
            file_grades = open(self.path_grades, "r")
            lines = file_grades.readlines()
            for line in lines:
                data = line.split(';')
                self.data.append(Grade(Student(data[0], ""), Discipline(data[1], ""), data[2]))
            file_grades.close()
            return

        if Repository.FILE_TXT in self.path_grades:
            file_grades = Unpickler(self.path_grades)
            self.data = file_grades.load()
            return

    def __parse_to_file(self):
        if self.type == Repository.TYPE_STUDENTS:
            self.__parse_students_to_file()
            return
        
        if self.type == Repository.TYPE_DISCIPLINES:
            self.__parse_disciplines_to_file()
            return

        if self.type == Repository.TYPE_GRADES:
            self.__parse_grades_to_file()
            return

    def __parse_students_to_file(self):
        if Repository.FILE_TXT in self.path_students:
            file_students = open(self.path_students, "w")
            for student in self.data:
                file_students.write(str(student.id) + ';' + student.name + "\r\n")
            file_students.close()
            return

        if Repository.FILE_TXT in self.path_students:
            file_students = Pickler(self.path_students)
            file_students.dump(self.data)
            return

    def __parse_disciplines_to_file(self):
        if Repository.FILE_TXT in self.path_disciplines:
            file_disciplines = open(self.path_disciplines, "w")
            for discipline in self.data:
                file_disciplines.write(str(discipline.id) + ';' + discipline.name + "\r\n")
            file_disciplines.close()
            return

        if Repository.FILE_TXT in self.path_disciplines:
            file_disciplines = Pickler(self.path_disciplines)
            file_disciplines.dump(self.data)
            return

    def __parse_grades_to_file(self):
        if Repository.FILE_TXT in self.path_grades:
            file_grades = open(self.path_grades, "w")
            for grade in self.data:
                file_grades.write(str(grade.student.id) + ';' + str(grade.discipline.id) + ';' + grade.value + "\r\n")
            file_grades.close()
            return

        if Repository.FILE_TXT in self.path_grades:
            file_grades = Pickler(self.path_grades)
            file_grades.dump(self.data)
            return

    def clear(self):
        self.data.clear()

    def size(self):
        return len(self.data)
        
    def is_empty(self):
        return self.size() < 1
        
    def is_not_empty(self):
        return not self.is_empty()

    def add(self, element):
        self.data.append(element)
        if self.store_type != Repository.STORE_MEMORY:
            self.__parse_to_file()

    def add_all(self, elements):
        self.data.extend(elements)
        if self.store_type != Repository.STORE_MEMORY:
            self.__parse_to_file()

    def remove(self, condition):
        initial_data = list(self.data)
        for element in initial_data:
            if condition(element):
                self.data.remove(element)
        if self.store_type != Repository.STORE_MEMORY:
            self.__parse_to_file()
