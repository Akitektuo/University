from domains import Coordinate

map_number_to_letter = {
    0: 'A',
    1: 'B',
    2: 'C',
    3: 'D',
    4: 'E',
    5: 'F',
    6: 'G',
    7: 'H',
    8: 'I',
    9: 'J'
}

map_letter_to_number = {
    'A': 0,
    'B': 1,
    'C': 2,
    'D': 3,
    'E': 4,
    'F': 5,
    'G': 6,
    'H': 7,
    'I': 8,
    'J': 9
}

def is_int(var):
    try:
        int(var)
        return True
    except:
        return False

def validate_coordinate_input(coord):
    coord = coord.replace(' ', '')
    if len(coord) != 2:
        raise Exception("The coordinates should be in the form of 'A0' or '0A'")
    coord = Coordinate(coord[0], coord[1])

    if coord.x in map_letter_to_number:
        if not is_int(coord.y):
            raise Exception("The coordinates should be in the form of 'A0' or '0A'")
        coord.y = int(coord.y)
        if coord.y not in map_number_to_letter:
            raise Exception("The coordinates on the Y-axis are from '0' to '9'")
        return Coordinate(coord.y, map_letter_to_number[coord.x])

    if coord.y not in map_letter_to_number:
        raise Exception("The coordinates on the X-axi are from 'A' to 'J'")
    if not is_int(coord.x):
        raise Exception("The coordinates should be in the form of 'A0' or '0A'")
    coord.x = int(coord.x)
    if coord.x not in map_number_to_letter:
        raise Exception("The coordinates on the Y-axis are from '0' to '9'")
    return Coordinate(coord.x, map_letter_to_number[coord.y])

def validate_coordinates(coord_start, coord_end, unplaced_boats):
    if coord_start.x != coord_end.x and coord_start.y != coord_end.y:
        raise Exception("The coordinates should have at least one axis: " + str(coord_start.x) + "!=" + coord_start.y + " & " + str(coord_end.x) + "!=" + coord_end.y)
    if coord_start.x == coord_end.x:
        dist = abs(coord_start.y - coord_end.y) + 1
        if dist not in unplaced_boats:
            raise Exception("No boat left with size " + str(dist))
        return dist
    dist = abs(coord_start.x - coord_end.x) + 1
    if dist not in unplaced_boats:
        raise Exception("No boat left with size " + str(dist))
    return dist

