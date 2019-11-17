from services import *

def test_add():
    flights = []

    try:
        add(flights, "", "", "", "")
        return False
    except:
        return True
    assert len(flights) == 0

    try:
        add(flights, "ab", "ab", "ab", "ab")
        return False
    except:
        return True
    assert len(flights) == 0

    try:
        add(flights, "abc", "ab", "ab", "ab")
        return False
    except:
        return True
    assert len(flights) == 0

    try:
        add(flights, "abc", "15", "ab", "ab")
        return False
    except:
        return True
    assert len(flights) == 0

    try:
        add(flights, "abc", "20", "ab", "ab")
        return False
    except:
        return True
    assert len(flights) == 0

    try:
        add(flights, "abc", "20", "abc", "ab")
        return False
    except:
        return True
    assert len(flights) == 0

    add(flights, "abc", "20", "abc", "abc")
    assert len(flights) == 1

test_add()