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
	
error(3, 'Invalid Action Error: \n%s borrowed %s, but %s did not visit anyone who has %s', [A, B, A, B]) :-
    action(A, borrow, B),
    \+ (action(A, visit, C), action(C, has, B)).
    
:- discontiguous action/3.
action(alice, has, ingredients).
:- discontiguous error/3.
error(3, 'Invalid Action Error: \nalice is baking a cake, but alice does not own all the ingredients she needs', []) :-
    action(alice, bake, cake),
    (action(alice, need, X), \+ action(alice, has, X)).