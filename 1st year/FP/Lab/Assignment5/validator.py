from exceptions import ValidationError

STUDENT = "student"
DISCIPLINE = "discipline"
BOTH = "both"

def validate_param_length(params, expected_length):
    if len(params) != expected_length:
        raise ValidationError("Params length requirement not met, wanted " + str(expected_length) + " params")

def is_int(value):
    try:
        int(value)
        return True
    except:
        return False

def is_float(value):
    try:
        float(value)
        return True
    except:
        return False

def is_type_valid(atype):
    return atype == STUDENT or atype == DISCIPLINE or atype == BOTH

def validate_int_id(string_id):
    if not is_int(string_id):
        raise ValidationError("Given id must be an integer")

def validate_new_student_id(repository, sid):
    validate_int_id(sid)
    if repository.is_student_id(int(sid)):
        raise ValidationError("Student ID " + sid + " already exists")

def validate_new_discipline_id(repository, did):
    validate_int_id(did)
    if repository.is_discipline_id(int(did)):
        raise ValidationError("Discipline ID " + did + " already exists")

def validate_existing_student_id(repository, sid):
    validate_int_id(sid)
    if not repository.is_student_id(int(sid)):
        raise ValidationError("No student found with ID " + sid + ", press 4 to see the list with IDs")

def validate_existing_discipline_id(repository, did):
    validate_int_id(did)
    if not repository.is_discipline_id(int(did)):
        raise ValidationError("No discipline found with ID " + did + ", press 4 to see the list with IDs")

def validate_name(name):
    if len(name) < 4:
        raise ValidationError("Name must be at least 4 characters")

def validate_type(atype):
    if not is_type_valid(atype):
        raise ValidationError(atype + " type is not valid, must be 'student', 'discipline' or 'both'")

def validate_grade(grade):
    if not is_float(grade):
        raise ValidationError("The given grade must be a float between 1 and 10")
    grade = float(grade)
    if 1 > grade or grade > 10:
        raise ValidationError("The given grade must be between 1 and 10")

def validate_keyword(keyword):
    if len(keyword) < 1:
        raise ValidationError("Search aborted, keyword must have at least one character")