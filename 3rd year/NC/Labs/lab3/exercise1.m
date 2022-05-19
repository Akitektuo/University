range = 1930:10:1980;
values = [123203, 131669, 150697, 179323, 203212, 226505];
aprox = [1955, 1995];
  
L = lagrange(range, values, aprox);

size = length(L);
for i = 1:size
  round(L(i))
endfor