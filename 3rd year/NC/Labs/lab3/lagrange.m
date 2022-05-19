clear

function result = aiProduct(x, i)
  result = 1;
  m = length(x);
  for j = 1:m
    if i != j
      result = result * (x(i) - x(j));
    endif
  endfor
  result = 1 / result;
endfunction

function result = lagrange(x, f, X)
  m = length(x);
  P = zeros(1, m);
  N = length(X);
  result = zeros(1,N);
  for j = 1:N
    supra = 0;
    sub = 0;
    for i = 1:m
      P(i) = aiProduct(x, i);
      div = P(i) / (X(j)-x(i));
      supra = supra + div * f(i);
      sub = sub + div;
    endfor
    result(j) = supra / sub;
  end
endfunction