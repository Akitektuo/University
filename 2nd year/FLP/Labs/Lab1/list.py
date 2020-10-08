class Node:
    def __init__(self, element):
        self.element = element
        self.next = None


class List:
    def __init__(self):
        self.first = None


'''
crearea unei liste din valori citite pana la 0
'''


def create_list():
    list = List()
    list.first = create_list_recursively()
    return list


def create_list_recursively():
    number = int(input("x = "))
    if number == 0:
        return
    else:
        node = Node(number)
        node.next = create_list_recursively()
        return node


'''
tiparirea elementelor unei liste
'''


def print_list(list):
    print_recursively(list.first)


def print_recursively(node):
    if node:
        print(node.element, end=" ")
        print_recursively(node.next)


'''
program pentru test
'''


def is_list_set(list):
    return is_list_set_recursively(list.first)


def is_list_set_recursively(node):
    """
    is_list_set_recursively(l1...ln) = {
        True, n = 0
        False, is_element_in_list(l1, l2...ln)
        is_list_set_recursively(l2...ln), otherwise
    }
    """
    if not node:
        return True
    if is_element_in_list(node.element, node.next):
        return False
    return is_list_set_recursively(node.next)


def is_element_in_list(element, node):
    """
    is_element_in_list(element, l1...ln) = {
        False, n = 0
        True, l1 = element
        is_element_in_list(element, l2...ln), otherwise
    }
    """
    if not node:
        return False
    if node.element == element:
        return True
    return is_element_in_list(element, node.next)


def compute_total_of_distinct_elements(list):
    return compute_total_of_distinct_elements_recursively(list.first)
    # return compute_total_of_distinct_elements_recursively_optimized(list.first)


def compute_total_of_distinct_elements_recursively(node):
    """
    compute_total_of_distinct_elements_recursively(l1...ln) = {
        0, n = 0
        compute_total_of_distinct_elements_recursively(l2...ln), is_element_in_list(l1, l2...ln)
        1 + compute_total_of_distinct_elements_recursively(l2...ln), otherwise
    }
    """
    if not node:
        return 0
    if is_element_in_list(node.element, node.next):
        return compute_total_of_distinct_elements_recursively(node.next)
    return 1 + compute_total_of_distinct_elements_recursively(node.next)


def compute_total_of_distinct_elements_recursively_optimized(node, count=0):
    """
    compute_total_of_distinct_elements_recursively_optimized(l1...ln, count) = {
        count, n = 0
        compute_total_of_distinct_elements_recursively_optimized(l2...ln, count), is_element_in_list(l1, l2...ln)
        compute_total_of_distinct_elements_recursively_optimized(l2...ln, count + 1), otherwise
    }
    """
    if not node:
        return count
    if is_element_in_list(node.element, node.next):
        return compute_total_of_distinct_elements_recursively_optimized(node.next, count)
    return compute_total_of_distinct_elements_recursively_optimized(node.next, count + 1)


def main():
    list = create_list()

    print_list(list)

    print()
    # print(is_list_set(list))
    print(compute_total_of_distinct_elements(list))


main()
