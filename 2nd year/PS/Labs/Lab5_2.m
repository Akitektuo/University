confidenceLevel = input("Give confidence level (in (0, 1)): ");
alpha = 1 - confidenceLevel;
sample = [7 7 4 5 9 9 ...
4 12 8 1 8 7 ...
3 13 2 1 17 7 ...
12 5 6 2 1 13 ...
14 10 2 4 9 11 ...
3 5 12 6 10 7 ];

n = length(sample);
xbar = mean(sample);

quantile1= tinv(1-alpha/2, n - 1);
quantile2= tinv(alpha/2, n - 1); 
s = std(sample);

left = xbar - (s/(sqrt(n))) * quantile1;
right = xbar - (s/(sqrt(n))) * quantile2;

fprintf('C.I for the population mean, miu, case sigma known is (% 3.5f, %3.5f)\n',left,right)