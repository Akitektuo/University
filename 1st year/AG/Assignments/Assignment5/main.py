def print_solution(path):
    for vertex in path:
        print(vertex, end=" ")
    print(path[0], "\n")


class Graph:
    def __init__(self, vertices):
        self.graph = [[0 for column in range(vertices)] for row in range(vertices)]
        self.vertices = vertices

    def is_safe(self, vertex, position, path):
        if self.graph[path[position - 1]][vertex] == 0:
            return False

        if vertex in path:
            return False

        return True

    def hamiltonian_cycle_helper(self, path, position):
        if position == self.vertices:
            return self.graph[path[position - 1]][path[0]] == 1

        for vertex in range(1, self.vertices):
            if self.is_safe(vertex, position, path):
                path[position] = vertex

                if self.hamiltonian_cycle_helper(path, position + 1):
                    return True

                path[position] = -1

        return False

    def hamiltonian_cycle(self):
        path = [-1] * self.vertices
        path[0] = 0

        if not self.hamiltonian_cycle_helper(path, 1):
            return False

        print_solution(path)
        return True


if __name__ == "__main__":
    graph1 = Graph(5)
    graph1.graph = [
        [0, 1, 0, 1, 0],
        [1, 0, 1, 1, 1],
        [0, 1, 0, 0, 1],
        [1, 1, 0, 0, 1],
        [0, 1, 1, 1, 0]
    ]
    print(graph1.hamiltonian_cycle())

    graph2 = Graph(5)
    graph2.graph = [
        [0, 1, 0, 1, 0],
        [1, 0, 1, 1, 1],
        [0, 1, 0, 0, 1],
        [1, 1, 0, 0, 0],
        [0, 1, 1, 0, 0]
    ]
    print(graph2.hamiltonian_cycle())
