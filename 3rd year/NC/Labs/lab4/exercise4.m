x = [64 81 100 121 144 169];
y = [8 9 10 11 12 13];

a = 115;

answer = aitkenPol(x, y, a, 1e-3)

err = abs(answer - sqrt(a))