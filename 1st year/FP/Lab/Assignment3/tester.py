from __future__ import absolute_import
from function import Function
from datetime import date
import constants

def run_tests():
    run_add_test()
    run_insert_test()
    run_remove_day_test()
    run_remove_range_test()
    run_remove_category_test()
    run_list_category_test()
    run_list_category_condition_test()
    run_sum_category_test()
    run_max_day_test()
    run_sort_day_test()
    run_sort_category_test()
    run_filter_category_test()
    run_filter_category_condition_test()
    run_undo_test()

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
    assert not function.add(["16", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 1
    expense = expenses[0]
    assert expense.cost == 16
    assert expense.category == constants.CATEGORY_CLOATHING
    assert expense.day == date.today().day

def run_insert_test():
    expenses = []
    function = Function(expenses)
    assert function.insert(["", "", ""])
    assert len(expenses) == 0
    assert function.insert(["abc", "abc", "abc"])
    assert len(expenses) == 0
    assert function.insert(["0", "0", "abc"])
    assert len(expenses) == 0
    assert function.insert(["32", "abc", "abc"])
    assert len(expenses) == 0
    assert function.insert(["30", "abc", "abc"])
    assert len(expenses) == 0
    assert function.insert(["29", "128", "abc"])
    assert len(expenses) == 0
    assert not function.insert(["28", "64", constants.CATEGORY_FOOD])
    assert len(expenses) == 1
    expense = expenses[0]
    assert expense.cost == 64
    assert expense.category == constants.CATEGORY_FOOD
    assert expense.day == 28
    
def run_remove_day_test():
    expenses = []
    function = Function(expenses)
    assert not function.insert(["27", "8", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 1
    assert function.remove_day([""])
    assert len(expenses) == 1
    assert function.remove_day(["abc"])
    assert len(expenses) == 1
    assert function.remove_day(["0"])
    assert len(expenses) == 1
    assert function.remove_day(["32"])
    assert len(expenses) == 1
    assert not function.remove_day(["31"])
    assert len(expenses) == 1
    assert not function.remove_day(["1"])
    assert len(expenses) == 1
    assert not function.remove_day(["27"])
    assert len(expenses) == 0

def run_remove_range_test():
    expenses = []
    function = Function(expenses)
    assert not function.insert(["26", "4", constants.CATEGORY_INTERNET])
    assert len(expenses) == 1
    assert function.remove_range(["", "", ""])
    assert len(expenses) == 1
    assert function.remove_range(["abc", "", "abc"])
    assert len(expenses) == 1
    assert function.remove_range(["0", "", "abc"])
    assert len(expenses) == 1
    assert function.remove_range(["32", "", "abc"])
    assert len(expenses) == 1
    assert function.remove_range(["1", "", "abc"])
    assert len(expenses) == 1
    assert function.remove_range(["31", "", "0"])
    assert len(expenses) == 1
    assert function.remove_range(["2", "", "32"])
    assert len(expenses) == 1
    assert function.remove_range(["30", "", "3"])
    assert len(expenses) == 1
    assert not function.remove_range(["1", "", "10"])
    assert len(expenses) == 1
    assert not function.remove_range(["10", "to", "20"])
    assert len(expenses) == 1
    assert not function.remove_range(["1", "to", "20"])
    assert len(expenses) == 1
    assert not function.remove_range(["21", "to", "26"])
    assert len(expenses) == 0
    assert not function.remove_range(["1", "", "31"])
    assert len(expenses) == 0

def run_remove_category_test():
    expenses = []
    function = Function(expenses)
    assert not function.insert(["25", "2", constants.CATEGORY_OTHERS])
    assert len(expenses) == 1
    assert function.remove_category([""])
    assert len(expenses) == 1
    assert function.remove_category(["abc"])
    assert len(expenses) == 1
    assert not function.remove_category([constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 1
    assert not function.remove_category([constants.CATEGORY_OTHERS])
    assert len(expenses) == 0

def run_list_category_test():
    expenses = []
    function = Function(expenses)
    assert function.list_category([""])
    assert function.list_category([constants.CATEGORY_TRANSPORT])
    assert not function.insert(["24", "1", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 1
    assert '\n' in function.list_category([constants.CATEGORY_CLOATHING])

def run_list_category_condition_test():
    expenses = []
    function = Function(expenses)
    assert function.list_category(["", "", ""])
    assert function.list_category(["abc", "abc", "abc"])
    assert function.list_category([constants.CATEGORY_FOOD, "abc", "abc"])
    assert function.list_category([constants.CATEGORY_HOUSEKEEPING, 'a', "abc"])
    assert function.list_category([constants.CATEGORY_INTERNET, '-', "abc"])
    assert function.list_category([constants.CATEGORY_OTHERS, '<', "abc"])
    assert function.list_category([constants.CATEGORY_TRANSPORT, '=', "0"])
    assert not function.insert(["23", "2", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 1
    assert '\n' in function.list_category([constants.CATEGORY_CLOATHING, '>', -1])

def run_sum_category_test():
    expenses = []
    function = Function(expenses)
    assert function.sum_category([""])
    assert function.sum_category(["abc"])
    assert "0" in function.sum_category([constants.CATEGORY_CLOATHING])
    assert not function.insert(["22", "4", constants.CATEGORY_FOOD])
    assert len(expenses) == 1
    assert "4" in function.sum_category([constants.CATEGORY_FOOD])
    assert not function.insert(["21", "8", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 2
    assert "4" in function.sum_category([constants.CATEGORY_FOOD])
    assert not function.insert(["20", "16", constants.CATEGORY_FOOD])
    assert len(expenses) == 3
    assert "20" in function.sum_category([constants.CATEGORY_FOOD])

def run_max_day_test():
    expenses = []
    function = Function(expenses)
    assert function.max_day([""])
    assert function.max_day(["abc"])
    assert function.max_day(["-1"])
    assert function.max_day(["0"])
    assert function.max_day(["32"])
    assert function.max_day(["19"])
    assert not function.insert(["18", "32", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 1
    assert "32" in function.max_day(["18"])
    assert not function.insert(["17", "64", constants.CATEGORY_INTERNET])
    assert len(expenses) == 2
    assert "32" in function.max_day(["18"])
    assert not function.insert(["18", "128", constants.CATEGORY_OTHERS])
    assert len(expenses) == 3
    assert "128" in function.max_day(["18"])

def run_sort_day_test():
    expenses = []
    function = Function(expenses)
    assert function.sort_day([""])
    assert function.sort_day(["abc"])
    assert function.sort_day(["-1"])
    assert function.sort_day(["0"])
    assert function.sort_day(["32"])
    assert function.sort_day(["31"])
    assert not function.insert(["16", "128", constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 1
    assert not function.insert(["16", "256", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 2
    assert not function.insert(["16", "64", constants.CATEGORY_FOOD])
    assert len(expenses) == 3
    assert not function.insert(["15", "512", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 4
    assert '\n' in function.sort_day(["16"])
    assert "4." not in function.sort_day(["16"])

def run_sort_category_test():
    expenses = []
    function = Function(expenses)
    assert function.sort_category([""])
    assert function.sort_category(["abc"])
    assert function.sort_category([constants.CATEGORY_INTERNET])
    assert not function.insert(["14", "128", constants.CATEGORY_OTHERS])
    assert len(expenses) == 1
    assert not function.insert(["13", "256", constants.CATEGORY_OTHERS])
    assert len(expenses) == 2
    assert not function.insert(["12", "64", constants.CATEGORY_OTHERS])
    assert len(expenses) == 3
    assert not function.insert(["11", "512", constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 4
    assert '\n' in function.sort_category([constants.CATEGORY_OTHERS])
    assert "4." not in function.sort_category([constants.CATEGORY_OTHERS])

def run_filter_category_test():
    expenses = []
    function = Function(expenses)
    assert function.filter_category([""])
    assert function.filter_category(["abc"])
    assert not function.filter_category([constants.CATEGORY_TRANSPORT])
    assert not function.insert(["11", "32", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 1
    assert not function.insert(["10", "16", constants.CATEGORY_FOOD])
    assert len(expenses) == 2
    assert not function.insert(["9", "8", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 3
    assert not function.filter_category([constants.CATEGORY_CLOATHING])
    assert len(expenses) == 2

def run_filter_category_condition_test():
    expenses = []
    function = Function(expenses)
    assert function.filter_category_condition(["", "", ""])
    assert function.filter_category_condition(["abc", "abc", "abc"])
    assert function.filter_category_condition([constants.CATEGORY_FOOD, "abc", "abc"])
    assert function.filter_category_condition([constants.CATEGORY_HOUSEKEEPING, '-', "abc"])
    assert function.filter_category_condition([constants.CATEGORY_INTERNET, '<', "abc"])
    assert not function.filter_category_condition([constants.CATEGORY_OTHERS, '=', "0"])
    assert not function.insert(["8", "4", constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 1
    assert not function.insert(["7", "2", constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 2
    assert not function.insert(["6", "1", constants.CATEGORY_TRANSPORT])
    assert len(expenses) == 3
    assert not function.insert(["5", "2", constants.CATEGORY_CLOATHING])
    assert len(expenses) == 4
    assert not function.filter_category_condition([constants.CATEGORY_TRANSPORT, '>', "1"])
    assert len(expenses) == 2

def run_undo_test():
    expenses = []
    function = Function(expenses)
    assert not function.insert(["4", "4", constants.CATEGORY_FOOD])
    assert len(expenses) == 1
    assert not function.insert(["4", "8", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 2
    assert not function.insert(["3", "16", constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 3
    assert not function.insert(["2", "32", constants.CATEGORY_INTERNET])
    assert len(expenses) == 4
    assert not function.remove_day(["4"])
    assert len(expenses) == 2
    assert not function.undo_last_action()
    assert len(expenses) == 4
    assert not function.remove_category([constants.CATEGORY_HOUSEKEEPING])
    assert len(expenses) == 2
    assert not function.undo_last_action()
    assert len(expenses) == 4
    assert not function.filter_category_condition([constants.CATEGORY_HOUSEKEEPING, '<', "10"])
    assert len(expenses) == 1
    assert not function.undo_last_action()
    assert len(expenses) == 4
    assert not function.undo_last_action()
    assert len(expenses) == 3
    assert not function.undo_last_action()
    assert len(expenses) == 2
    assert not function.undo_last_action()
    assert len(expenses) == 1
    assert not function.undo_last_action()
    assert len(expenses) == 0
    assert function.undo_last_action()
    