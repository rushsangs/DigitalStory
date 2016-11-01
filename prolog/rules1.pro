action(A, see, B) :- action(A, saw, B).
action(A, sees, B) :- action(A, saw, B).

action(A, meet, B) :- action(A, met, B).
action(A, meets, B) :- action(A, met, B).

action(A, know, B) :- action(A, met, B).
action(A, knows, B) :- action(A, met, B).

action(A, has, B) :- action(A, took, B).
action(A, have, B) :- action(A, took, B).

trait(A, huge) :- trait(A, enormous).
trait(A, B) :- action(A, was, B), \+ type(B, human).
