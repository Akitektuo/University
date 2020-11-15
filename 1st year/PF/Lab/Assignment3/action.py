from __future__ import absolute_import

class Action:
    
    def __init__(self, atype, params = []):
        self.type = atype
        self.params = params