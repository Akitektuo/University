class DirectedGraph:

    def __init__(self, size=0):
        self.store_in = {}
        self.store_out = {}
        self.store_cost = {}

        for v in range(size):
            self.store_in[v] = []
            self.store_out[v] = []

    def get_vertices(self):
        return max(len(self.store_in), len(self.store_out))

    def get_edges(self):
        return len(self.store_cost)

    def add_edge(self, from_vertex, to_vertex, cost):
        self.store_in[from_vertex].append(to_vertex)
        self.store_out[to_vertex].append(from_vertex)
        self.store_cost[(from_vertex, to_vertex)] = cost
