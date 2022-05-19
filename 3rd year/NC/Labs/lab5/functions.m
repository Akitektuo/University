clear;

function [res] = hermitePol(interpolationVector, functionCalc, derivative, aprox)
    l = length(interpolationVector);
    
    iv = zeros(1, 2*l);
    iv(1:2:end) = interpolationVector;
    iv(2:2:end) = interpolationVector;

    fc = zeros(1, 2*l);
    fc(1:2:end) = functionCalc;
    fc(2:2:end) = functionCalc;

    dividedDiffTable = zeros(2*l, 2*l);
    dividedDiffTable(:,1) = fc';
    dividedDiffTable(1:2:end,2) = derivative';
    dividedDiffTable(2:2:2*l-1,2) = (functionCalc(2:l)-functionCalc(1:l-1))./(interpolationVector(2:l)-interpolationVector(1:l-1));
    for k=3:2*l
        for i=1:2*l-k+1
            dividedDiffTable(i,k) = (dividedDiffTable(i+1,k-1)-dividedDiffTable(i,k-1))/(iv(i+k-1)-iv(i));
        endfor
    endfor

    lAprox = length(aprox);
    p = ones(lAprox,1);
    s = dividedDiffTable(1,1) * ones(lAprox,1);
    for j=1:lAprox
        for i=1:2*l-1
            p(j) = p(j) * (aprox(j)-iv(i));
            s(j) = s(j) + p(j) * dividedDiffTable(1,i+1);
        endfor
    endfor

    res = s';
endfunction