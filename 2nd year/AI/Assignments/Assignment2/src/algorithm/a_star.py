from heapq import heappush
from typing import Optional

from numpy import ndarray

from src.algorithm.domain.node import Node
from src.algorithm.greedy import Greedy
from src.util.extensions import compute_manhattan_distance


class AStar(Greedy):
    def __is_child_in_open_list(self, child: Node) -> bool:
        return any(child.position == open_node.position and
                   child.distance_to_start > open_node.distance_to_start
                   for open_node in self.__open_list)

    def __check_children(self, children: list[Node]):
        for child in filter(lambda c: c not in self.__closed_list, children):
            child.distance_to_start = self.__current_node.distance_to_start + 1
            child.distance_to_goal = compute_manhattan_distance(child.position, self.__end_node.position)
            child.update_total_cost()

            if self.__is_child_in_open_list(child):
                continue

            heappush(self.__open_list, child)


a_star = None


def execute_a_star(maze: ndarray, start: tuple[int, int], end: tuple[int, int]) -> Optional[tuple[int, int]]:
    global a_star
    if not a_star:
        a_star = AStar(maze, start, end)
    return a_star.execute()
