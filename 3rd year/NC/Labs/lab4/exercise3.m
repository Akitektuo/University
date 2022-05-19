f = @(x)(exp(sin(x)));

x = linspace(0, 6, 13);

y = f(x);

aprox = 0:0.1:6;

plot(x, f(x),'*')

hold on

N = newtonPol(x, y, aprox);

plot(aprox, N)
plot(aprox, f(aprox))