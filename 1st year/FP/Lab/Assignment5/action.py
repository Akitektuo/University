ACTION_ADD = "action_add"
ACTION_REMOVE = "action_remove"
ACTION_UPDATE = "action_update"
ACTION_LIST = "action_list"
ACTION_GRADE = "action_grid"
ACTION_SEARCH = "action_search"
ACTION_SEE_FAILING_STUDENTS = "action_see_failing_students"
ACTION_SEE_BEST_STUDENTS = "action_see_best_students"
ACTION_SEE_GRADES = "action_see_grades"
ACTION_UNDO = "action_undo"
ACTION_HELP = "action_help"
ACTION_EXIT = "action_exit"

class Action:
    
    def __init__(self, atype, params = []):
        self.type = atype
        self.params = params