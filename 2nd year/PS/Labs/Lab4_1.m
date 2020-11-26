p = input("probability = ");
U = rand;
X = U < p;

N = input("number of simulations = ");
for i = 1:N
  U = rand;
  X(i) = U < p;
endfor

UX = unique(X)
histogram = hist(X, length(UX));

relativeFrequency = histogram / N