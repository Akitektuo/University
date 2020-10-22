%fill(Start, End) {
%	[], Start = End + 1
%	Start (+) fill(Start + 1, End), otherwise
%}

%fill(Start: integer, End: integer, Result: list)
%(I,I,O)
%deterministic

fill(Start, End, []) :- Start is End + 1, !.

fill(Start, End, [Start | ResultTail]) :-
    NewStart is Start + 1,
    fill(NewStart, End, ResultTail).

%fill(1, 10, Result).