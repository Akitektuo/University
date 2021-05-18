import torch
import torch.nn.functional as F
import matplotlib.pyplot as plt
import numpy as np

from math import sin

from matplotlib import pyplot

import code.myModel as myModel

PI = 3.14159265358979

x = torch.rand(1000, 2).mul(20).sub(10)

# Creates a one-dimensional tensor of size 100 whose values are evenly spaced
# from -1 to 1, inclusive, and is placing the values in a tensor like
# so x = [ [-1], [-0.98], ..., [0.98]. 1]

print(x)

# Compute the values for the function

y = []
for domain in x:
	y.append(sin(domain[0] + domain[1] / PI))

y = torch.Tensor(y)
y = y.unsqueeze(1)

# the function to be optimised

# we set up the lossFunction as the mean square error
lossFunction = torch.nn.MSELoss()

# we create the ANN
ann = myModel.Net(n_feature=2, n_hidden=64, n_output=1)

print(ann)
# we use an optimizer that implements stochastic gradient descent 
optimizer_batch = torch.optim.SGD(ann.parameters(), lr=0.01)

# we memorize the losses forsome graphics
loss_list = []
avg_loss_list = []

# we set up the environment for training in batches  
batch_size = 16
n_batches = int(len(x) / batch_size)
print(n_batches)

epochs = []
losses = []

for epoch in range(2000):

	for batch in range(n_batches):
		# we prepare the current batch  -- please observe the slicing for tensors
		start = batch * batch_size
		end = (batch + 1) * batch_size
		batch_X, batch_Y = x[start:end], y[start:end]

		# we compute the output for this batch
		prediction = ann(batch_X)

		# we compute the loss for this batch
		loss = lossFunction(prediction, batch_Y)

		# we save it for graphics
		loss_list.append(loss)

		# we set up the gradients for the weights to zero (important in pytorch)
		optimizer_batch.zero_grad()

		# we compute automatically the variation for each weight (and bias) of the network
		loss.backward()

		# we compute the new values for the weights
		optimizer_batch.step()

	# we print the loss for all the dataset for each 10th epoch
	if epoch % 100 == 99:
		y_pred = ann(x)
		loss = lossFunction(y_pred, y)
		epochs.append(epoch)
		losses.append(loss.item())
		print('\repoch: {}\tLoss =  {:.5f}'.format(epoch, loss))
		pyplot.plot(epochs, losses)
		pyplot.show()

# Specify a path
filepath = "myNet.pt"

# save the model to file
torch.save(ann.state_dict(), filepath)

# visualise the parameters for the ann (aka weights and biases)
# for name, param in ann.named_parameters():
#     if param.requires_grad:
#         print (name, param.data)
