%ex 1
figure(1)
t = [0 1]
f1= @(x)(exp(10*x*(x-1))*sin(12*pi*x));
fplot(f1,t)
f2 = @(x)(3*exp(5*x.^2-1)*cos(12*pi*x));
fplot(f2,t)

%ex2 
a = 2
b = 3

t = [0, 10*pi]

fx = @(t) ((a + b) * cos(t) - b * cos((a / b + 1) * t));
fy = @(t) ((a + b) * sin(t) - b * sin((a / b + 1) * t));

fplot(fx, t)
fplot(fy, t)

% ex 3
figure(3)
t1 = [0 2*pi]
fplot(@(t)(cos(t)), t1)
hold on 
fplot(@(t)(sin(t)), t1) 
fplot(@(t)(cos(2*t)),t1)

% ex 4
figure(4)
f4 = @(t) t.^3 + sqrt(1-t) .* (t>=-1 & t<=0) + t.^3 - sqrt(1-t) .* (t>0 & t<=1)
fplot(f4)


% ex 5
t5 = [0 50]
figure(5)
f5 = @(t) t/2  .* (mod(t,2)==0) + 3*t+1 .* (mod(t,2)==1)
fplot(f5,t5)


%ex6 
function result = fraction(n)
   result = 0
   num = 2 
   for i = 1:n
     num = 1 + 1 / num 
   endfor
   result = num
end 

% ex 7
figure(7)
x7 = -2:0.01:2;
y7 = -4:0.01:4;
[X,Y] = meshgrid(x7,y7);
R = -((X-1/2).^2+(Y-1/2).^2);
Z = exp(R);
mesh(X,Y,Z)