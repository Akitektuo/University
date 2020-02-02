class Product:

    def __init__(self, name, ptype, price):
        self.name = name
        self.type = ptype
        self.price = price

    def __str__(self):
        return self.name + ", " + self.type + ", " + str(self.price)


def bsort(arr, by = lambda e: e, reverse = False):
    l = len(arr)
    swaped = False
    for i in range(l):
        for j in range(i + 1, l):
            a = by(arr[i])
            b = by(arr[j])
            if (a > b, a < b)[reverse]: # same as: reverse ? a < b : a > b
                arr[i], arr[j] = arr[j], arr[i]
                swaped = True
        if not swaped:
            break


arr = [
    Product("paine", "mancare", 4),
    Product("lapte", "mancare", 5),
    Product("caiet", "rechizite", 3),
    Product("casti", "electronice", 40),
    Product("mouse", "electronice", 50),
    Product("tastatura", "electronice", 10),
]

# Alphabetically, by product name
bsort(arr, by = lambda p: p.name)
for product in arr:
    print(product)

print()

# By price, decreasing
bsort(arr, by = lambda p: p.price, reverse = True)
for product in arr:
    print(product)