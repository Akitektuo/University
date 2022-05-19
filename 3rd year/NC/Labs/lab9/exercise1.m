x = linspace(0,10,20);

f = @(x) exp(-x.^2);

a = 1;
b = 1.5;

% a 
v1 = rectangle(a,b,f)

% b 
hold on
axis([a b f(b) f(a)])
fill([a,a,b,b], [0,f((a+b)/2),f((a+b)/2), 0], 'r');
fplot(f,[a b]);

% c 
n1 = 150;
n2 = 500;

v2 = repeated_rectangle(a,b,n1,f)
v3 = repeated_rectangle(a,b,n2,f)