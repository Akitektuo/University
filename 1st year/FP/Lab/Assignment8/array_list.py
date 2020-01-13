class ArrayList:

    def __init__(self):
        self.data = []
        self.index = 0

    def get_size(self):
        return len(self.data)

    def add(self, item):
        self.data.append(item)

    def remove(self, item):
        self.data.remove(item)

    def __setitem__(self, key, item):
        if key < self.get_size():
            self.data[key] = item
            return

        while self.get_size() < key:
            self.add(None)
        self.add(item)
        
    def __delitem__(self, key):
        self.remove(self.data[key])

    def __iter__(self):
        self.index = 0
        return self

    def __next__(self):
        try:
            item = self.data[self.index]
        except IndexError:
            raise StopIteration()
        self.index += 1
        return item

    def __heapify(self, arr, n, i, cond):
        largest = i
        l = 2 * i + 1
        r = 2 * i + 2

        if l < n and cond(arr[i], arr[l]):
            largest = l

        if r < n and cond(arr[largest], arr[r]):
            largest = r

        if largest != i:
            arr[i], arr[largest] = arr[largest], arr[i]
            self.__heapify(arr, n, largest, cond)

    def sorted(self, condition):
        arr = list(self.data)
        n = len(arr)

        for i in range(n, -1, -1):
            self.__heapify(arr, n, i, condition)

        for i in range(n - 1, 0, -1):
            arr[i], arr[0] = arr[0], arr[i]
            self.__heapify(arr, i, 0, condition)

        return arr

    def filtered(self, condition):
        filtered = ArrayList()
        for i in self:
            if condition(i):
                filtered.add(i)
        return filtered

    def __str__(self):
        return str(self.data)
