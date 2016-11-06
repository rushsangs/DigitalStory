action(they, were, poor).
action(they, had, cow).
action(mot, mother, told).
action(jack, met, man).
action(jack, gave, beans).
action(jack, took, beans).
action(mother, saw, beans).
action(mother, was, angry).
action(mother, threw, beans).
action(jack, looked, out).
action(jack, saw, castle).
action(jack, heard, voice).
action(giant, sat, down).
action(hen, laid, egg).
action(giant, was, asleep).
action(jack, jumped, out).
action(jack, took, hen).
action(jack, took, harp).
action(giant, woke, up).
action(giant, came, down).
action(mother, took, axe).
action(mother, chopped, beanstalk).
action(mother, chopped, down).
action(nobody, saw, jack).action(A, see, B) :- action(A, saw, B).
action(A, sees, B) :- action(A, saw, B).

action(A, meet, B) :- action(A, met, B).
action(A, meets, B) :- action(A, met, B).

action(A, know, B) :- action(A, met, B).
action(A, knows, B) :- action(A, met, B).
action(A, know, B) :- action(A, X, B), type(A, human).
action(A, knows, B) :- action(A, X, B), type(A, human).

action(A, has, B) :- action(A, took, B).
action(A, have, B) :- action(A, took, B).trait(beans, magic).
trait(morning, next).
trait(beanstalk, giant).
trait(castle, beautiful).
trait(giant, enormous).
trait(harp, golden).
trait(eggs, golden).trait(A, huge) :- trait(A, enormous).
trait(A, B) :- action(A, was, B), \+ type(B, human).type(they,).
type(poor,).
type(cow,).
type(mot,).
type(told,).
type(jack,human).
type(man,human).
type(beans,).
type(mother,human).
type(angry,).
type(morning,).
type(out,).
type(beanstalk,).
type(castle,).
type(voice,).
type(giant,human).
type(down,).
type(harp,).
type(hen,).
type(egg,).
type(asleep,).
type(up,).
type(axe,).
type(nobody,).
type(eggs,).