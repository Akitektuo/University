p = input("Number of probability = ");
N = input("Number of simulations = ");
n = input("Number of trials = ");

U = rand(n, 1);
X = sum(U < p);
for i = 1:N
  U = rand(n, 1);
  X(i) = sum(U < p);
endfor

UX = unique(X);
NX = hist(x, length(UX));
relativeFrequency = NX / N;
k = 0:n;
binomial = binopdf(k, n, p);
plot(k, binomial, '*', UX, relativeFrequency);