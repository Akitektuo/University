from repository import Repository

STUDENT = "student"
DISCIPLINE = "discipline"
BOTH = "both"

class ValidationError(Exception):
    pass

def validate_param_length(params, expected_length):
    if len(params) != expected_length:
        raise ValidationError("Params length requirement not met, wanted " + str(expected_length) + " params")

def is_int(value):
    try:
        int(value)
        return True
    except:
        return False

def is_type_valid(atype):
    return atype == STUDENT or atype == DISCIPLINE or atype == BOTH

def validate_student_id(repository, sid):
    if not is_int(sid):
        raise ValidationError("Given id must be an integer")
    if repository.is_student_id(sid):
        raise ValidationError("Student ID " + sid + " already exists")

def validate_discipline_id(repository, did):
    if not is_int(did):
        raise ValidationError("Given id must be an integer")
    if repository.is_discipline_id(did):
        raise ValidationError("Discipline ID " + did + " already exists")

def validate_name(name):
    if len(name) < 4:
        raise ValidationError("Name must be at least 4 characters")

def validate_type(atype):
    if not is_type_valid(atype):
        raise ValidationError(atype + " type is not valid, must be 'student', 'discipline' or 'both'")