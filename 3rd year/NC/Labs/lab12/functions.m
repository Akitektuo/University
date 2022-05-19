clear;

function ret = newton(f, fd, x0, maxIter, err)
  for n = 1:maxIter
    x1 = x0 - f(x0) / fd(x0)
    if abs(x1 - x0) <= err
      if maxIter!=6
        n
      endif
      break
    end
    x0 = x1;
  end
  if maxIter==6
    maxIter
  endif
  ret = x0
end

function ret = secant(f, x0, x1, maxIter, err)
  for n = 1:maxIter
    x = x1 - (x1 - x0) / (f(x1) - f(x0)) * f(x1);
    if abs(x1 - x0) <= err   
      n-1
      break
    end
    x0 = x1;
    x1 = x;
  end
  ret = x1
end

function ret = bisect(f, a, b, err)
  if f(a)*f(b)<0
    for n = 1:1000
      c = (a + b) ./ 2;
      if abs(a - b)/a < err
        n-2
        break
      end
      if f(a) * f(c) <= 0
        b = c;
      else
        a = c;
      end
    end
    ret = a
   else 
    disp('does not converge')
    endif
end

function ret = false_position(f, a, b, err)
  for n = 1:1000
    c = (a * f(b) - b * f(a)) / (f(b) - f(a));
    if abs(f(c)) < err
      n
      break
    end
    if f(a) * f(c) <= 0
      b = c;
    else
      a = c;
    end
  end
  ret = c
end