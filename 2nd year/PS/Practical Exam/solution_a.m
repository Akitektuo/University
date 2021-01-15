pkg load statistics;

x = [1001.7, 975.0, 978.3, 988.3, 978.7, 988.9, 1000.3, 979.2, 968.9, 983.5, 999.2, 985.6];
y = 995;

fprintf("\n\n\n");

n = length(x)
m = mean(x)
sigma = var(x)

% aplha = input("Significance level (0, 1) = ");
alpha = 0.05;

%a)
typeA = 1; 
fprintf("\na)\n\n");
fprintf("Significance level %f: \n", alpha);

[h, pValue, ci, stats] = ttest(x, y, 'alpha', alpha, 'tail', 'right');
rr = [tinv(1 - alpha, n - 1), Inf];

fprintf("The confidence interval is (%4.4f, %4.4f)\n", ci);
fprintf("The rejection region is (%4.4f, %4.4f)\n", rr);
fprintf("The value of the test statistic t is %4.4f\n", stats.tstat);
fprintf("The P-value of the test is %4.4f\n", pValue);

if h == 1 % result of the test, h = 0, if H0 is NOT rejected, h = 1, if H0 IS rejected
    fprintf("\nThe null hypothesis is rejected.\n");
    fprintf("The data suggests that the muzzles ARE NOT fatser.\n");
else
    fprintf("\nThe null hypothesis is not rejected.\n");
    fprintf("The data suggests that the muzzles ARE fatser.\n");
end