clear;

function x = gauss(A, b)
  [none, n] = size(A);
  
  for p = 1 : n - 1
    # get max 
    [none, q] = max(abs(A(p:n, p)));
    q = q + p - 1;
    
    # interchange elements in A and b
    if (q!=p)
      A([p, q], :) = A([q, p], :);
      b([p, q]) = b([q, p]);
    endif
   
    for i = p + 1 : n 
      const = A(i,p)/A(p,p);
      b(i) = b(i) - const * b(p);
      A(i, p : n) = A(i, p : n) - const * A(p, p : n);
    endfor
   
  endfor
  
  % backward elimination
  sol = zeros(size(b));
  for i = n : -1: 1
    sol(i) = (b(i) - A(i, i + 1 : n) * sol(i + 1 : n)) / A(i,i);
  endfor
  x = sol;
endfunction