import socket
from threading import Thread, Lock
import sys


lock = Lock()


def update_connections(connections):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((socket.gethostname(), 7000))

    while True:
        connections_as_string = client_socket.recv(1024).decode("utf-8")
        lock.acquire()
        connections.clear()
        for connection in connections_as_string.split("__;__"):
            details = connection.split("_;_")
            connections.append((details[0], int(details[1])))
        lock.release()


def listen_messages(client_socket):
    while True:
        message = client_socket.recvfrom(1024).decode("utf-8")
        print(message)


def run_client():

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    client_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    client_socket.bind((socket.gethostname(), 1234))

    connections = []

    Thread(target=update_connections, args=(connections,)).start()
    Thread(target=listen_messages, args=(client_socket,)).start()

    while True:
        message = input("Your message here > ")
        if message == "QUIT":
            client_socket.close()
            sys.exit(0)
        for connection in connections:
            client_socket.sendto(message.encode("utf-8"), connection)


if __name__ == "__main__":
    run_client()
