x = [0, pi / 2, pi, (3 * pi) / 2, 2 * pi];
fVals = sin(x);

xRes = spline(x, [1, fVals, 1]);
clamped = ppval(xRes, pi / 4)

fRes = spline(x, fVals);
natural = ppval(fRes, pi / 4)

interval = linspace(0, 2*pi, 50);

hold on;
plot(interval, sin(interval));
xPpval = ppval(xRes, interval);
plot(interval, xPpval);
fPPval = ppval(fRes, interval);
plot(interval, fPPval);