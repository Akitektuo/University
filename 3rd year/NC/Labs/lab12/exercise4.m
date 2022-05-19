f = @(x) (x - 2) ^ 2 - log(x);

bisect(f, 1, 2, 1e-4);

false_position(f, 1, 2, 1e-4);