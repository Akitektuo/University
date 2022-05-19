a = 0;
const = 2./sqrt(pi);

int = @(t) exp(-1 .* (t .^ 2));

erf = @(x, n) const .* simpsons(int, a, x, n);

n1 = 4;
n2 = 10;

correct = 0.520499876;

r1 = erf(0.5, n1)
err1 = abs(r1-correct)

r2 = erf(0.5, n2)
err2 = abs(r2-correct)