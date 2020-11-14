import socket
import select


def run_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((socket.gethostname(), 7000))
    server_socket.listen(10)

    connections = [server_socket]
    addresses = []

    while True:
        incoming_connections, _, _ = select.select(connections, [], [])
        for connection in incoming_connections:
            if connection == server_socket:
                client_socket, address = server_socket.accept()
                connections.append(client_socket)
                addresses.append(address)
                print(f"Client {address} has connected")
                continue
            try:
                connection.send("_;_".join(str(addresses)).encode("utf-8"))
            except ConnectionResetError:
                address = connection.getpeername()
                print(f"Client {address} has disconnected")
                addresses.remove(address)
                connections.remove(connection)

        for connection in connections:
            try:

                message = ""
                for address in addresses:
                    if address != connection.getpeername():
                        message += address[0] + "_;_" + str(address[1]) + "__;__"
                connection.send(message[:-5].encode("utf-8"))
            except:
                pass

        print(addresses)


if __name__ == "__main__":
    run_server()
