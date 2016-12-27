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

action(A, own, B) :- action(A, owned, B).
action(A, own, B) :- action(A, owns, B).

action(A, gets, B) :- action(A, got, B).
action(A, gets, B) :- action(A, get, B).

action(A, sells, B) :- action(A, sold, B).
action(A, sells, B) :- action(A, sell, B).

action(A, visit, B) :- action(A, visited, B).
action(A, visit, B) :- action(A, visits, B).

action(A, throws, B) :- action(A, threw, B).
action(A, throws, B) :- action(A, throw, B).

action(A, reach, B) :- action(A, reached, B).
action(A, reach, B) :- action(A, reaches, B).

action(A, climb, B) :- action(A, climbed, B).
action(A, climb, B) :- action(A, climbs, B).

action(A, descend, B) :- action(A, descended, B).
action(A, descend, B) :- action(A, descends, B).

action(A, plummet, _) :- action(A, plummeted-down, _).

action(A, borrow, B) :- action(A, borrowed, B).
action(A, borrow, B) :- action(A, borrows, B).

action(A, need, B) :- action(A, needed, B).
action(A, need, B) :- action(A, needs, B).

action(A, bake, B) :- action(A, baked, B).
action(A, bake, B) :- action(A, bakes, B).

action(A, has, B) :- action(A, took, B).
action(A, has, B) :- action(A, own, B).
action(A, has, B) :- action(A, gets, B).
action(A, has, B) :- action(A, borrow, B).

action(A, on, B) :- action(A, climb, B).
action(A, on, B) :- action(A, descend, B).

action(A, fall, _) :- action(A, plummet, _).

action(A, possess, B) :- action(A, have, B).

