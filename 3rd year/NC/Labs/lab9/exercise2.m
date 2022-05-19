f = @(x) 2./(1+x.^2);

eps = 1e-4;
actual = pi/2;

k=0;
res = romberg(0,1,f,k);
while abs(res-actual) > eps
  k = k+1;
  res = romberg(0,1,f,k);
endwhile

k
res