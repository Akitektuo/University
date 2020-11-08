m = input("m = ");
s = input("s = ");
alpha = input("alpha = ");
beta = input("beta = ");

firstRsultA = normcdf(0, m, s)
secondResultA = 1 - firstRsultA

cdfPositive = normcdf(1, m, s);
cdfNegative = normcdf(-1, m, s);
firstResultB = cdfPositive - cdfNegative
secondResultB = 1 - firstResultB

resultC = norminv(alpha, m, s)

resultD = norminv(1 - beta, m, s)