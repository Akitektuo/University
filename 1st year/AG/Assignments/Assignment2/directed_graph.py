import copy


class Vertex:

    def __init__(self, value):
        """
        Constructs a vertex with value

        :param value: int
        """
        self.value = value


class DirectedGraph:

    def __init__(self, size=0):
        """
        Constructs a graph with size vertices, the default size is 0

        :param size: int
        """
        self.store_from = {}
        self.store_to = {}
        self.store_cost = {}
        self.existing_vertices = []

        for v in range(size):
            new_vertex = Vertex(v)
            self.existing_vertices.append(new_vertex)
            self.store_from[new_vertex] = []
            self.store_to[new_vertex] = []

    def get_vertices(self):
        """
        Returns the number of vertices of the graph

        :return: int
        """
        return len(self.store_from)

    def get_edges(self):
        """
        Returns the number of edges of the graph

        :return: int
        """
        return len(self.store_cost)

    def get_vertex_with_value(self, value):
        """
        Returns the vertex with the given value

        :param value: int
        :return: Vertex
        """
        return self.existing_vertices[value]

    def add_edge(self, from_vertex_value, to_vertex_value, cost):
        """
        Adds an edge to the graph

        :param from_vertex_value: int
        :param to_vertex_value: int
        :param cost: int
        """
        from_vertex = self.get_vertex_with_value(from_vertex_value)
        to_vertex = self.get_vertex_with_value(to_vertex_value)
        self.store_from[from_vertex].append(to_vertex)
        self.store_to[to_vertex].append(from_vertex)
        self.store_cost[(from_vertex, to_vertex)] = cost

    def parse_vertices(self):
        """
        Returns an iterable list of vertices

        :return: int
        """
        return map(lambda v: v.value, self.store_from.keys())

    def is_edge_defined(self, having_start, having_end):
        """
        Returns true if an edge is defined by the start and end vertices and false otherwise

        :param having_start: int
        :param having_end: int
        :return: boolean
        """
        having_start_vertex = self.get_vertex_with_value(having_start)
        having_end_vertex = self.get_vertex_with_value(having_end)

        return (having_start_vertex, having_end_vertex) in self.store_cost.keys()

    def get_vertex_in_out(self, vertex_value):
        """
        Returns a pair of total in degree and out degree vertices for a given vertex

        :param vertex_value: int
        :return: (int, int)
        """
        vertex = self.get_vertex_with_value(vertex_value)

        in_degree = 0
        for _ in self.store_to[vertex]:
            in_degree += 1

        out_degree = 0
        for _ in self.store_from[vertex]:
            out_degree += 1

        return in_degree, out_degree

    def parse_vertex_in(self, vertex_value):
        """
        Returns an iterable list of in degree vertices for a given vertex

        :param vertex_value: int
        :return: list
        """
        vertex = self.get_vertex_with_value(vertex_value)

        return list(map(lambda v: v.value, self.store_to[vertex]))

    def parse_vertex_out(self, vertex_value):
        """
        Returns an iterable list of out degree vertices for a given vertex

        :param vertex_value: int
        :return: list
        """
        vertex = self.get_vertex_with_value(vertex_value)

        return list(map(lambda v: v.value, self.store_from[vertex]))

    def get_edge_cost(self, having_start, having_end):
        """
        Returns the cost of a defined edge for a given start and end vertex

        :param having_start: int
        :param having_end: int
        :return: int
        """
        having_start_vertex = self.get_vertex_with_value(having_start)
        having_end_vertex = self.get_vertex_with_value(having_end)

        try:
            return self.store_cost[(having_start_vertex, having_end_vertex)]
        except KeyError:
            raise Exception("The specified edge has no cost")

    def set_edge_cost(self, having_start, having_end, cost):
        """
        Sets a given cost to a given edge defined by a start and end vertex

        :param having_start: int
        :param having_end: int
        :param cost: int
        """
        having_start_vertex = self.get_vertex_with_value(having_start)
        having_end_vertex = self.get_vertex_with_value(having_end)

        self.store_cost[(having_start_vertex, having_end_vertex)] = cost

    def add_vertex(self):
        """
        Adds a new vertex
        """
        value = len(self.store_from)
        new_vertex = Vertex(value)

        self.store_from[new_vertex] = []
        self.store_to[new_vertex] = []

    def remove_edge(self, having_start, having_end):
        """
        Removes an edge defined by the given start and end of a vertex

        :param having_start: int
        :param having_end: int
        """
        having_start_vertex = self.get_vertex_with_value(having_start)
        having_end_vertex = self.get_vertex_with_value(having_end)

        self.store_cost.pop((having_start_vertex, having_end_vertex))
        self.store_from[having_start_vertex].remove(having_end_vertex)
        self.store_to[having_end_vertex].remove(having_start_vertex)

    def remove_vertex(self, vertex_value):
        """
        Removes the given vertex

        :param vertex_value: int
        """

        vertex = self.get_vertex_with_value(vertex_value)
        if not vertex:
            raise Exception("Vertex " + str(vertex_value) + " not found")

        for v in self.parse_vertex_out(vertex_value):
            self.remove_edge(vertex_value, v)

        for v in self.parse_vertex_in(vertex_value):
            self.remove_edge(v, vertex_value)

        del self.store_to[vertex]
        del self.store_from[vertex]

        for i in range(vertex_value, len(self.existing_vertices)):
            self.existing_vertices[i] -= 1

        self.existing_vertices.pop()

    def get_copy(self):
        """
        Returns a copy of the DirectedGraph as a DirectedGraph

        :return: DirectedGraph
        """
        return copy.deepcopy(self)

    # noinspection PyTypeChecker
    def breadth_first_search(self, source, target):
        """
        Returns the shortest length betweehn the given source and target

        :param source: int
        :param target: int
        :return: int
        """
        source, target = target, source

        visited = [False] * len(self.existing_vertices)
        distance = [None] * len(self.existing_vertices)

        queue = [source]
        visited[source] = True
        distance[source] = 0

        while len(queue) > 0:
            root = queue.pop(0)
            for v in self.parse_vertex_in(root):
                if not visited[v]:
                    visited[v] = True
                    distance[v] = distance[root] + 1
                    queue.append(v)

        return distance[target]

