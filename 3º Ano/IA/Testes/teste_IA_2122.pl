% Exercicio 1

% a)

aviao("B737").
aviao("A330").
aviao("A320").
aviao("A319").
aviao("A340").
aviao("A321").
aviao("E190").

companhia("Ryanair").
companhia("Tap").
companhia("EasyJet").
companhia("Emirates").
companhia("Azul").

% b)

aviaoCompanhia("B737", "Ryanair").
aviaoCompanhia("A330", "Tap").
aviaoCompanhia("A320", "EasyJet").
aviaoCompanhia("A319", "EasyJet").
aviaoCompanhia("A340", "Tap").
aviaoCompanhia("A340", "Ryanair").
aviaoCompanhia("E190", "Azul").

% c)

% c)

:- dynamic aviao/2.

novo_aviao(Nome, Companhia) :-
    \+ aviao(Nome, Companhia), % Verifica se o avião não existe
    assertz(aviao(Nome, Companhia)).

remover_aviao(Nome) :-
    (viagem(_, _, _, _, _, Nome)
     -> fail
     ; retract(aviao(Nome, _))
     ).
