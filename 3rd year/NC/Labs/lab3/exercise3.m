f = @(a) (1 + cos(pi * a)) ./ (1 + a);

range = 0:0.01:10;

points = linspace(0, 10, 21);
values = f(points);

plot(range, f(range));
hold on;

L = lagrange(points, values, range);

plot(range, L);