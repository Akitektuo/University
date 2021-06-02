import random

from PIL import Image
from torchvision.transforms import transforms

from constants import PIC_SIZE


def random_subset(subset, percentage):
    out1 = []
    out2 = []

    for x in subset:
        if random.random() < percentage:
            out1.append(x)
        else:
            out2.append(x)

    return out1, out2


def get_image_list():
    image_list = []

    for index in range(1, 4):
        image_list.extend([f"data/dataset{index}/man{i}.jpg" for i in range(1, 51)])
        image_list.extend([f"data/dataset{index}/woman{i}.jpg" for i in range(1, 51)])
        image_list.extend([f"data/dataset{index}/other{i}.jpg" for i in range(1, 51)])

    return image_list


def strings_to_images(strings):
    return [Image.open(file_name).convert("RGB") for file_name in strings]


test_transformations = transforms.Compose([
    transforms.Resize(PIC_SIZE),
    transforms.CenterCrop(PIC_SIZE),
    transforms.ToTensor(),
    transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
])


train_transformations = transforms.Compose([
    transforms.Resize(PIC_SIZE),
    transforms.CenterCrop(PIC_SIZE),
    transforms.RandomHorizontalFlip(),
    transforms.ToTensor(),
    transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
])
