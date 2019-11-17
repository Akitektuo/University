def create_flight(code, duration, departure_city, destination_city):
    return [code, duration, departure_city, destination_city]

def get_code(flight):
    return flight[0]

def get_duration(flight):
    return flight[1]

def get_departure_city(flight):
    return flight[2]

def get_destination_city(flight):
    return flight[3]

def set_duration(flight, duration):
    flight[1] = duration

def set_destination_city(flights, destination_city):
    flights[3] = destination_city

def to_string(flight):
    return "Flight with code " + get_code(flight) + " expected to arrive in " + str(get_duration(flight)) + " minutes from " + get_departure_city(flight) + " to " + get_destination_city(flight) 