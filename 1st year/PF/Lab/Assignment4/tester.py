from __future__ import absolute_import
from services import Services

def run_tests():
    run_add_test()
    run_filter_above_test()
    run_undo_test()

def run_add_test():
    expenses = []
    services = Services(expenses)

    try:
        services.add(["", "", ""])
        assert False
    except:
        assert True
    assert len(expenses) == 0
    
    try:
        services.add(["abc", "abc", "abc"])
        assert False
    except:
        assert True
    assert len(expenses) == 0

    try:
        services.add(["0", "0", "abc"])
        assert False
    except:
        assert True
    assert len(expenses) == 0

    try:
        services.add(["32", "abc", "abc"])
        assert False
    except:
        assert True
    assert len(expenses) == 0

    try:
        services.add(["30", "abc", "abc"])
        assert False
    except:
        assert True
    assert len(expenses) == 0

    services.add(["28", "64", "food"])
    assert len(expenses) == 1

    expense = expenses[0]
    assert expense.cost == 64
    assert expense.category == "food"
    assert expense.day == 28

def run_filter_above_test():
    expenses = []
    services = Services(expenses)

    try:
        services.filter_above([""])
        assert False
    except:
        assert True

    try:
        services.filter_above(["abc"])
        assert False
    except:
        assert True

    services.filter_above(["0"])

    services.add(["8", "4", "transport"])
    assert len(expenses) == 1

    services.add(["7", "2", "transport"])
    assert len(expenses) == 2

    services.add(["6", "1", "transport"])
    assert len(expenses) == 3

    services.add(["5", "2", "cloathing"])
    assert len(expenses) == 4

    services.filter_above(["1"])
    assert len(expenses) == 3

def run_undo_test():
    expenses = []
    services = Services(expenses)

    services.add(["4", "4", "food"])
    assert len(expenses) == 1

    services.add(["4", "8", "housekeeping"])
    assert len(expenses) == 2

    services.add(["3", "16", "housekeeping"])
    assert len(expenses) == 3

    services.add(["2", "32", "internet"])
    assert len(expenses) == 4

    services.filter_above(["10"])
    assert len(expenses) == 2

    services.undo_last_action()
    assert len(expenses) == 4

    services.undo_last_action()
    assert len(expenses) == 3

    services.undo_last_action()
    assert len(expenses) == 2

    services.undo_last_action()
    assert len(expenses) == 1

    services.undo_last_action()
    assert len(expenses) == 0

    try:
        services.undo_last_action()
        assert False
    except:
        assert True
    