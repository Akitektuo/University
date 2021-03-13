class Node:
    def __init__(self, parent=None, position=None):
        self.parent = parent
        self.position = position

        self.distance_to_start = 0  # = g
        self.distance_to_goal = 0  # = h
        self.total_cost = 0  # = f

    def __eq__(self, other):
        return self.position == other.position

    def __lt__(self, other):
        return self.total_cost < other.total_cost

    def __gt__(self, other):
        return self.total_cost > other.total_cost

    def get_distances_sum(self) -> int:
        return self.distance_to_start + self.distance_to_goal

    def update_total_cost(self):
        self.total_cost = self.get_distances_sum()
