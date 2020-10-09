x = linspace(0, 3);
f = x.^5/10;
g = x.*sin(x);
h = cos(x);
plot(x, f, "-");
hold on;
plot(x, g, "--");
hold on;
plot(x, h, ":");
