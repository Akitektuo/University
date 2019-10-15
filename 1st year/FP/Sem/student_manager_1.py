def create_student(sid = 0, name = "", grade = 0):
    return {
        "id": sid,
        "name": name,
        "grade": grade
    }

def set_student_id(student, sid):
    student["id"] = sid

def set_student_name(student, name):
    student["name"] = name

def set_student_grade(student, grade):
    student["grade"] = grade

def get_student_id(student):
    return student["id"]

def get_student_name(student):
    return student["name"]

def get_student_grade(student):
    return student["grade"]

def print_menu():
    print("\n1. Add student")
    print("2. Remove student")
    print("3. Show students")
    print("4. Exit")

def init_students():
    students = []
    students.append(create_student(100, "Alice", 10))
    students.append(create_student(101, "Bob", 10))
    return students

def read_student():
    return create_student(
        sid = int(input("\nGive student id: ")),
        name = input("Give student name: "),
        grade = int(input("Give student grade: "))
    )

def is_student_valid(student, withStudents):
    for s in withStudents:
        if get_student_id(s) == get_student_id(student):
            return "A student with the same id already exists"
    if len(get_student_name(student)) == 0:
        return "A student's name can not be empty"
    grade = get_student_grade(student)
    if grade < 1:
        return "A student's grade can not be lower than 1"
    if grade > 10:
        return "A student's grade can not be higher than 10"
    return ""

def add_student(students):
    student = read_student()
    error = is_student_valid(student, students)
    if (error == ""):
        input("Student added successfully, press enter to return to menu...")
    else:
        input("ERROR: " + error + ", press enter to return to menu...")

def show_students(students):
    print("\nList of students")
    for s in students:
        print ("id: " + str(get_student_id(s)) + ", name: " + get_student_name(s) + ", grade: " + str(get_student_grade(s)))
    input("Press enter to return to menu...")

def start():
    students = init_students()
    while True:
        print_menu()
        choice = input("> ")
        if choice == "1":
            add_student(students)
        if choice == "3":
            show_students(students)
        elif choice == "4":
            return
        else:
            print("Invalid command")

start()