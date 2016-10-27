action(A, throw, B) :- action(A, sees, B), trait(A, angry), type(A, human).

action(A, chopped, B) :- action(A, has, axe), type(A, human).
