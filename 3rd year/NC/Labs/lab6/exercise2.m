[x, y] = ginput(5);
a = min(x);
b = max(x);

t = linspace(a, b, 50);

pol = spline(x, y);

hold on

plot(x, y, 'o')
plot(t, ppval(pol, t))