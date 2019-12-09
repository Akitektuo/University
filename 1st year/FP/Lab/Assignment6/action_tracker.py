import copy

class Action:
    
    def __init__(self, atype, data, data_type = ""):
        self.type = atype
        self.data = data
        self.data_type = data_type

class ActionTracker:
    ADD = "add"
    ADD_MULTIPLE = "add_multiple"
    EDIT = "edit"
    REMOVE = "remove"
    REMOVE_MULTIPLE = "remove_multiple"

    STUDENT = "student"
    DISCIPLINE = "discipline"
    GRADE = "grade"

    oposite_action = {
        ADD: REMOVE,
        ADD_MULTIPLE: REMOVE_MULTIPLE,
        REMOVE: ADD,
        REMOVE_MULTIPLE: ADD_MULTIPLE,
        EDIT: EDIT
    }

    def __init__(self, repo_to_track):
        self.repo_to_track = repo_to_track
        self.actions = []
        self.reverted_actions = []

    def add_action(self, atype, data, data_type = ""):
        self.reverted_actions.clear()
        atype = self.oposite_action[atype]
        self.actions.append(Action(atype, data, data_type))

    def nothing_to_undo(self):
        return len(self.actions) < 1

    def nothing_to_redo(self):
        return len(self.reverted_actions) < 1

    def undo(self):
        if self.nothing_to_undo():
            raise Exception("Nothing left to undo")
        last_action = self.actions.pop()
        if last_action.type == self.ADD_MULTIPLE:
            return self.add_multiple(last_action, True)
        if last_action.type == self.EDIT:
            return self.edit(last_action, True)
        if last_action.type == self.REMOVE:
            return self.remove(last_action)
        if last_action.type == self.REMOVE_MULTIPLE:
            return self.remove_multiple(last_action, True)

    def redo(self):
        if self.nothing_to_redo():
            raise Exception("Nothing left to redo")
        last_action = self.reverted_actions.pop()
        if last_action.type == self.ADD:
            return self.add(last_action)
        if last_action.type == self.ADD_MULTIPLE:
            return self.add_multiple(last_action, False)
        if last_action.type == self.EDIT:
            return self.edit(last_action, False)
        if last_action.type == self.REMOVE_MULTIPLE:
            return self.remove_multiple(last_action, False)

    def add(self, action):
        self.actions.append(Action(self.REMOVE, action.data, action.data_type))
        if action.data_type == self.STUDENT:
            return self.repo_to_track.students.add(action.data)
        if action.data_type == self.DISCIPLINE:
            return self.repo_to_track.disciplines.append(action.data)
        if action.data_type == self.GRADE:
            return self.repo_to_track.grades.append(action.data)

    def add_multiple(self, action, undo):
        if undo:
            self.reverted_actions.append(Action(self.REMOVE_MULTIPLE, action.data))
        else:
            self.actions.append(Action(self.REMOVE_MULTIPLE, action.data))
        self.repo_to_track.add_all(action.data, False)

    def edit(self, action, undo):
        data = action.data
        if action.data_type == self.STUDENT:
            student = self.repo_to_track.get_student(data.id)
            if undo:
                self.reverted_actions.append(Action(self.EDIT, copy.copy(student), self.STUDENT))
            else:
                self.actions.append(Action(self.EDIT, copy.copy(student), self.STUDENT))
            return self.repo_to_track.update_student(data.id, data.name, False)
        if action.data_type == self.DISCIPLINE:
            discipline = self.repo_to_track.get_discipline(data.id)
            if undo:
                self.reverted_actions.append(Action(self.EDIT, copy.copy(discipline), self.DISCIPLINE))
            else:
                self.actions.append(Action(self.EDIT, copy.copy(discipline), self.DISCIPLINE))
            return self.repo_to_track.update_discipline(data.id, data.name, False)

    def remove(self, action):
        self.reverted_actions.append(Action(self.ADD, action.data, action.data_type))
        if action.data_type == self.STUDENT:
            return self.repo_to_track.students.remove(action.data)
        if action.data_type == self.DISCIPLINE:
            return self.repo_to_track.disciplines.remove(action.data)
        if action.data_type == self.GRADE:
            return self.repo_to_track.grades.remove(action.data)

    def remove_multiple(self, action, undo):
        if undo:
            self.reverted_actions.append(Action(self.ADD_MULTIPLE, action.data))
        else:
            self.actions.append(Action(self.ADD_MULTIPLE, action.data))
        for sr in action.data.students:
            self.repo_to_track.students.remove(sr)
        for dr in action.data.disciplines:
            self.repo_to_track.disciplines.remove(dr)
        for gr in action.data.grades:
            self.repo_to_track.grades.remove(gr)

