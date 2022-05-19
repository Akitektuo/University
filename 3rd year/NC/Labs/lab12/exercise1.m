f = @(x) x-cos(x);
fd = @(x) sin(x) + 1;

x = newton(f, fd,  pi/4, 100, 1e-4);