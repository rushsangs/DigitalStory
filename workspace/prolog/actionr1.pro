action(A, see, B) :- action(A, saw, B).
action(A, sees, B) :- action(A, saw, B).

action(A, meet, B) :- action(A, met, B).
action(A, meets, B) :- action(A, met, B).

action(A, know, B) :- action(A, met, B).
action(A, knows, B) :- action(A, met, B).
action(A, know, B) :- action(A, X, B), type(A, human).
action(A, knows, B) :- action(A, X, B), type(A, human).

action(A, has, B) :- action(A, took, B).
action(A, have, B) :- action(A, took, B).