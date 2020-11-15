from domains import Address, Driver

class Repository:
    TYPE_ADDRESS = "address"
    TYPE_DRIVER = "driver"

    def __init__(self, from_file = "", rtype = ""):
        self.file = from_file
        self.type = rtype
        self.data = []

        self.read_file()

    def read_file(self):
        if not self.file or not self.type:
            return
        f = open(self.file, "r")
        line = f.readline().strip()
        while line:
            self.decode_line(line)
            line = f.readline().strip()
        f.close()

    def decode_line(self, line):
        params = line.split(",")
        if self.type == Repository.TYPE_ADDRESS:
            self.data.append(Address(int(params[0]), params[1], int(params[2]), int(params[3])))
            return
        if self.type == Repository.TYPE_DRIVER:
            self.data.append(Driver(params[0], int(params[1]), int(params[2])))
            return

    def is_empty(self):
        return len(self.data) < 1
        