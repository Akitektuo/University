fill(Start, End, []) :- Start is End + 1.

fill(Start, End, [Start | ResultTail]) :-
    NewStart is Start + 1,
    fill(Start + 1, End, ResultTail).
