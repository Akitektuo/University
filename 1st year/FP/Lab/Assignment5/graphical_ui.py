import tkinter as gui
from tkinter import messagebox
from action import Action
from validator import validate_existing_discipline_id

class GraphicalUI:
    TYPE = "graphical_ui"

    def __init__(self):
        self.main = None
        self.type = GraphicalUI.TYPE
        self.action = Action(Action.HELP)
        print("\nGUI running...\n")

    def add(self, atype, aid, name):
        self.action = Action(Action.ADD, [atype, aid, name])
        self.main.destroy()

    def remove(self, atype, aid):
        self.action = Action(Action.REMOVE, [atype, aid])
        self.main.destroy()

    def update(self, atype, aid, name):
        self.action = Action(Action.UPDATE, [atype, aid, name])
        self.main.destroy()

    def grade(self, sid, did, grade):
        self.action = Action(Action.GRADE, [sid, did, grade])
        self.main.destroy()

    def search(self, keyword):
        self.action = Action(Action.SEARCH, [keyword])
        self.main.destroy()

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

    def show_window(self, elements, destroy_previous = False):
        if destroy_previous:
            self.main.destroy()
        self.main = gui.Tk()
        self.main.title("Student Register")
        elements()
        self.main.protocol("WM_DELETE_WINDOW", self.on_destroy)
        self.main.mainloop()

    def build_button(self, text, action, width = 128):
        gui.Button(self.main, text = text, width = width, command = action).pack()

    def build_dropdown(self, values):
        var = gui.StringVar(self.main)
        var.set(values[0])
        gui.OptionMenu(self.main, var, *values).pack()
        return var

    def build_label(self, text):
        gui.Label(self.main, text = text).pack()

    def build_input(self):
        entry = gui.Entry(self.main)
        entry.pack()
        return entry

    def build_input_with_label(self, text):
        self.build_label(text)
        return self.build_input()

    def build_main_window(self):
        self.build_button("Add a student or discipline", lambda: self.show_window(self.build_add_window, True))
        self.build_button("Remove a student or discipline by ID", lambda: self.show_window(self.build_remove_window, True))
        self.build_button("Update a student or discipline", lambda: self.show_window(self.build_update_window, True))
        self.build_button("List all the students and disciplines", self.list_all)
        self.build_button("Grade students at a given discipline", lambda: self.show_window(self.build_grade_window, True))
        self.build_button("Search for disciplines and student", lambda: self.show_window(self.build_search_window, True))
        self.build_button("Show all students failing at one or more disciplines", self.failing_students)
        self.build_button("Show the students with the best school situation", self.best_students)
        self.build_button("Show all disciplines with grades", self.grades)
        self.build_button("Undo the previous operation", self.undo)
        self.build_button("Redo the previous undo", self.redo)
        self.build_button("Change to menu UI", self.ui)
        self.build_button("Run all tests", self.test)
        self.build_button("Exit", self.exit)

    def build_add_window(self):
        self.build_label("Add a new student or discipline")
        atype = self.build_dropdown(["student", "discipline"])
        aid = self.build_input_with_label("ID ")
        name = self.build_input_with_label("Name ")
        self.build_button("Add", lambda: self.add(atype.get(), aid.get(), name.get()), 32)

    def build_remove_window(self):
        self.build_label("Remove an existing student or discipline by ID")
        atype = self.build_dropdown(["student", "discipline"])
        aid = self.build_input_with_label("ID ")
        self.build_button("Remove", lambda: self.remove(atype.get(), aid.get()), 32)

    def build_update_window(self):
        self.build_label("Update an existing student or discipline by ID")
        atype = self.build_dropdown(["student", "discipline"])
        aid = self.build_input_with_label("ID ")
        name = self.build_input_with_label("Name ")
        self.build_button("Update", lambda: self.update(atype.get(), aid.get(), name.get()), 32)

    def build_grade_window(self):
        self.build_label("Grade an existing student at an existing discipline by ID")
        sid = self.build_input_with_label("Grade student (ID) ")
        did = self.build_input_with_label("At discipline (ID) ")
        grade = self.build_input_with_label("With grade ")
        self.build_button("Give grade", lambda: self.grade(sid.get(), did.get(), grade.get()), 32)

    def build_search_window(self):
        self.build_label("Search for a name or ID")
        keyword = self.build_input_with_label("Search for ")
        self.build_button("Search", lambda: self.search(keyword.get()), 32)

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
        