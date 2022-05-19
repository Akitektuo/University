values = linspace(-5, 5, 15);
f = @(x)sin(2*x);

funcValues = f(values);

deriv = @(x)2*cos(2*x);

derivValues = deriv(values);

aprox = linspace(-5, 5, 50);

H = hermitePol(values, funcValues, derivValues, aprox);

hold on 
plot(aprox, f(aprox),'*')
plot(aprox, H)