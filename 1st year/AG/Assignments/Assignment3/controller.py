import random

from directed_graph import DirectedGraph


class Controller:

    def __init__(self):
        self.graphs = []
        self.selected_graph = None

    def detail_loaded_graphs(self):
        length = len(self.graphs)
        str_res = "Currently, the program has loaded " + ("no graphs", "one graph", str(length) + " graphs")[
            min(length, 2)]

        for index, graph in enumerate(self.graphs):
            str_res += "\n"
            if graph == self.selected_graph:
                str_res += "Currently selected: "
            str_res += "Graph " + str(index) + ", has " + str(graph.get_vertices()) + " vertices and " + str(
                graph.get_edges()) + " edges"

        return str_res

    def import_graph(self, file_name, process=lambda progress: None):
        try:
            file_graph = open(file_name + ".txt", "r")
            lines = file_graph.readlines()
            first_line = lines.pop(0).strip()

            graph_params = first_line.split()
            imported_graph = DirectedGraph(int(graph_params[0]))
            total_lines = len(lines)
            last_percent = 0

            for index, line in enumerate(lines):
                new_percent = index * 100 // total_lines
                if last_percent != new_percent:
                    process(new_percent)
                    last_percent = new_percent

                data = line.strip().split(" ")
                imported_graph.add_edge(int(data[0]), int(data[1]), int(data[2]))

            self.graphs.append(imported_graph)

            file_graph.close()
        except FileNotFoundError:
            raise Exception("File '" + file_name + "' not found")

    def select_last_graph(self):
        if not len(self.graphs):
            raise Exception("No Graphs in memory")
        self.selected_graph = self.graphs[-1]

    def select_graph(self, index):
        if index not in range(0, len(self.graphs)):
            raise Exception("Index not existent in memory")

        self.selected_graph = self.graphs[index]

    def get_vertices_count(self):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        return self.selected_graph.get_vertices()

    def get_vertices(self):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        str_builder = ""
        for v in self.selected_graph.parse_vertices():
            str_builder += "\n" + str(v)

        return str_builder

    def is_edge(self, from_vertex, to_vertex):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        return self.selected_graph.is_edge_defined(from_vertex, to_vertex)

    def get_vertex_in_and_out(self, vertex):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        return self.selected_graph.get_vertex_in_out(vertex)

    def get_vertex_in(self, vertex):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        vertices_in = self.selected_graph.parse_vertex_in(vertex)

        if len(vertices_in) < 1:
            return "()"

        str_builder = "("
        for v in vertices_in:
            str_builder += str(v) + ", "

        return str_builder[:-2] + ")"

    def get_vertex_out(self, vertex):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        vertices_out = self.selected_graph.parse_vertex_out(vertex)

        if len(vertices_out) < 1:
            return "()"

        str_builder = "("
        for v in vertices_out:
            str_builder += str(v) + ", "

        return str_builder[:-2] + ")"

    def get_edge_cost(self, from_vertex, to_vertex):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        return self.selected_graph.get_edge_cost(from_vertex, to_vertex)

    def set_edge_cost(self, from_vertex, to_vertex, cost):
        if self.selected_graph is None:
            raise Exception("No selected graph")

        self.selected_graph.set_edge_cost(from_vertex, to_vertex, cost)

    def add_vertex(self):
        self.selected_graph.add_vertex()

    def remove_vertex(self, vertex):
        self.selected_graph.remove_vertex(vertex)

    def add_edge(self, from_vertex, to_vertex, cost):
        self.selected_graph.add_edge(from_vertex, to_vertex, cost)

    def remove_edge(self, from_vertex, to_vertex):
        self.selected_graph.remove_edge(from_vertex, to_vertex)

    def copy_selected(self):
        self.graphs.append(self.selected_graph.get_copy())

    def generate(self, vertices, edges):
        generated_graph = DirectedGraph(vertices)

        while generated_graph.get_edges() < edges:
            from_vertex = random.randrange(0, vertices)
            to_vertex = random.randrange(0, vertices)
            if generated_graph.is_edge_defined(from_vertex, to_vertex):
                continue
            cost = random.randint(-100, 100)
            generated_graph.add_edge(from_vertex, to_vertex, cost)

        self.graphs.append(generated_graph)
        self.select_last_graph()

    def save_graph(self, file_name):
        if self.selected_graph is None:
            raise Exception("No selected graph")
        
        file_graph = open(file_name + ".txt", "w")
        file_graph.write(str(self.selected_graph.get_vertices()) + " " + str(self.selected_graph.get_edges()) + "\n")
        for vertices, cost in self.selected_graph.store_cost.items():
            file_graph.write(str(vertices[0].value) + " " + str(vertices[1].value) + " " + str(cost) + "\n")
        file_graph.close()

    def compute_length_of_shortest_path(self, source, target):
        return self.selected_graph.breadth_first_search(source, target)

    def find_lowest_path(self, source, target):
        return self.selected_graph.find_lowest_cost_path(source, target)
