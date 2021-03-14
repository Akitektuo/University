def add_tuples(tuple_a: tuple[int, int], tuple_b: tuple[int, int]) -> tuple[int, int]:
    return tuple_a[0] + tuple_b[0], tuple_a[1] + tuple_b[1]


def compute_manhattan_distance(start: tuple[int, int], end: tuple[int, int]) -> int:
    return (start[0] - end[0]) ** 2 + (start[1] - end[1]) ** 2
