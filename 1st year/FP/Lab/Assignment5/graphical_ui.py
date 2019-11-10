import tkinter as gui
from tkinter import messagebox
from action import Action
from validator import validate_existing_discipline_id

class GraphicalUI:
    TYPE = "graphical_ui"

    def __init__(self):
        self.type = GraphicalUI.TYPE
        self.action = Action(Action.HELP)
        print("\nGUI running...\n")

    def list_all(self):
        self.action = Action(Action.LIST)
        self.main.destroy()

    def failing_students(self):
        self.action = Action(Action.SEE_FAILING_STUDENTS)
        self.main.destroy()

    def best_students(self):
        self.action = Action(Action.SEE_BEST_STUDENTS)
        self.main.destroy()

    def grades(self):
        self.action = Action(Action.SEE_GRADES)
        self.main.destroy()

    def undo(self):
        self.action = Action(Action.UNDO)
        self.main.destroy()

    def redo(self):
        self.action = Action(Action.REDO)
        self.main.destroy()

    def ui(self):
        self.action = Action(Action.UI)
        self.main.destroy()

    def test(self):
        self.action = Action(Action.TEST)
        self.main.destroy()

    def exit(self):
        print("\nStopping GUI and app")
        self.action = Action(Action.EXIT)
        self.main.destroy()

    def on_destroy(self):
        if messagebox.askyesno("Quiting", "Do you wish to exit the app?"):
            self.exit()

    def show_window(self, elements):
        self.main = gui.Tk()
        self.main.title("Student Register")
        elements()
        self.main.protocol("WM_DELETE_WINDOW", self.on_destroy)
        self.main.mainloop()

    def build_button(self, text, action):
        gui.Button(self.main, text = text, width = 128, command = action).pack()

    def build_main_window(self):
        self.build_button("Add a student or discipline", lambda: print("Add"))
        self.build_button("Remove a student or discipline by ID", lambda: print("Remove"))
        self.build_button("Update a student or discipline", lambda: print("Update"))
        self.build_button("List all the students and disciplines", self.list_all)
        self.build_button("Grade students at a given discipline", lambda: print("Grade"))
        self.build_button("Seacrh for disciplines and student", lambda: print("Search"))
        self.build_button("Show all students failing at one or more disciplines", self.failing_students)
        self.build_button("Show the students with the best school situation", self.best_students)
        self.build_button("Show all disciplines with grades", self.grades)
        self.build_button("Undo the previous operation", self.undo)
        self.build_button("Redo the previous undo", self.redo)
        self.build_button("Change to menu UI", self.ui)
        self.build_button("Run all tests", self.test)
        self.build_button("Exit", self.exit)

    def show_main_window(self):
        self.show_window(self.build_main_window)

    def switch_ui_prefernece(self):
        if messagebox.askyesno("Closing GUI", "Do you wish to switch to the menu UI?"):
            print("\nStopping GUI")
            gui._default_root.destroy()
            return True
        gui._default_root.destroy()
        return False

    def get_action(self):
        self.show_main_window()
        return self.action
        # if command == "1":
        #     return Action(Action.ADD, self.get_add_params())
        # if command == "2":
        #     return Action(Action.REMOVE, self.get_remove_params())
        # if command == "3":
        #     return Action(Action.UPDATE, self.get_update_params())
        # if command == "5":
        #     return Action(Action.GRADE, self.get_grade_params())
        # if command == "6":
        #     return Action(Action.SEARCH, self.get_search_params())

    def handle_error(self, error):
        messagebox.showerror("Error", str(error))
        gui._default_root.destroy()

    def print_result(self, result):
        main = gui.Tk()
        main.title("Result")
        text = gui.Text(main, width = 128)
        if result == "" or result == None:
            text.insert(gui.END, "Operation successful")
        else:
            text.insert(gui.END, result)
        text.pack()
        gui.Button(main, text = "Ok", width = 32, command = main.destroy).pack()
        main.mainloop()
        