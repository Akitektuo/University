from torch.utils.data import Dataset

from utils import strings_to_images


class ImageDataset(Dataset):

    def __init__(self, image_list, transformation):
        image_classes = []
        for image in image_list:
            if "woman" in image:
                image_classes.append("woman")
            elif "man" in image:
                image_classes.append("man")
            else:
                image_classes.append("other")

        image_list = strings_to_images(image_list)
        self.images = []
        self.labels = []
        self.class_to_label = {"man": 1, "woman": 1, "other": 0}

        for image, image_class in zip(image_list, image_classes):
            transformed_image = transformation(image)
            self.images.append(transformed_image)
            label = self.class_to_label[image_class]
            self.labels.append(label)

    def __getitem__(self, index):
        return self.images[index], self.labels[index]

    def __len__(self):
        return len(self.images)
