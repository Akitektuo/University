from networking.advanced_socket import AdvancedSocket
from networking.constants import CONNECTION_TCP


class ServerSocket:

    def __init__(self):
        self.socket = AdvancedSocket(CONNECTION_TCP)
        self.socket.host()
        self.on_accept_listeners = []

    def add_on_accept_listener(self, callback):
        self.on_accept_listeners.append(callback)

    def run(self):
        while True:
            client_socket = self.socket.accept()
            print("accept")

            for listener in self.on_accept_listeners:
                listener(client_socket)
