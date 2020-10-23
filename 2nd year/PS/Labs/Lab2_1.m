numberOfValues = input("Input number of values > ");
probability = input("Input probability > ");

if probability > 1
  disp("Probability is between 0 and 1, terminating script");
  return;
end

values = 0:0.001:numberOfValues;

pdfGraph = binopdf(values, numberOfValues, probability);
plot(values, pdfGraph, "*");
hold on;

cdfGraph = binocdf(values, numberOfValues, probability);
plot(values, cdfGraph);
hold on;

count = 0;
for i = 1:3
  toss = rand;
  if toss > 0.5
    count++;
  end
end
X = count
