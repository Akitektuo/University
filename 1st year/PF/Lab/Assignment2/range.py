# Class Range is used to store the start and end properties - [start, end)
class Range:

    # Init block
    def __init__(self, start = 0, end = 0):
        self.start = start
        self.end = end

    # Override str()
    def __str__(self):
        return "[" + str(self.start) + ", " + str(self.end) + ")"

    '''
        Returns the size of the Range

        Input: -
        Output int - representing the size
    '''
    def get_size(self):
        return self.end - self.start

    '''
        Updates the instance of the class with the given start and end

        Input:
            int - representing start
            int - representing the end
        Output: -
    '''
    def update(self, start, end):
        if start > end:
            self.start = end
            self.end = start
            return
        self.start = start
        self.end = end

    '''
        Returns the instance of the class as a standard range

        Input: -
        Output: range - represeting the converted instance
    '''
    def to_range(self):
        return range(self.start, self.end)