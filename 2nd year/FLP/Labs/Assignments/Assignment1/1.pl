%isIncluded(Element, l1...ln) = {
%	false, n = 0
%	true, Element = l1
%	isIncluded(Element, l2..ln), otherwise
%}
  
%isIncluded(Element: element, ListL list)
%(I, I)
%deterministic

isIncluded(Element, [Element | _]) :- !.
isIncluded(Element, [_ | Tail]) :- isIncluded(Element, Tail).

%intersect(l1...ln, q1...qm) = {
%	[], n = 0
%	l1 (+) intersect(l2...ln, q1...qm), isIncluded(l1, q1...qm)
%	intersect(l2...ln, q1...qm), otherwise
%}

%intersect(List1: list, List2: list, Result: list)
%(I, I, O)
%deterministic

intersect([], _, []) :- !.
intersect([ListAHead | ListATail], ListB, [ListAHead | ResultTail]) :-
    isIncluded(ListAHead, ListB), !, intersect(ListATail, ListB, ResultTail).
intersect([_ | ListATail], ListB, Result) :-
    intersect(ListATail, ListB, Result).

%intersect([1, 3, 2, 7, 6], [2, 4, 7, 1], Result).