x = [-2, -1, 0, 1, 2];

f1 = 3.^x;

actual = sqrt(3)

pred1 = nevillePol(x, f1, 1/2)

x = [0, 1, 2, 4, 5];

f2 = sqrt(x);

pred2 = nevillePol(x, f2, 3)