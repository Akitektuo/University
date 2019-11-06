class Action:
    
    def __init__(self, atype, data, data_type):
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

    def add_action(self, atype, data, data_type = ""):
        atype = self.oposite_action[atype]
        self.actions.append(Action(atype, data, data_type))

    def is_empty(self):
        return len(self.actions) < 1

    def revert(self):
        if self.is_empty():
            raise Exception("Nothing left to undo")
        last_action = self.actions.pop()
        if last_action.type == self.ADD_MULTIPLE:
            return self.add_multiple(last_action)
        if last_action.type == self.EDIT:
            return self.edit(last_action)
        if last_action.type == self.REMOVE:
            return self.remove(last_action)
        if last_action.type == self.REMOVE_MULTIPLE:
            return self.remove_multiple(last_action)

    def add_multiple(self, action):
        self.repo_to_track.add_all(action.data, False)

    def edit(self, action):
        data = action.data
        if action.data_type == self.STUDENT:
            return self.repo_to_track.update_student(data.id, data.name, False)
        if action.data_type == self.DISCIPLINE:
            return self.repo_to_track.update_discipline(data.id, data.name, False)

    def remove(self, action):
        if action.data_type == self.STUDENT:
            return self.repo_to_track.students.remove(action.data)
        if action.data_type == self.DISCIPLINE:
            return self.repo_to_track.disciplines.remove(action.data)
        if action.data_type == self.GRADE:
            return self.repo_to_track.grades.remove(action.data)

    def remove_multiple(self, action):
        for sr in action.data.students:
            self.repo_to_track.students.remove(sr)
        for dr in action.data.disciplines:
            self.repo_to_track.disciplines.remove(dr)
        for gr in action.data.grades:
            self.repo_to_track.grades.remove(gr)

