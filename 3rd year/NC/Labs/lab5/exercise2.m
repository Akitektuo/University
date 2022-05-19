values = [1 2];
funcValues = [0 0.6931];
derivValues = [1 0.5];
aprox = 1.5;

H = hermitePol(values, funcValues, derivValues, aprox)

err = abs(log(aprox) - H)