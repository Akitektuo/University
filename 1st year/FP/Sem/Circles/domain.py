# c = [0, 0, 1] unit circle not valid
def create_circle(x, y, r):
    '''
    Creates a circle centered in (x, y) with radius 3
    params:
        x,y,r - ints, where x,y,r > 0 and circle in (+, +)
    Returns the circle
    Raises ValueError if circle is invalid
    '''
    if r <= 0:
        raise ValueError("Radius < 0!")
    if x < r or y < r:
        raise ValueError("Circle not in 1st quadrant!")
    return [x, y, r]

def get_x(c):
    return c[0]

def get_y(c):
    return c[1]

def get_r(c):
    return c[2]

def to_str(c):
    return "(" + str(get_x(c)) + ", " + str(get_y(c)) + "), r = " + str(get_r(c))

def test_init():
    circles = []
    circles.append(create_circle(2, 2, 1))
    circles.append(create_circle(2, 3, 1))
    circles.append(create_circle(3, 2, 1))

def test_create_circle():
    # nice circle
    c = create_circle(1, 1, 1)
    assert get_x(c) == 1 and get_y(c) == 1 and get_r(c) == 1

    # radius < 0
    try:
        c = create_circle(1, 1, -1)
        assert False
    except ValueError:
        assert True

    # not in first q
    try:
        c = create_circle(1, 1, 2)
        assert False
    except ValueError:
        assert True

def test_to_str():
    c = create_circle(2, 2, 1)
    assert to_str(c) == '(2, 2), r = 1'
    c = create_circle(29, 21, 11)
    assert to_str(c) == '(29, 21), r = 11'

test_create_circle()
test_to_str()