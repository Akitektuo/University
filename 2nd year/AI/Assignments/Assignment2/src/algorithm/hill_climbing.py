from typing import Optional

from numpy import ndarray

from src.algorithm.domain.node import Node
from src.util.constants import Direction, Status
from src.util.extensions import compute_manhattan_distance, add_tuples


class HillClimbing:
    def __init__(self, maze: ndarray, start: tuple[int, int], end: tuple[int, int]):
        self.__maze = maze
        self.__start_node = Node(position=start)
        self.__end_node = Node(position=end)
        self.__visited = set()
        self.__define_iterations()
        self.__current_node = None

    def __define_iterations(self):
        self.height = len(self.__maze)
        self.width = len(self.__maze[0])

        self.__iterations = 0
        self.__maximum_iterations = self.height * self.width // 2

    def __set_current_node(self, node: Node):
        self.__current_node = node
        self.__visited.add(node.position)
        self.__current_node.total_cost = compute_manhattan_distance(self.__end_node.position, node.position)

    def __increment_and_check_exceeding_iterations(self) -> bool:
        self.__iterations += 1

        return self.__iterations > self.__maximum_iterations

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

    def __get_best_neighbour(self) -> Node:
        best_node = Node()
        best_node.total_cost = self.width * self.height

        for new_position in Direction.DIRECTIONS:
            node_position = add_tuples(self.__current_node.position, new_position)

            if not (self.__is_position_in_range(node_position) and self.__are_coordinates_valid(node_position)):
                continue

            if node_position in self.__visited:
                continue

            cost = compute_manhattan_distance(self.__end_node.position, node_position)
            if cost < best_node.total_cost:
                best_node = Node(position=node_position)
                best_node.total_cost = cost

        return best_node

    def execute(self) -> Optional[tuple[int, int]]:
        if self.__did_find_goal() or self.__increment_and_check_exceeding_iterations():
            return None

        if not self.__current_node:
            self.__set_current_node(self.__start_node)
            return self.__current_node.position

        best_node = self.__get_best_neighbour()
        if best_node.position:
            self.__set_current_node(best_node)
            return self.__current_node.position

        return None


hill_climbing = None


def execute_hill_climbing(maze: ndarray, start: tuple[int, int], end: tuple[int, int]) -> Optional[tuple[int, int]]:
    global hill_climbing
    if not hill_climbing:
        hill_climbing = HillClimbing(maze, start, end)
    return hill_climbing.execute()
