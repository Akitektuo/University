import os

import torch
from PIL import Image

from constants import MODEL_FILE_PATH
from network import SimpleNet
from utils import test_transformations

ann = SimpleNet()
ann.load_state_dict(torch.load(MODEL_FILE_PATH))
ann.eval()
success = 0
total = 0

for index in (1, 2, 3):
    which = 0
    bad = 0
    for file in (file_list := os.listdir(f"data/dataset{index}")):
        which += 1
        total += 1
        path = f"./data/dataset{index}/{file}"
        image = test_transformations(Image.open(path).convert("RGB"))
        image = image.unsqueeze(0)
        output = ann(image)
        other, prediction = torch.max(output.data, 1)
        if "man" in file:
            expected = 1
        else:
            expected = 0
        if expected == prediction.numpy()[0]:
            success += 1
        else:
            bad += 1
            print(path, f"{which} out of {len(file_list)}")
    print(f"{bad} bad out of {len(file_list)} in dataset{index}")

print(f"The accuracy is {success * 100 / total}")
