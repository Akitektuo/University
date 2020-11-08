import time

from networking.advanced_socket import AdvancedSocket

if __name__ == '__main__':
    socket = AdvancedSocket(False)
    socket.host()
    while True:
        client_socket = socket.accept()
        client_address = client_socket.get_address()
        print(f"Connection from {client_address} has been established!")
        client_socket.send("Welcome to the server!")

        while True:
            time.sleep(3)
            try:
                client_socket.send(f"The time is {time.time()}!")
            except ConnectionResetError:
                print(f"Connection to {client_address} has been lost!")
                break
