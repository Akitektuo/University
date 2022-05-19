clear;

function res = divDif(x, f)
  n = length(x);
  res = [f', zeros(n, n - 1)];
  for k = 2:n
    for i = 1:n-k+1
      res(i, k) = (res(i+1, k-1) - res(i, k-1)) / (x(i+k-1) - x(i));
    endfor
  endfor
  res = [x', res]; 
endfunction

function res = newtonPol(x, f, aprox)
  n = length(x) - 1;
  table = divDif(x, f);
  l = length(aprox);
  
  p = ones(1, l);
  res = table(1, 2) * ones(1, l);

  for i = 1:n
    p = p .* (aprox - x(i));
    res = res + p .* table(1, i + 2);
  endfor
endfunction

function res = aitkenPol(x, y, a, err)
  [res, I] = sort(abs(x - a));
  x = x(I);
  y = y(I);
  n = length(x);

  ak = zeros(n, n);
  ak(:,1) = y';
  for i = 1:n
      for j = 1:i-1
         ak(i, j+1) = (1 / (x(i) - x(j))) * (ak(j, j) * (x(i) - a) - ak(i, j) * (x(j) - a));
      endfor
      if i > 1 && abs(ak(i-1, i-1) - ak(i, i)) <= err
            res = ak(i, i);
            i
            return
      endif
   endfor
endfunction

function res = nevillePol(x, f, aprox)
  m = length(x);
  table = zeros(m, m);
  table(:, 1) = f;
 
  for i=2:m
    for j=2:i
      table(i, j) = ((x(i) - aprox) * table(i-1, j-1) + (aprox-x(i-j+1))*table(i, j-1))/(x(i)-x(i-j+1));   
    endfor
  endfor

  res = table(m,m);
endfunction