sample = [7, 7, 4, 5, 9, 9,
    4, 12, 8, 1, 8, 7,
    3, 13, 2, 1, 17, 7,
    12, 5, 6, 2, 1, 13,
    14, 10, 2, 4, 9, 11,
    3, 5, 12, 6, 10, 7];

sigma = 5;
n = columns(sample) * rows(sample)
meanResult = mean(mean(sample));

confidenceLevel = input('Give the confidence level (in (0,1 )): ');
alpha = 1 - confidenceLevel;

division = sigma / sqrt(n);
left = meanResult - division * norminv(1 - alpha / 2, 0, 1);
right = meanResult - division * norminv(alpha / 2, 0, 1);

fprintf("The interval is [%.3f, %.3f]\n", left, right);