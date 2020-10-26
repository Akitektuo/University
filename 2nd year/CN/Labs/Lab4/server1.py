import socket, threading

UDP_IP = "localhost"
UDP_PORT = 1234


def handle_request(client_address, client_data):
    response_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    print("Incoming message from " + str(client_address))
    response_socket.sendto(("Hello, %s!" % client_data.decode("UTF-8")).encode("UTF-8"), client_address)

    response_socket.close()


if __name__ == "__main__":
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_socket.bind((UDP_IP, UDP_PORT))

    while True:
        data, address = server_socket.recvfrom(1024)

        threading.Thread(target=handle_request, args=(address, data)).start()
