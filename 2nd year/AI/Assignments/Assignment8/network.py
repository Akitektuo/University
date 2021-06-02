import torch
from matplotlib import pyplot
from torch import nn
from torch.autograd import Variable
from torch.optim import Adam
from torch.utils.data import DataLoader

from ImageDataset import ImageDataset
from constants import PIC_SIZE, OUTPUT_NODES, LEARNING_RATE_DECAY, PERCENT_TEST, BATCH_SIZE, LEARNING_RATE, EPOCHS
from utils import random_subset, get_image_list, train_transformations, test_transformations

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")


class Unit(nn.Module):
    def __init__(self, in_channels, out_channels):
        super(Unit, self).__init__()
        self.conv = nn.Conv2d(in_channels=in_channels, kernel_size=3, out_channels=out_channels, stride=1, padding=1)
        self.bn = nn.BatchNorm2d(num_features=out_channels)
        self.relu = nn.ReLU()

    def forward(self, input_tensor):
        output = self.conv(input_tensor)
        output = self.bn(output)

        return self.relu(output)


class SimpleNet(nn.Module):
    def __init__(self):
        super(SimpleNet, self).__init__()

        unit1 = Unit(in_channels=3, out_channels=PIC_SIZE)
        unit2 = Unit(in_channels=PIC_SIZE, out_channels=PIC_SIZE)
        unit3 = Unit(in_channels=PIC_SIZE, out_channels=PIC_SIZE)

        pool1 = nn.MaxPool2d(kernel_size=2)

        unit4 = Unit(in_channels=PIC_SIZE, out_channels=PIC_SIZE * 2)
        unit5 = Unit(in_channels=PIC_SIZE * 2, out_channels=PIC_SIZE * 2)
        unit6 = Unit(in_channels=PIC_SIZE * 2, out_channels=PIC_SIZE * 2)
        unit7 = Unit(in_channels=PIC_SIZE * 2, out_channels=PIC_SIZE * 2)

        pool2 = nn.MaxPool2d(kernel_size=2)

        unit8 = Unit(in_channels=PIC_SIZE * 2, out_channels=PIC_SIZE * 4)
        unit9 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)
        unit10 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)
        unit11 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)

        pool3 = nn.MaxPool2d(kernel_size=2)

        unit12 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)
        unit13 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)
        unit14 = Unit(in_channels=PIC_SIZE * 4, out_channels=PIC_SIZE * 4)

        average_pool = nn.AvgPool2d(kernel_size=5)

        self.net = nn.Sequential(unit1, unit2, unit3, pool1, unit4, unit5, unit6, unit7, pool2, unit8, unit9, unit10,
                                 unit11, pool3, unit12, unit13, unit14, average_pool)

        self.fc = nn.Linear(in_features=PIC_SIZE * 4, out_features=OUTPUT_NODES)

    def forward(self, input_tensor):
        output = self.net(input_tensor)
        output = output.view(-1, PIC_SIZE * 4)

        return self.fc(output)


def adjust_learning_rate():
    for param_group in optimizer.param_groups:
        param_group["lr"] *= LEARNING_RATE_DECAY


def save_models(epoch):
    torch.save(model.state_dict(), f"models/network_{epoch}.model")


def test():
    model.eval()
    test_acc = 0.0

    for i, (images, labels) in enumerate(test_loader):
        if cuda_avail:
            images = Variable(images.cuda())
            labels = Variable(labels.cuda())
        outputs = model(images)
        _, prediction = torch.max(outputs.data, 1)
        test_acc += torch.sum(torch.eq(prediction, labels.data))

    return test_acc / test_size


def train(num_epochs):
    best_acc = 0.0
    best_at = 0
    loss_list = []
    least_loss = None

    for epoch in range(num_epochs):
        model.train()
        train_acc = 0.0
        train_loss = 0.0
        for images, labels in train_loader:
            if cuda_avail:
                images = Variable(images.cuda())
                labels = Variable(labels.cuda())
            optimizer.zero_grad()
            outputs = model(images)
            loss = loss_fn(outputs, labels)
            loss.backward()
            optimizer.step()
            train_loss += loss.cpu().data.item() * images.size(0)
            _, prediction = torch.max(outputs.data, 1)
            train_acc += torch.sum(prediction == labels.data)
        adjust_learning_rate()
        train_acc /= train_size
        train_loss /= train_size
        loss_list.append(train_loss)
        if least_loss is None:
            least_loss = (epoch, train_loss)
        model.eval()
        test_acc = test()
        save_models(epoch)
        if test_acc > best_acc:
            best_acc = test_acc
            best_at = epoch
        print(f"Epoch: {epoch}, "
              f"Train accuracy: {train_acc}, "
              f"Train loss: {train_loss}, "
              f"Test accuracy: {test_acc}, "
              f"Best test accuracy: {best_acc}, "
              f"Best at: {best_at}, ")
        if train_loss < least_loss[1]:
            least_loss = (epoch, train_loss)
        if epoch - least_loss[0] > 10:
            print("Stuck, aborting")
            break
    pyplot.plot(loss_list)
    pyplot.ylim((0, None))


if __name__ == "__main__":
    train_images, test_images = random_subset(get_image_list(), 1 - PERCENT_TEST)

    train_size = len(train_images)
    train_set = ImageDataset(train_images, train_transformations)
    train_loader = DataLoader(train_set, batch_size=BATCH_SIZE, shuffle=True, num_workers=4)

    test_size = len(test_images)
    test_set = ImageDataset(test_images, test_transformations)
    test_loader = DataLoader(test_set, batch_size=BATCH_SIZE, shuffle=False, num_workers=4)

    cuda_avail = torch.cuda.is_available()
    model = SimpleNet()
    if cuda_avail:
        model.cuda(device)
        
    optimizer = Adam(model.parameters(), lr=LEARNING_RATE, weight_decay=0.0)
    loss_fn = nn.CrossEntropyLoss()

    print("Training...")
    train(EPOCHS)
