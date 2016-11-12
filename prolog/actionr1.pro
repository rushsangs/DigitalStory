action(A, see, B) :- action(A, saw, B).
action(A, sees, B) :- action(A, saw, B).

action(A, meet, B) :- action(A, met, B).
action(A, meets, B) :- action(A, met, B).

action(A, know, B) :- action(A, met, B).
action(A, knows, B) :- action(A, met, B).
action(A, know, B) :- action(A, _, B), type(A, human).
action(A, knows, B) :- action(A, _, B), type(A, human).

action(A, chop, B) :- action(A, chops, B).
action(A, chop, B) :- action(A, chopped, B).

action(A, have, B) :- action(A, has, B).

action(A, owns, B) :- action(A, owned, B).
action(A, owns, B) :- action(A, own, B).

action(A, gets, B) :- action(A, got, B).
action(A, gets, B) :- action(A, get, B).

action(A, sells, B) :- action(A, sold, B).
action(A, sells, B) :- action(A, sell, B).

action(A, visits, B) :- action(A, visited, B).
action(A, visits, B) :- action(A, visit, B).

action(A, throws, B) :- action(A, threw, B).
action(A, throws, B) :- action(A, throw, B).

action(A, has, B) :- action(A, took, B).
action(A, has, B) :- action(A, owns, B).
action(A, has, B) :- action(A, gets, B).

action(A, possess, B) :- action(A, have, B).