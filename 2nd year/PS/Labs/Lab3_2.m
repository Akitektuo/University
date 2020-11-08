m = input("m = ");
s = input("s = ");

cdfPositive = normcdf(1, m, s);
cdfNegative = normcdf(-1, m, s);
firstResult = cdfPositive - cdfNegative
secondResult = 1 - firstResult
