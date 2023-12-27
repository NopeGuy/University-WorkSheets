
aluno(1,joao,m).
aluno(2,antonio,m).
aluno(3,carlos,m).
aluno(4,luisa,f).
aluno(5,maria,f).
aluno(6,isabel,f).

curso(1,lei).
curso(2,miei).
curso(3,lcc).

%disciplina(cod,sigla,ano,curso)
disciplina(1,ed,2,1).
disciplina(2,ia,3,1).
disciplina(3,fp,1,2).

%inscrito(aluno,disciplina)
inscrito(1,1).
inscrito(1,2).
inscrito(5,3).
inscrito(5,5).
inscrito(2,5).

%nota(aluno,disciplina,nota)
nota(1,1,15).
nota(1,2,16).
nota(1,5,20).
nota(2,5,10).
nota(3,5,8).

%copia
copia(1,2).
copia(2,3).
copia(3,4).


%------------
% alinea  i)

alunosInscritos(Aluno) :- aluno(Numero, Aluno, _), not(inscrito(Numero, _)).

naoInscritos(Aluno) :- findall(Aluno, alunosInscritos(Aluno), L).

%------------
% alinea ii)

concatenar( [], L2, L2).
concatenar( [X|R],L2,[X|L]) :-
            concatenar(R,L2,L).

disciplinaNaoExiste(Aluno) :- aluno(Numero, Aluno, _), inscrito(Numero, D), not(disciplina(D, _, _, _)).

naoInscrito2(S) :- findall(Aluno, disciplinaNaoExiste(Aluno), L), naoInscritos(R), concatenar(L, R, S).

%------------
% alinea iii)

mediaAluno(Aluno, Media) :- findall(Nota, nota(Aluno, _, Nota), Notas), length(Notas, N), sumlist(Notas, S), Media is S / N.

%------------
% alinea iv)

lista_acima_media(M,R) :- findall(Aluno, (nota(Aluno, _, Nota), Nota >= M), R).
acimamedia(Aluno) :- findall(N,nota,(_,_,N),L), media(L,M), lista_acima_media(M,aluno).


%------------
% alinea v)

copiaram(Nome) :- aluno(Numero, Nome, _), copia(_,Numero).
todosC(L) :- findall(Nome,copiaram(Nome), L).

%------------
% alinea vi)

copiou(X,Y) :- copia(X,Y).
copiou(X,Y) :- copia(A,Y), copiou(X,A). 

%------------
% alinea vii)

maptolist([],[]).
maptolist([H|T],[N|T1]) :- aluno(H,N,_), maptolist(T,T1); maptolist(T,T1).