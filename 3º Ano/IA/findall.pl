homem(joao).
homem(pedro).
homem(miguel).
mulher(ana).
mulher(maria).
gosta(joao, vinho).
gosta(pedro, cerveja).
gosta(miguel, vinho).
gosta(ana, vinho).
gosta(maria, cerveja).

?- findall(Pessoa, (gosta(Pessoa, vinho), homem(Pessoa)), Lista).

Lista = [joao, miguel, ana].


livro(aventura, harry_potter, 4.5).
livro(aventura, senhor_dos_aneis, 4.8).
livro(ficcao_cientifica, fundacao, 4.2).
livro(ficcao_cientifica, neuromancer, 4.6).
livro(misterio, sherlock_holmes, 4.7).
livro(misterio, codigo_da_vinci, 4.4).

?- findall(Livro, (livro(_, Livro, Avaliacao), Avaliacao > 4.5), Livros_Boa_Avaliacao).
