x = [1 2 3 4 5];
f = [22 23 25 30 28];

y = [2.5];

N = newtonPol(x, f, y)

plot(x, f, 'b*')

hold on;

z = 0:0.01:6;
A = newtonPol(x, f, z);

plot(z, A,'r')