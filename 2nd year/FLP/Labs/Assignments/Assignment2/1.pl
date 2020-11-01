%sum(l1l2) {
% l1 + l2
%}

%sum(List: list, Result: integer)
%(I,O)
%deterministic

sum([First, Second | _], Sum) :- Sum is First + Second.