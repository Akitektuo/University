import copy


class DirectedGraph:

    def __init__(self, size=0):
        self.store_from = {}
        self.store_to = {}
        self.store_cost = {}

        for v in range(size):
            self.store_from[v] = []
            self.store_to[v] = []

    def get_vertices(self):
        return len(self.store_from)

    def get_edges(self):
        return len(self.store_cost)

    def add_edge(self, from_vertex, to_vertex, cost):
        self.store_from[from_vertex].append(to_vertex)
        self.store_to[to_vertex].append(from_vertex)
        self.store_cost[(from_vertex, to_vertex)] = cost

    def parse_vertices(self):
        return list(self.store_from.keys())

    def is_edge_defined(self, having_start, having_end):
        return (having_start, having_end) in self.store_cost.keys()

    def get_vertex_in_out(self, vertex):
        in_degree = 0
        for _ in self.store_to[vertex]:
            in_degree += 1

        out_degree = 0
        for _ in self.store_from[vertex]:
            out_degree += 1

        return in_degree, out_degree

    def parse_vertex_in(self, vertex):
        return list(self.store_to[vertex])

    def parse_vertex_out(self, vertex):
        return list(self.store_from[vertex])

    def get_edge_cost(self, having_start, having_end):
        try:
            return self.store_cost[(having_start, having_end)]
        except KeyError:
            raise Exception("The specified edge has no cost")

    def set_edge_cost(self, having_start, having_end, cost):
        self.store_cost[(having_start, having_end)] = cost

    def add_vertex(self):
        index = len(self.store_from)
        self.store_from[index] = []
        self.store_to[index] = []

    def remove_edge(self, having_start, having_end):
        self.store_cost.pop((having_start, having_end))
        self.store_from[having_start].remove(having_end)
        self.store_to[having_end].remove(having_start)

    def remove_vertex(self, vertex):
        for v in self.parse_vertex_out(vertex):
            self.remove_edge(vertex, v)

        for v in self.parse_vertex_in(vertex):
            self.remove_edge(v, vertex)

    def get_copy(self):
        return copy.deepcopy(self)
