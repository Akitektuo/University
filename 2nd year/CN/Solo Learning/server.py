import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((socket.gethostname(), 1234))
server_socket.listen(5)

while True:
    client_socket, address = server_socket.accept()
    print(f"Connection from {address} has been established!")
    client_socket.send("Welcome to the server!".encode("utf-8"))
    client_socket.close()
