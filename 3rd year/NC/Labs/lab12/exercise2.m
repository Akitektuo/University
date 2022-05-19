f = @(E) E - 0.8*sin(E) - 2*pi/10;
fd = @(E) 1 - 0.8*cos(E);

x = newton(f, fd,  1, 6, 1e-4);