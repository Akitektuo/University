r = 110; 
p = 75; 
a = 0; 
b = 2 * pi;
n1 = 2; 
n2 = 3;

coef = 60 * r / (r * r - p * p);
f = @(x) sqrt(1 - (p / r) ^ 2 * sin(x));

v1 = trapezium(f, a, b, n1);
v2 = trapezium(f, a, b, n2);

n1
r1 = coef * v1
n2
r2 = coef * v2