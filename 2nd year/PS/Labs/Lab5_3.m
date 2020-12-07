%1-alpha
confidenceLevel = input("Give confidence level (in (0, 1)): ");
alpha = 1 - confidenceLevel;
sample = [7 7 4 5 9 9 ...
4 12 8 1 8 7 ...
3 13 2 1 17 7 ...
12 5 6 2 1 13 ...
14 10 2 4 9 11 ...
3 5 12 6 10 7 ];

sSqaured = var(sample)


quantile1= chi2inv(1-alpha/2, n - 1);
quantile2= chi2inv(alpha/2, n - 1);

left=((n-1)*sSqaured)/quantile1;
right=((n-1)*sSqaured)/quantile2;

fprintf('C.I for the population pop. variance sigma^2 is (% 3.5f, %3.5f)\n',left,right)
