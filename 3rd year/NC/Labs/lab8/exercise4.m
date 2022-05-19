a = 1;
b = 2;

f = @(x) x .* log(x);
n = 1;

correct = 0.636294368858383;
v = trapezium(f, a, b, n);
while abs(v - correct) > 6e-4
  n = n + 1;
  v = trapezium(f, a, b, n);
endwhile

printf("Approximation %d was reached for n=%d\n", v, n);