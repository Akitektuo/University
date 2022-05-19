x = 1:7;
f = [13, 15, 20, 14, 15, 13, 10];

A = [sum(x.^2), sum(x); sum(x), length(x)];
B = [sum(x.*f); sum(f)];

X = linsolve(A,B);
fprintf('phi(x) = %f x + %f\n',X);
v = polyval(X, 8)

p = polyfit(x, f, 1);
fprintf('phi(x) = %f x + %f\n',p);
v2 = polyval(p, 8)

E_min = sum((f-polyval(p,x)).^2)

plot(x,f,'bo');
hold on

xp = 1:0.01:8;
plot(xp, polyval(p, xp),'r-');