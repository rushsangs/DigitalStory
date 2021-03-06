action(jack, visited, market).
action(jack, met, man).

action(jack, owned, cow).
action(mother, owned, cow).

action(mother, asked, jack).


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

action(A, reach, B) :- action(A, reached, B).
action(A, reach, B) :- action(A, reaches, B).

action(A, climb, B) :- action(A, climbed, B).
action(A, climb, B) :- action(A, climbs, B).

action(A, descend, B) :- action(A, descended, B).
action(A, descend, B) :- action(A, descends, B).

action(A, plummet, _) :- action(A, plummeted-down, _).

action(A, has, B) :- action(A, took, B).
action(A, has, B) :- action(A, owns, B).
action(A, has, B) :- action(A, gets, B).

action(A, on, B) :- action(A, climb, B).
action(A, on, B) :- action(A, descend, B).

action(A, fall, _) :- action(A, plummet, _).

action(A, possess, B) :- action(A, have, B).
trait(boy, young).
trait(jack, boy).




trait(A, huge) :- trait(A, enormous).
trait(A, B) :- action(A, was, B), \+ type(B, human).
type(null,null).
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
