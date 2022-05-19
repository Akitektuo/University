clear

function y = simpsons(f, a, b, n)
   h = (b-a)/n; 
   xi = a:h:b;
   len = length(xi);
   y = h/6*(f(a)+f(b)+4*sum(f((xi(1:len-1) + xi(2:len)) / 2))+2*sum(f(xi(2:len-1))));
endfunction

function y = trapezium(f, a, b, n)
    h = (b-a)/n;
    xi = a:h:b;
    len = length(xi);
    y = h/2*(f(a)+f(b)+2*sum(f(xi(2:len-1))));
endfunction

function J = double_simpsons(f,a,b,c,d,n)
 h = (b-a)/(2*n);
 j1=0;j2=0;j3=0;
 for i=0:2*n
   x = a + i*h;
   h1 = (d-c)/(2*n);
   k1 = f(x,c) + f(x,d);
   k2 = 0; k3=0;
   for j=1:2*n-1
     y = c+j*h1;
     z = f(x,y);
     if mod(j,2)==0
       k2=k2+z;
     else 
       k3=k3+z;
     endif
   endfor
   l = (k1+2*k2+4*k3)*h1/3;
   if i==0 || i==2*n 
     j1=j1+l;
   elseif mod(i,2)==0
     j2 = j2+l; 
   else
     j3=j3+l;
   endif
 endfor
 J = (j1+2*j2+4*j3)*h/3;
endfunction

function y = double_trapezium(f, a, b, c, d)
  y = (b - a)*(d-c)/16 * (f(a, c) + f(a, d) + f(b, c) + f(b, d) +
  2*f((a+b)/2, c) + 2*f((a+b)/2, d) + 2*f(a,(c+d)/2) + 2*f(b, (c+d)/2) + 4*f((a+b)/2, (c+d)/2));
endfunction