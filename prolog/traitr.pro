trait(A, huge) :- trait(A, enormous).
trait(A, B) :- action(A, was, B), \+ type(B, human).