from threading import Thread
import socket
import sys
import time
import random


def get_address_from_leader(student_socket):
    message = None
    address = None

    while message != "leader":
        message, address = student_socket.recvfrom(6)
        message = message.decode("utf-8")

    return address


def send_question(student_socket, address):
    while True:
        if random.randint(0, 1) == 0:
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum posuere.".encode("utf-8")
            student_socket.sendto(message, address)
        time.sleep(3)


def read_question(leader_socket):
    message, _ = leader_socket.recvfrom(1024)
    return message.decode("utf-8")


def read_answer(teacher_socket):
    message = teacher_socket.recv(1024)
    return message.decode("utf-8")


def send_broadcast(leader_socket, server_port, message="leader"):
    leader_socket.sendto(message.encode("utf-8"), ("<broadcast>", server_port))


def send_broadcasts(leader_socket, server_port):
    while True:
        send_broadcast(leader_socket, server_port)
        time.sleep(5)


def receive_broadcasts(student_socket):
    while True:
        message, _ = student_socket.recvfrom(1024)
        message = message.decode("utf-8")
        if message != "leader":
            print(message)
            print()


def handle_leader(server_port):
    leader_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    leader_socket.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

    Thread(target=send_broadcasts, args=(leader_socket, server_port)).start()

    teacher_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    teacher_socket.connect(("localhost", 1234))

    while True:
        question = read_question(leader_socket)
        teacher_socket.send(question.encode("utf-8"))
        answer = read_answer(teacher_socket)
        send_broadcast(leader_socket, server_port, f"Q: {question}\nA: {answer}")


def handle_student(server_port):
    student_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    student_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    student_socket.bind(("", server_port))

    address = get_address_from_leader(student_socket)

    Thread(target=receive_broadcasts, args=(student_socket,)).start()

    send_question(student_socket, address)


def run_client():
    port = int(sys.argv[1])
    is_leader = sys.argv[2] == "1"

    if is_leader:
        handle_leader(port)
    else:
        handle_student(port)


if __name__ == "__main__":
    run_client()
