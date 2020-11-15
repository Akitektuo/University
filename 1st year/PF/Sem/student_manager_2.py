def create_student(sid, name, grade):
    '''
    Create a student
    params:
        sid - id
        name - string of len >= 3
        grade - int between 1 and 10
    output:
        success - return the student
        error - return none
    '''
    if len(name) < 3:
        return None
    grade = int(grade)
    if grade < 1 or grade > 10:
        return None
    return [sid, name, grade]

def get_id(student):
    return student[0]
    
def get_name(student):
    return student[1]
    
def get_grade(student):
    return student[2]
    
def find_student(student_list, sid):
    '''
    Find student having given id
    params:...
    output:
        Student having given id
        None, student with given id not in list
    '''
    for s in student_list:
        if get_id(s) == sid:
            return s
    return None

def add_student(student_list, student):
    '''
    Add student to the list
    params:
        studentList - the list of students
        student - the student
    output:
        0 - success
        1 - Duplicate student id
    '''
    if find_student(student_list, get_id(student)) != None:
        return 1
    student_list.append(student)
    return 0

def test_add_student():
    slist = []
    s1 = create_student(1, "Marie", 10)
    assert add_student(slist, s1) == 0
    assert len(slist) == 1
    assert get_id(slist[0]) == 1
    assert add_student(slist, s1) == 1
    assert len(slist) == 1

def read_command(predefined_command = ""):
    '''
    Read and parse the user's command
    '''
    command = predefined_command
    if predefined_command == "":
        command = input("> ")
    split_index = command.find(' ')
    if split_index == -1:
        return (command)
    params = command[split_index:]
    command = command[:split_index]
    params = params.split(",")
    for i in range(len(params)):
        params[i] = params[i].strip()
    return (command, params)

def add_student_ui(stundent_list, params):
    if len(params) != 3:
        print("Bad student parameters")
        return
    s = create_student(params[0], params[1], params[2])
    if s == None:
        print("Invalid student data")
        return
    if add_student(stundent_list, s) == 1:
        print("Student with the same id already exists")
        return

def start():
    stundent_list = []
    while True:
        commands = read_command()
        command = commands[0]
        params = commands[1]
        if command == "add":
            add_student_ui(stundent_list, params)
        elif command == "exit":
            return
        else:
            print("Bad command")

start()