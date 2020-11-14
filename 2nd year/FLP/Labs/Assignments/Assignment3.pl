% 5. Two integers, n and m are given. 
% Write a predicate to determine all possible sequences of numbers from 1 to n, 
% such that between any two numbers from consecutive positions, 
% the absolute difference to be >= m.

% difference(a, b) =
% 	b - a, a < b
% 	a - b, a > b
% difference(A: int, B: int, Result: int)
% difference(I,I,O)

difference(A, B, Result) :-
    A < B,
    !,
    Result is B - A.
difference(A, B, Result) :-
    A > B,
    Result is A - B.

% generateSequences(Length, Index) =
% 	[], Index = Length + Index
% 	Index + generateSequences(Length, Index + 1), Index <= Length
% 	generateSequences(Length, Index + 1), Index <= Length
% generateSequences(Length: int, Index: int, Result: list)
% generateSequences(I,I,O)

generateSequences(Length, Index, []) :- Index =:= Length + 1, !.
generateSequences(Length, Index, [Index | Result]) :-
    Index =< Length,
    NewIndex is Index + 1,
    generateSequences(Length, NewIndex, Result).
generateSequences(Length, Index, Result) :-
    Index =< Length,
    NewIndex is Index + 1,
    generateSequences(Length, NewIndex, Result).

% isMinimumDifference(l1...ln, m) =
% 	true, diff(l1, l2) >= m and n = 2
% 	check(l2...ln, m), diff(l1, l2) >= m and n > 2
% 	false, otherwise
% isMinimumDifference(List: list, MinimumDifference: int)
% isMinimumDifference(I,I)

isMinimumDifference([Head1, Head2], MinimumDifference):-
    difference(Head1, Head2, Result),
    Result >= MinimumDifference, !.
isMinimumDifference([Head1, Head2 | Tail], MinimumDifference) :-
    difference(Head1, Head2, Result),
    Result >= MinimumDifference,
    isMinimumDifference([Head2 | Tail], MinimumDifference).

% generateSolution(Length: int, MinimumDifference: number, Result: list)
% generateSolution(I,I,O)

generateSolution(Length, MinimumDifference, Result) :-
    generateSequences(Length, 1, Result),
    isMinimumDifference(Result, MinimumDifference).

% generateAllSolutions(Length: int, MinimumDifference: int, Result: list)
% generateAllSolutions(I,I,O)

generateAllSolutions(Length, MinimumDifference, Result) :-
    findall(OneSolution, generateSolution(Length, MinimumDifference, OneSolution), Result).

%generateAllSolutions(5, 2, Results).