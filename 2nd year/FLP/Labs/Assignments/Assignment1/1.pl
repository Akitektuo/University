isIncluded(Element, [Element | _]).
isIncluded(Element, [_ | Tail]) :- isIncluded(Element, Tail).

intersect([], _, []).
intersect([ListAHead | ListATail], ListB, Result) :-
    \+ isIncluded(ListAHead, ListB), intersect(ListATail, ListB, Result).
intersect([ListAHead | ListATail], ListB, [ListAHead | ResultTail]) :-
    isIncluded(ListAHead, ListB), intersect(ListATail, ListB, ResultTail).






