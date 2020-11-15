from domain import *

def printMenu():
    print("1. Add circle")
    print("3. Show circles")
    print("0. Exit")

def add_circle_ui():
    pass

def show_circles_ui(circles):
    for c in circles:
        print(to_str(c))
    pass

def start():
    circles = test_init()
    commands = {
        '1': add_circle_ui,
        '3': show_circles_ui 
    }
    while True:
        printMenu()
        cmd = input("command: ")
        if cmd == '0':
            return
        if cmd in commands:
            commands[cmd](circles)
        else:
            print("Bad command")

start()