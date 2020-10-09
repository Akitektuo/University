A = [1 0 -2; 2 1 3; 0 1 0];
B = [2 1 1; 1 0 -1; 1 1 0];
C = A - B;
D = A * B;
E = [];
##for i = 1:3;
##  for j = 1:3;
##    E(i,j) = A(i,j) * B(i,j);
##  endfor
##endfor
E = A.*B
C
D
E