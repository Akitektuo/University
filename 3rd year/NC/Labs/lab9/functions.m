clear

function y = rectangle(a,b,f)
    y = (b-a) * f((a+b)/2);
endfunction

function y = repeated_rectangle(a,b,n,f)
    h = (b-a)/n;
    x1 = a + h/2;
    i = [2:n];
    xi = x1 + (i-1)*h;
    y = h * (f(x1) + sum(f(xi(1:end))));
endfunction

function y = romberg(a, b, f, k)
    h = b - a;
    if k == 0
        y = h/2*(f(a) + f(b));
    else
        q0 = romberg(a, b, f, k-1);
        coef = (2 * [1:2^(k-1)] - 1) ./ 2^k;
        s = sum(f(a + coef .* h));
        y = 1/2 * q0 + h/2^k*s;
    endif
endfunction

function y = simpsons(f, a, b, n)
    h = (b-a)/n; 
    xi = a:h:b;
    len = length(xi);
    y = (h/6).*(f(a)+f(b)+4*sum(f((xi(2:len) + xi(1:len-1)) ./ 2))+2*sum(f(xi(2:len-1))));
endfunction 

function y=adquad(a, b, f, err, n)
    n = 1;
    y1 = simpsons(f, a, b, n);
    y2 = simpsons(f, a, (a+b)./2, n) + simpsons(f, (a+b)./2, b, n);
    
    if abs(y1 - y2) < 15 .* err
        y = y2;
        return;
    else
        y = adquad(a, (a+b)./2, f, err./2,n) + adquad((a+b)./2, b, f, err./2,n);
    end
endfunction