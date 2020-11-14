from networking.advanced_socket import AdvancedSocket

if __name__ == '__main__':
    socket = AdvancedSocket(False)
    socket.connect()
    while message := socket.receive():
        print(message)
