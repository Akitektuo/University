import socket
import time

UDP_IP = "localhost"
UDP_PORT = 1234

if __name__ == "__main__":
    message = input("Type your name here > ")

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    start_request_time = time.time()
    client_socket.sendto(message.encode("utf-8"), (UDP_IP, UDP_PORT))

    data, address = client_socket.recvfrom(1024)
    request_time = time.time() - start_request_time
    print("Received from server: '%s'\nRequest took: %s" % (data.decode("UTF-8"), str(request_time)))
