cases = input("([30, inf)) n = ");
probability = input("([0, 0.05]) probability = ");

values = 0:cases;
binomial = binopdf(values, cases, probability);
poisson = poisspdf(values, cases * probability);

plot(values, binomial, "color", "b", "*");
hold on;
plot(values, poisson, "color", "r");