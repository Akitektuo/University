from heapq import heapify, heappush, heappop
from typing import Optional

from numpy import ndarray

from src.algorithm.domain.node import Node
from src.util.constants import Status, Direction
from src.util.extensions import add_tuples, compute_manhattan_distance


class Greedy:
    def __init__(self, maze: ndarray, start: tuple[int, int], end: tuple[int, int]):
        self.__maze = maze
        self.__start_node = Node(position=start)
        self.__end_node = Node(position=end)
        self.__current_node: Optional[Node] = None

        self.__initialize_lists()
        self.__define_iterations()

    def __initialize_lists(self):
        self.__open_list = []
        self.__closed_list = []

        heapify(self.__open_list)
        heappush(self.__open_list, self.__start_node)

    def __define_iterations(self):
        self.height = len(self.__maze)
        self.width = len(self.__maze[0])

        self.__iterations = 0
        self.__maximum_iterations = self.height * self.width // 2

    def __increment_and_check_exceeding_iterations(self) -> bool:
        self.__iterations += 1

        return self.__iterations > self.__maximum_iterations

    def __set_current_node(self):
        self.__current_node = heappop(self.__open_list)
        self.__closed_list.append(self.__current_node)

    def __did_find_goal(self) -> bool:
        return self.__current_node and self.__current_node == self.__end_node

    def __is_x_in_range(self, x) -> bool:
        return 0 <= x < self.height

    def __is_y_in_range(self, y) -> bool:
        return 0 <= y < self.width

    def __is_position_in_range(self, position: tuple[int, int]) -> bool:
        return self.__is_x_in_range(position[0]) and self.__is_y_in_range(position[1])

    def __are_coordinates_valid(self, coordinates) -> bool:
        return self.__maze[coordinates[0]][coordinates[1]] == Status.EMPTY

    def __get_children_from_adjacent_squares(self) -> list[Node]:
        children = []

        for new_position in Direction.DIRECTIONS:
            node_position = add_tuples(self.__current_node.position, new_position)

            if not (self.__is_position_in_range(node_position) and self.__are_coordinates_valid(node_position)):
                continue

            children.append(Node(self.__current_node, node_position))

        return children

    def __check_children(self, children: list[Node]):
        for child in filter(lambda c: c not in self.__closed_list, children):
            child.total_cost = compute_manhattan_distance(child.position, self.__end_node.position)

            if child not in self.__open_list:
                heappush(self.__open_list, child)

    def __get_current_coordinates(self) -> tuple[int, int]:
        return self.__current_node.position

    def execute(self) -> Optional[tuple[int, int]]:
        if self.__did_find_goal() or not self.__open_list:
            return None

        if self.__increment_and_check_exceeding_iterations():
            return None

        self.__set_current_node()

        if self.__did_find_goal():
            return self.__get_current_coordinates()

        children = self.__get_children_from_adjacent_squares()

        self.__check_children(children)

        return self.__get_current_coordinates()


greedy = None


def execute_greedy(maze: ndarray, start: tuple[int, int], end: tuple[int, int]) -> Optional[tuple[int, int]]:
    global greedy
    if not greedy:
        greedy = Greedy(maze, start, end)
    return greedy.execute()
