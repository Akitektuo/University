import socket

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((socket.gethostname(), 1234))

while True:
    message = client_socket.recv(1024)
    print(message.decode("utf-8"))
