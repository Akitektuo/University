probability = input("([0.05, 0.95]) probability = ");

plot([]);

for n = 1:10:200;
  values = 1:n;
  binomialResult = binopdf(values, n, probability);
  plot(values, binomialResult);
  pause(0.5);
endfor
