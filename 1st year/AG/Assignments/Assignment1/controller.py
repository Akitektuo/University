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

    def import_graph(self, file_name):
        file_graph = open(file_name + ".txt", "r")
        lines = file_graph.readlines()
        first_line = lines.pop(0).strip()

        graph_params = first_line.split()
        imported_graph = DirectedGraph(int(graph_params[0]))

        for line in lines:
            data = line.strip().split(" ")
            imported_graph.add_edge(int(data[0]), int(data[1]), int(data[2]))

        self.graphs.append(imported_graph)

        file_graph.close()

    def select_last_graph(self):
        if not len(self.graphs):
            raise Exception("No Graphs in memory")
        self.selected_graph = self.graphs[-1]
