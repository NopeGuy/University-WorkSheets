% Definição das relações familiares
filho(joao, jose).
filho(jose, manuel).
filho(carlos, jose).
pai(paulo, filipe).
pai(paulo, maria).
avo(antonio, nadia).
neto(nuno, ana).

% Definição dos sexos
sexo(joao, masculino).
sexo(jose, masculino).
sexo(maria, feminino).
sexo(joana, feminino).

% Regras para determinar a relação pai-filho
pai_de(Pai, Filho) :- filho(Filho, Pai).

% Regras para determinar a relação avô-neto
avo_de(Avo, Neto) :- pai_de(Pai, Neto), pai_de(Avo, Pai).

% Regras para determinar a relação neto-avo
neto_de(Neto, Avo) :- avo_de(Avo, Neto).

% Regra para determinar descendência
descende_de(X, Y) :- filho(X, Y).
descende_de(X, Y) :- filho(X, Z), descende_de(Z, Y).

% Regra para determinar o grau de descendência
grau_de_descendencia(X, Y, N) :- filho(X, Y), N is 1.
grau_de_descendencia(X, Y, N) :- filho(X, Z), grau_de_descendencia(Z, Y, M), N is M + 1.

avo_de_por_grau(Avo, Neto) :- grau_de_descendencia(Avo, Neto, Grau), Grau = 2.

bisavo_de(X, Y) :- grau_de_descendencia(X, Y, Grau), Grau = 3.
trisavo_de(X, Y) :- grau_de_descendencia(X, Y, Grau), Grau = 4.
tetraneto_de(X, Y) :- grau_de_descendencia(X, Y, Grau), Grau = 5.
