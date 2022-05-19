range = 1:0.25:2.5;
fun = @(x) sqrt(5 * x.^2 + 1);

function finite_diff = dif(x, f)
  n = length(x);
  finite_diff = [f(x)', zeros(n, n-1)];
  for k = 2:n
    for i = 1:n-k+1
      finite_diff(i,k) = finite_diff(i+1, k-1) - finite_diff(i, k-1);
    endfor
  endfor
  finite_diff = [x', finite_diff];
endfunction

res = dif(range, fun)