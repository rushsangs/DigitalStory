action(A, throw, B) :- action(A, sees, B), trait(A, angry), type(A, human).
action(A, throws, B) :- action(A, sees, B), trait(A, angry), type(A, human).
action(A, threw, B) :- action(A, sees, B), trait(A, angry), type(A, human).

action(A, chopped, B) :- action(A, has, axe), type(A, human).
action(A, chops, B) :- action(A, has, axe), type(A, human).
action(A, chop, B) :- action(A, has, axe), type(A, human).
