import socket
import select


def run_server():
    teacher_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    teacher_socket.bind(("localhost", 1234))
    teacher_socket.listen(10)

    connections = [teacher_socket]

    while True:
        incoming_connections, _, _ = select.select(connections, [], [])
        for connection in incoming_connections:
            if connection == teacher_socket:
                leader_socket, _ = teacher_socket.accept()
                connections.append(leader_socket)
                continue

            message = connection.recv(1024)
            question = message.decode("utf-8")

            print(f"Q: {question}")
            connection.send("Well this is quite obvious....".encode("utf-8"))


if __name__ == "__main__":
    run_server()
