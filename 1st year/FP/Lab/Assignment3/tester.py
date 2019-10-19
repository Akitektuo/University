from __future__ import absolute_import
from function import Function

def run_tests():
    run_add_test()

def run_add_test():
    expenses = []
    function = Function(expenses)
    assert function.add(["", ""])
    assert len(expenses) == 0
    assert function.add(["abc", "abc"])
    assert len(expenses) == 0
    assert function.add(["0", "abc"])
    assert len(expenses) == 0
    assert function.add(["256", "abc"])
    assert len(expenses) == 0
    assert not function.add(["16", "others"])
    assert len(expenses) == 1

def run_insert_test():
    expenses = []
    function = Function(expenses)
    assert function.add(["", "", ""])
    assert len(expenses) == 0
    assert function.add(["abc", "abc", "abc"])
    assert len(expenses) == 0
    assert function.add(["0", "0", "abc"])
    assert len(expenses) == 0
    assert function.add(["32", "abc", "abc"])
    assert len(expenses) == 0
    assert function.add(["30", "abc", "abc"])
    assert len(expenses) == 0
    assert function.add(["29", "128", "abc"])
    assert len(expenses) == 0
    assert function.add(["28", "64", "others"])
    assert len(expenses) == 0
    assert not function.add(["27", "32", "others"])
    assert len(expenses) == 1
    

    