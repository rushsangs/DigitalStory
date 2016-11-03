error('%s cannot throw %s unless they have seen %s before', [A,C,C], action, A, B, C) :- 
	action(A, B, C),
	action(A, throws, C),
	\+ action(A, sees, C).