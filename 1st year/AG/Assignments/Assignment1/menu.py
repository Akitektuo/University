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
    print("12. Save a copy of the graph")
    print("13. Generate a new graph")
    print("14. Exit")


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
                    print("Importing, big files will take a few seconds to be processed...")

                    self.controller.import_graph(file_name)
                    self.controller.select_last_graph()

                    print("Graph imported successfully and selected")
                    continue

                if command == "3":
                    continue

                if command == "4":
                    continue

                if command == "5":
                    continue

                if command == "6":
                    continue

                if command == "7":
                    continue

                if command == "8":
                    continue

                if command == "9":
                    continue

                if command == "10":
                    continue

                if command == "11":
                    continue

                if command == "12":
                    continue

                if command == "13":
                    continue

                if command == "14":
                    return

                raise Exception("Invalid command")
            except Exception as error:
                print("Error: " + str(error))
