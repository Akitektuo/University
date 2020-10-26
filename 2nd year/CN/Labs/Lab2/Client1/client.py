import socket, struct

if __name__ == "__main__":
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    text = input("> ")

    try:
        clientSocket.connect(("localhost", 1234))
    except socket.error as message:
        print("error: ", message.strerror)
        exit(-1)

    for character in text:
        clientSocket.send(struct.pack("c", character.encode("ascii")))
    #result = clientSocket.recv(2)

    #result = struct.unpack("!H", result)
    #print("a + b = " + str(result[0]))
    clientSocket.close()
