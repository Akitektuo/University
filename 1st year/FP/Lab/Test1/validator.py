def is_int(value):
    try:
        int(value)
        return True
    except:
        return False

def validate_code(code):
    validate_string(code, "The code must have least 3 characters")

def validate_departure_city(departure_city):
    validate_string(departure_city, "The departure city must have least 3 characters")

def validate_destination_city(destination_city):
    validate_string(destination_city, "The destination city must have least 3 characters")

def validate_string(string, error_message):
    if len(string) < 3:
        raise Exception(error_message)

def validate_duration(duration):
    if not is_int(duration):
        raise Exception("The duration must be an integer")
    duration = int(duration)
    if duration < 20:
        raise Exception("The duration of a flight cannot be less than 20 minutes")