from PIL import Image
from matplotlib import image, pyplot


def task_a():
    img = Image.open("f1.jpg")

    print(img.format)
    print(img.mode)
    print(img.size)

    img.show()


def task_b():
    data = image.imread("f1.jpg")

    print(data.dtype)
    print(data.shape)

    pyplot.imshow(data)
    pyplot.show()


def task_c():
    img = Image.open("f1.jpg")

    print(img.size)

    img.thumbnail((100, 100))

    print(img.size)


task_c()
