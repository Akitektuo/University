class Program:

    def __init__(self):
        pass
    
    def print_menu(self):
        print("Welcome to Battleships!")
        print("1. Singleplayer")
        print("2. Mulltiplayer")
        print("3. Exit")

    def print_table(self, table):
        tableStr = "   | A | B | C | D |   \n"
        tableStr += "---+---+---+---+---+---\n"
        tableStr += " 1 | x |   |   |   | 1 \n"
        print(tableStr)

    def start_singleplayer(self):
        table = [
            ['x', '', '', ''],
            ['', 'x', '', ''],
            ['', '', 'x', ''],
            ['', '', '', 'x']
        ]
        self.print_table(table)

    def start(self):
        self.print_menu()
        while True:
            cmd = input("> ")
            if cmd == "1":
                self.start_singleplayer()
                return
            if cmd == "2":
                return
            if cmd == "3":
                return
            print("Invalid command")