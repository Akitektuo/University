a = 0;
b = pi;
n1 = 10;
n2 = 30;

f = @(x) 1./(4+sin(20.*x));

correct = 0.8111579

n1
v1 = simpsons(f,a,b,n1)

n2 
v2 = simpsons(f,a,b,n2)