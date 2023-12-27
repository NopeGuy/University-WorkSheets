soma(X,Y,Z,S):- S is X+Y+Z.

soma([],0).
soma([H|T],S):- soma(T,S1), S is H+S1.

maior(X,Y,X):- X>Y.
maior(X,Y,Y).

quantos( [],0 ).
quantos ( [X|L],N ) :-
    pertence( X,L),
    quantos( L,N ),
quantos ( [X|L],N1 ) :-
    nao( pertence( X,L ) ),
    quantos( L,N ), N1 is N+1.

%-------------------
% Extensao do predicado apagar: Elemento, Lista, Resultado -> {V,F}

apagar( X,[X|R],R ).
apagar( X,[Y|R],[Y|L] ) :- 
    X \= Y,
    apagar( X,R,L ).

%-------------------
% Extensao do predicado apagartudo: Elemento, Lista, Resultado -> {V,F}

apagartudo( X,[],[] ).
apagartudo( X,[X|R],L ) :- 
    apagartudo( X,R,L ).
apagartudo( X,[Y|R],[Y|L] ) :- 
    X \= Y,
    apagartudo( X,R,L ).

%-------------------
% Extensao do predicado adicionar: Elemento, Lista, Resultado -> {V,F}

adicionar( X,L,L ) :-
    pertence(X,L).
adicionar( X,L,[X|L] ) :-
    not( pertence(X,L) ).