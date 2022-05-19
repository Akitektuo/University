hold on;

range = -1:0.01:3;
n = 4;

function T = taylor(N)
  T = [1];
  fact = 1;
  for i = 1:N
    fact = fact * i;
    T(end + 1) = (1 / fact);
  endfor
  T = flip(T);
endfunction

for i = 1:n;
  values = polyval(taylor(i), range);
  plot(range, values);
endfor