import pickle
import socket

from networking.constants import HEADER_SIZE, ENCODING


class AdvancedSocket:

    def __init__(self, using_udp, ip=socket.gethostname(), port=1234, initial_socket=None):
        self.using_udp = using_udp

        if initial_socket:
            self.socket = initial_socket
        else:
            connection = (socket.SOCK_STREAM, socket.SOCK_DGRAM)[using_udp]
            self.socket = socket.socket(socket.AF_INET, connection)
            self.socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)

        self.ip = ip
        self.port = port

    def host(self):
        self.socket.bind((self.ip, self.port))
        self.socket.listen(5)

    def connect(self):
        self.socket.connect((self.ip, self.port))

    def accept(self):
        client_socket, address = self.socket.accept()
        return AdvancedSocket(self.using_udp, ip=address[0], port=address[1], initial_socket=client_socket)

    def close(self):
        self.socket.close()

    def send(self, data):
        encoded_data = pickle.dumps(data)
        header = f"{len(encoded_data):<{HEADER_SIZE}}".encode(ENCODING)
        if self.using_udp:
            self.socket.sendto(header + encoded_data, self.get_address())
        else:
            self.socket.send(header + encoded_data)

    def send_to(self, data, address):
        encoded_data = pickle.dumps(data)
        header = f"{len(encoded_data):<{HEADER_SIZE}}".encode(ENCODING)
        self.socket.sendto(header + encoded_data, address)

    def receive(self):
        size = int(self.socket.recv(HEADER_SIZE))
        if size == 0:
            return None
        return pickle.loads(self.socket.recv(size))

    def receive_from(self):
        size = int(self.socket.recvfrom(HEADER_SIZE)[1])
        if size == 0:
            return None
        data, address = self.socket.recvfrom(size)
        return pickle.loads(data), address

    def get_address(self):
        return self.ip, self.port
