from flight import *
from validator import *

def preload_flights():
    """
    Preloads the flight list with flights

    Input: -
    Output: -
    """
    flights = []
    flights.append(create_flight("0B3001", 30, "Cluj-Napoca", "Bucharest"))
    flights.append(create_flight("0B3002", 60, "Cluj-Napoca", "London"))
    flights.append(create_flight("0B4001", 120, "Bucharest", "Dubai"))
    flights.append(create_flight("0B4003", 90, "Cluj-Napoca", "Atena"))
    flights.append(create_flight("0B2005", 360, "New York", "Bucharest"))
    return flights

def add(flights, code, duration, departure_city, destination_city):
    """
    Adds a new flight to the flights list

    Input:
        flights - the list of flights
        code - flight code
        duration - the duration of the flight
        departure_city - the departure of the flight
        destination_city - the destination of the flight
    Output: -
    """
    validate_code(code)
    validate_duration(duration)
    validate_departure_city(departure_city)
    validate_destination_city(destination_city)

    duration = int(duration)

    flights.append(create_flight(code, duration, departure_city, destination_city))

def modify_duration(flights, code, duration):
    """
    Modifies a flight's duration based on a code

    Input:
        flights - the list of flights
        code - flight code
        duration - the duration of the flight
    Output: -
    """
    validate_code(code)
    validate_duration(duration)

    duration = int(duration)

    no_match = True
    for f in flights:
        if get_code(f) == code:
            no_match = False
            set_duration(f, duration)

    if no_match:
        raise Exception("No flight found with the code " + code)

def reroute(flights, old_destination, new_destination):
    """
    Reroutes an existing flight from an old destination to a new one

    Input:
        flights - the list of flights
        old_destination - the old destination of the flight
        new_destination - the new destination of the flight
    Output: -
    """
    validate_destination_city(old_destination)
    validate_destination_city(new_destination)

    no_match = True
    for f in flights:
        no_match = False
        if get_destination_city(f) == old_destination:
            set_destination_city(f, new_destination)

    if no_match:
        raise Exception("No flight found with the old destination " + old_destination)


def get_all_flights(flights):
    """
    Builds and returns a string representing the flights

    Input: -
    Output: string - list representing the flights
    """
    sorted_flights = sorted(flights, key = lambda f: get_duration(f))

    count = 0
    builded_list = "All flights are:"
    for f in sorted_flights:
        count += 1
        builded_list += "\n" + str(count) + ". " + to_string(f)

    if count < 1:
        raise Exception("No flights to show")

    return builded_list


