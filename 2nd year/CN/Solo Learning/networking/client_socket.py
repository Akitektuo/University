from networking.advanced_socket import AdvancedSocket
from networking.constants import CONNECTION_TCP


class ClientSocket:

    def __init__(self):
        self.socket = AdvancedSocket(CONNECTION_TCP)
        self.socket.connect()
        self.on_receive_listeners = []

    def add_on_receive_listener(self, callback):
        self.on_receive_listeners.append(callback)

    def receive(self):
        return self.socket.receive()

    def listen(self):
        while True:
            message = self.socket.receive()

            for listener in self.on_receive_listeners:
                listener(message)
