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
        if last_action.type == ADD:
            return self.add(last_action)
        if last_action.type == ADD_MULTIPLE:
            return self.add_multiple(last_action)
        if last_action.type == EDIT:
            return self.edit(last_action)
        if last_action.type == REMOVE:
            return self.remove(last_action)
        if last_action.type == REMOVE_MULTIPLE:
            return self.remove_multiple(last_action)

    def add(self, action):
        if action.data_type == STUDENT:
            self.repo_to_track.students.append(action.data)
            return
        if action.data_type == DISCIPLINE:
            self.repo_to_track.disciplines.append(action.data)
            return

    def add_multiple(self, action):
        pass

    def edit(self, action):
        pass

    def remove(self, action):
        pass

    def remove_multiple(self, action):
        pass

