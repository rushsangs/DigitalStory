error(3, 'Invalid Action Error: \n%s cannot throw %s unless they have seen %s before', [A,C,C]) :- 
  	action(A, throws, C),
  	\+ action(A, sees, C).

error(3, 'Invalid Action Error: \n%s cannot sell %s unless they possess %s', [A,C,C]) :-
	action(A, sells, C),
	\+ action(A, has, C).

error(2, 'Invalid Type Error: \n%s cannot visit entities that are not humans or locations', [A]) :-
	action(A, visits, B),
	\+ type(B, human),
	\+ type(B, location).

error(3, 'Invalid Action Error: \n%s fell from %s, but %s was never chopped down', [A, B, B]) :-
	action(A, fall, _),
	action(A, on, B),
	\+ action(_, chop, B).
error(3, 'Invalid Action Error: \n%s fell down, but %s is standing on solid ground', [A, A]) :-
	action(A, fall, _),
	\+ action(A, on, _).