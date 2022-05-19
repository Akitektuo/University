A = [ 10 7 8 7; 7 5 6 5; 8 6 10 9;7 5 9 10 ];
b = [ 32; 23; 33; 31 ];

printf("point a)\n");

x = gauss(A,b)
cond_nr = cond(A)


printf("point b)\n");

b2 = [ 32.1; 22.9; 33.1; 30.9 ];

x2 = gauss(A,b2)
      
inputB = norm(b - b2,2) / norm(b,2)
outputB = norm(x - x2,2) / norm(x,2)

errB = outputB / inputB 


printf("point c)\n");

A3 = [ 10 7 8.1 7.2; 7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98 ];
      
x3 = gauss(A3,b)
      
inputC = norm(A - A3,2) / norm(A,2)
outputC = norm(x - x3,2) / norm(x,2)

errC = outputC / inputC