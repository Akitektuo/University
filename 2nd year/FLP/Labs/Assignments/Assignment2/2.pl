%append(l1...ln, k1...km) {
%	k1...km, n = 1
%	l1 (+) append(l2...ln, k1...km), otherwise
%}
%append(ListA: list, ListB: list, Result: list)
%(I,I,O)
%deterministic

append([], List, List) :- !.
append([Head | Tail], List, [Head | Result]) :-
	!,
	append(Tail, List, Result).


%invertList(l1...ln) {
%	[], n = 0
%	invertList(l2...ln) (+) l1, otherwise
%}
%invertList(Input:list, Output:list)
%(I,O)
%deterministic
invertList([], []) :- !.
invertList([Head | Tail], Result):-
	!,
    invertList(Tail, Output),
    append(Output, [Head], Result).


%sumInvertedLists(l1...ln, k1...km, Carry) {
%	[], n = 0 and m = 0,
%	((Carry + k1) mod 10) (+) sumInvertedLists([], k2...km, (Carry + k1) / 10), n = 0
%	((Carry + l1) mod 10) (+) sumInvertedLists(l2..ln, [], (Carry + l1) / 10), m = 0
%	((Carry + l1 + k1) mod 10) (+) sumInvertedLists(l2..ln, k2...km, (Carry + l1 + k1) / 10), otherwise 
%}
%sumInvertedLists(ListA: list, ListB: list, Carry: integer, Output: list)
%(I,I,I,O)
sumInvertedLists([], [], _, []) :- !.
sumInvertedLists([], [Head | Tail], Carry, Output):-
	!,
    NewCarry is (Carry + Head) // 10,
    sumInvertedLists([], Tail, NewCarry, NewOutput),
    Result is (Carry + Head) mod 10,
    Output = [Result | NewOutput].
sumInvertedLists([Head | Tail], [], Carry, Output):-
	!,
    NewCarry is (Carry + Head) // 10,
    sumInvertedLists(Tail, [], NewCarry, NewOutput),
    Result is (Carry + Head) mod 10,
    Output = [Result|NewOutput].
sumInvertedLists([Head1 | Tail1], [Head2 | Tail2], Carry, Output):-
	!,
    NewCarry is (Carry + Head1 + Head2) // 10,
    sumInvertedLists(Tail1, Tail2, NewCarry, NewOutput),
    Result is (Carry + Head1 + Head2) mod 10,
    Output = [Result | NewOutput].


%sumLists(l1...ln, k1...km) {
%	invertList(sumInvertedLists(invertList(l1...ln), invertList(k1...km), 0))
%}
%sumLists(ListA: list, ListB: list, Result: list)
%(I,I,O)
sumLists(ListA, listB, Result):-
	!,
    invertList(ListA, InvertedA),
    invertList(listB, InvertedB),
    sumInvertedLists(InvertedA, InvertedB, 0, Output),
    invertList(Output, Result).


%sublistsSum(l1...ln) {
%	[0], n = 0,
%	sumLists(l1, sublistsSum(l2...ln)), l1 is list
%	sublistsSum(l2...ln), otherwise
%}
%sublistsSum(List: list, Result: list)
%(I,O)
sublistsSum([], [0]) :- !.
sublistsSum([[SubHead | SubTail] | Tail], Result) :-
	!,
	sublistsSum(Tail, PreviousResult),
	sumLists(PreviousResult, [SubHead | SubTail], Result).
sublistsSum([_ | Tail], Result) :- sublistsSum(Tail, Result).