from controller import Controller


def print_menu():
    print("If you want to see the menu at any time, enter 0")
    print("1. Check graphs loaded into memory")
    print("2. Import graphs")
    print("3. Switch graph")
    print("4. Get the number of vertices")
    print("5. Iterate the set of vertices")
    print("6. Check if two vertices form an edge")
    print("7. Get the in and out degree of a vertex")
    print("8. Iterate the set of outgoing edges of a vertex")
    print("9. Iterate the set of ingoing edges of a vertex")
    print("10. Get and modify the cost of an edge")
    print("11. Edit graph")
    print("12. Make a copy of the graph")
    print("13. Generate a new graph")
    print("14. Save selected graph ")
    print("15. Get length of the shortest path")
    print("16. Exit")


def fill(char, times):
    str_builder = ""
    for i in range(times):
        str_builder += char
    return str_builder


def print_progress(progress):
    if progress % 10 == 0:
        print("Processing " + str(progress) + "%...")


class Menu:

    def __init__(self):
        self.controller = Controller()

    def start(self):
        print("Welcome! The memory is currently empty, import a graph to begin")
        print_menu()
        while True:
            try:
                command = input("\n> ").strip()

                if command == "0":
                    print_menu()
                    continue

                if command == "1":
                    print(self.controller.detail_loaded_graphs())
                    continue

                if command == "2":
                    file_name = input("Please provide a txt file name (without extension): ")
                    print("Importing, big files will take a few seconds to be processed...\n")

                    self.controller.import_graph(file_name, lambda progress: print_progress(progress))
                    self.controller.select_last_graph()

                    print("Processing 100%...\n\nGraph imported successfully and selected")
                    continue

                if command == "3":
                    graph_number = input("Please give a graph index loaded in memory: ")
                    if not graph_number.isdecimal():
                        raise Exception("Input must be of type integer")

                    self.controller.select_graph(int(graph_number))

                    print("Graph " + graph_number + " selected")
                    continue

                if command == "4":
                    print("The selected graph has " + str(self.controller.get_vertices_count()) + " vertices")
                    continue

                if command == "5":
                    confirmation = input(
                        "Show all " + str(self.controller.get_vertices_count()) + " vertices? (Y/N): ").lower()

                    if confirmation.startswith("y"):
                        print(self.controller.get_vertices())

                    continue

                if command == "6":
                    source_vertex = input("Give starting vertex: ")
                    if not source_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    target_vertex = input("Give ending vertex: ")
                    if not target_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    if self.controller.is_edge(int(source_vertex), int(target_vertex)):
                        print("The edge (" + source_vertex + ", " + target_vertex + ") exists")
                    else:
                        print("The edge (" + source_vertex + ", " + target_vertex + ") does not exist")

                    continue

                if command == "7":
                    vertex = input("Give vertex: ")

                    if not vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    (degree_in, degree_out) = self.controller.get_vertex_in_and_out(int(vertex))
                    print("The vertex " + vertex + " has the in degree " + str(degree_in) +
                          " and the out degree " + str(degree_out))

                    continue

                if command == "8":
                    vertex = input("Give vertex: ")

                    if not vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    print("The vertex " + vertex + " has outbound edges with " +
                          self.controller.get_vertex_out(int(vertex)))
                    continue

                if command == "9":
                    vertex = input("Give vertex: ")

                    if not vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    print("The vertex " + vertex + " has inbound edges with " +
                          self.controller.get_vertex_in(int(vertex)))
                    continue

                if command == "10":
                    source_vertex = input("Give starting vertex: ")
                    if not source_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    target_vertex = input("Give ending vertex: ")
                    if not target_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    confirmation = input("The edge (" + source_vertex + ", " + target_vertex + ") has the cost of " +
                                         str(self.controller.get_edge_cost(int(source_vertex), int(target_vertex))) +
                                         ". Would you like to change it? (Y/N): ").lower()

                    if not confirmation.startswith("y"):
                        continue

                    new_cost = input("Give new cost of edge (" + source_vertex + ", " + target_vertex + "): ")

                    if not new_cost.isdecimal():
                        raise Exception("The cost must be of type integer")

                    self.controller.set_edge_cost(int(source_vertex), int(target_vertex), int(new_cost))
                    print("The new cost was successfully set to " + new_cost)
                    continue

                if command == "11":
                    edit_type = input("Do you want to add or remove a vertex/edge? (A/R): ").lower()
                    edit_type = ("add", "remove")[edit_type.startswith("r")]
                    what_to_edit = input("What do you want to " + edit_type + "? (V/E): ").lower()
                    if what_to_edit.startswith("v"):
                        if edit_type == "add":
                            self.controller.add_vertex()
                            print("Vertex added successfully")
                        else:
                            vertex = input("Give vertex to remove: ")
                            if not vertex.isdecimal():
                                raise Exception("The vertex must be of type integer")

                            self.controller.remove_vertex(int(vertex))
                            print("Vertex removed successfully")
                    else:
                        if edit_type == "add":
                            source_vertex = input("Give starting vertex of the new edge: ")
                            if not source_vertex.isdecimal():
                                raise Exception("The vertex must be of type integer")

                            target_vertex = input("Give ending vertex of the new edge: ")
                            if not target_vertex.isdecimal():
                                raise Exception("The vertex must be of type integer")

                            cost = input("Give the cost of the new edge: ")
                            if not cost.isdecimal():
                                raise Exception("The cost must be of type integer")

                            self.controller.add_edge(int(source_vertex), int(target_vertex), int(cost))
                            print("Edge added successfully")
                        else:
                            source_vertex = input("Give starting vertex of the edge: ")
                            if not source_vertex.isdecimal():
                                raise Exception("The vertex must be of type integer")

                            target_vertex = input("Give ending vertex of the edge: ")
                            if not target_vertex.isdecimal():
                                raise Exception("The vertex must be of type integer")

                            self.controller.remove_edge(int(source_vertex), int(target_vertex))
                            print("Edge removed successfully")
                    continue

                if command == "12":
                    self.controller.copy_selected()
                    print("Copy made")
                    continue

                if command == "13":
                    vertices = input("Give number of vertices: ")
                    if not vertices.isdecimal():
                        raise Exception("The number of vertices must be of type integer")

                    edges = input("Give number of edges: ")
                    if not edges.isdecimal():
                        raise Exception("The number of edges must be of type integer")

                    self.controller.generate(int(vertices), int(edges))
                    print("Graph generated successfully and selected")
                    continue

                if command == "14":
                    file_name = input("Please provide a txt file name (without extension): ")

                    self.controller.save_graph(file_name)

                    print("Selected graph saved successfully")
                    continue

                if command == "15":
                    source_vertex = input("Give source vertex: ")
                    if not source_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    target_vertex = input("Give target vertex: ")
                    if not target_vertex.isdecimal():
                        raise Exception("The vertex must be of type integer")

                    shortest_path = self.controller.compute_length_of_shortest_path(int(source_vertex),
                                                                                    int(target_vertex))

                    print(
                        "The shortest path from " + source_vertex + " to " + target_vertex + " is " + str(
                            shortest_path.vertices) + " having the length of " + str(shortest_path.value))

                    continue

                if command == "16":
                    return

                raise Exception("Invalid command")
            except Exception as error:
                print("Error: " + str(error))
