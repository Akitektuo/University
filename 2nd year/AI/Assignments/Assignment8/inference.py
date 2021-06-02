import os

import torch
from PIL import Image

from constants import MODEL_FILE_PATH
from network import SimpleNet
from utils import test_transformations

ann = SimpleNet()
ann.load_state_dict(torch.load(MODEL_FILE_PATH))
ann.eval()

for file in os.listdir("data/test"):
    path = f"./data/test/{file}"
    image = test_transformations(Image.open(path).convert("RGB"))
    image = image.unsqueeze(0)
    output = ann(image)
    other, prediction = torch.max(output.data, 1)
    if 1 == prediction.numpy()[0]:
        print(f"is a face\t\t✓\t{file}")
    else:
        print(f"is not a face\t✗\t{file}")
