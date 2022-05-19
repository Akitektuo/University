x = -1:0.01:1;

hold on;

% a)
T1 = @(t) cos(1 .* acos(t));
plot(x, T1(x))

T2 = @(t) cos(2 .* acos(t));
plot(x, T2(x))

T3 = @(t) cos(3 .* acos(t));
plot(x, T3(x))

% b)
##function c = cheby(n, x)
##  if n == 0
##    c = 1;
##  elseif n == 1
##    c = x;
##  else 
##    c = 2 * x .* cheby(n - 1, x) - cheby(n - 2, x);
##  endif
##endfunction
##
##for n = 1:4
##  l = cheby(n, x);
##  plot(x, l)
## endfor