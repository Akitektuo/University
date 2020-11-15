class Action:
    ADD = "action_add"
    REMOVE = "action_remove"
    UPDATE = "action_update"
    LIST = "action_list"
    GRADE = "action_grid"
    SEARCH = "action_search"
    SEE_FAILING_STUDENTS = "action_see_failing_students"
    SEE_BEST_STUDENTS = "action_see_best_students"
    SEE_GRADES = "action_see_grades"
    UNDO = "action_undo"
    REDO = "action_redo"
    HELP = "action_help"
    UI = "action_ui"
    TEST = "action_test"
    EXIT = "action_exit"
    
    def __init__(self, atype, params = []):
        self.type = atype
        self.params = params