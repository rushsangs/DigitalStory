error(3, 'Invalid Action Error: \n%s cannot throw %s unless they have seen %s before', [A,C,C]) :- 
	action(A, throws, C),
	\+ action(A, sees, C).