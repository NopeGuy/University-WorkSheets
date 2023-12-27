%LICENCIATURA EM ENGENHARIA INFORMATICA
%MESTRADO integrado EM ENGENHARIA INFORMATICA

%Inteligencia Artificial
%2023/24

%Draft Ficha 8 - Exercicio 2


%biblioteca(id, nome, localidade)

biblioteca(1, uminhogeral, braga).
biblioteca(2, luciocracveiro, braga).
biblioteca(3, municipal, porto).
biblioteca(4, publica, viana).
biblioteca(5, ajuda, lisboa).
biblioteca(6, cidade, coimbra).


%livros( id, nome, biblioteca)

livros(1, gameofthrones, 1). 
livros(2, codigodavinci, 2).
livros(3, setimoselo, 1).
livros(4, fireblood, 4).
livros(5, harrypotter, 6).
livros(6, senhoradosneis, 7).
livros(7, oalgoritmomestre, 9).

%leitores(id, nome, genero)

leitores(1, pedro, m).
leitores(2, joao, m).
leitores(3, lucia, f).
leitores(4, sofia, f).
leitores(5, patricia, f).
leitores(6, diana, f).

%requisicoes(id_requisicao,id_leitor, id_livro, data(A,M,D)

requisicoes(1,2,3,data(2022,5,17)).
requisicoes(2,1,2,data(2022,7,10)).
requisicoes(3,1,3,data(2021,11,2)).
requisicoes(4,1,4,data(2022,2,1)).
requisicoes(5,5,3,data(2022,4,23)).
requisicoes(6,4,2,data(2021,3,9)).
requisicoes(7,4,1,data(2022,5,5)).
requisicoes(8,2,6,data(2021,7,18)).
requisicoes(9,5,7,data(2022,4,12)).


%devolucoes(id_requisicao, data(A,M, D))


devolucoes(2, data(2022, 7,26)).
devolucoes(4, data(2022,2,4)).
devolucoes(5, data(2022, 6, 13)).
devolucoes(1, data(2022, 5, 23)).
devolucoes(6, data(2022, 4, 9)).

%---- quantos leitores do sexo feminino existem - i)

leitores_femininos(R) :- findall(Genero, (leitores(_, _, Genero), Genero == f), L), length(L, R).

%--- quais os livros que foram requisitados sem que não se encontram em nenhuma biblioteca representada na base de conhecimento - ii)
livros_sem_biblio(R):- findall(Nome,(requisicoes(_,_,Livro,_), livros(Livro,Nome,Biblio), not(biblioteca(Biblio,_,_))),R).

%--- indique quais os livros e os respetivos leitores , que tiveram livros requisitados em braga - iii)

procura(Nome, Leitor) :- requisicoes(_,ID_leitor, Livro,_) , leitores(ID_leitor,Leitor,_), livros(Livro,Nome, Id), biblioteca(Id,_, Biblioteca), Biblioteca == braga.

requisicoes_braga(L) :- setof((Nome, Leitor), procura(Nome, Leitor) , L).


% ---- quais os livros que não foram requisitados , quer se encontrem ou não em alguma biblioteca  -- iv)

concatenar( [],L2,L2 ).
concatenar( [X|R],L2,[X|L] ) :- concatenar( R,L2,L ).

inexistentes_bibliotecas(R) :- findall(Livro, (livros(_,Livro,Id_Biblio), not(biblioteca(Id_Biblio,_,_))),R).

nao_requistados(R) :- findall(Livro, (biblioteca(Id_Biblio, _, _), livros(Id_livro,Livro,Id_Biblio), not(requisicoes(_,_, Id_livro, _))), R).

livros_nao_requisitados(R) :- inexistentes_bibliotecas(L), nao_requistados(S), concatenar(L,S,R).

%----  Quais os livros e a a respetiva data em que foram requeridos, no ano de 2022  -- v) 

ano_2022(Livro,data(A,M,D)) :- requisicoes(_,_, Id_livro, data(A,M,D)), livros(Id_livro, Livro, _), A == 2022.

livro_ano(R) :- findall((Livro, data(A,M,D)), ano_2022(Livro, data(A,M,D)), R). 

%--- Quais os leitores que requisitaram livros no verao (assuma que o verao e entre o mes de Julho e Setembro --- vi)


livros_verao(L) :-  findall(Leitores, (requisicoes(_,ID_leitor, _ , data(A,M,D)), leitores(ID_leitor, Leitores,_), M < 10 , M > 6), R), sort(R,L).


% -- quais os leitores que entregaram livros fora da data limite --- vii)

fora_limite(data(A1,M1,D1), data(A2,M2,D2)) :- A2 > A1, !; M2 > M1, !; D2 - D1 > 15.


fora_prazo(Leitor) :- devolucoes(ID_requisicao, data(AD,MD,DD)), requisicoes(ID_requisicao, ID_leitor, _ , data(AR,MR,DR)), leitores(ID_leitor, Leitor,_), fora_limite(data(AR,MR,DR), data(AD,MD,DD)).

atrasado(L) :- findall(Leitor, fora_prazo(Leitor), L).









