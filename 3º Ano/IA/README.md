# Resumos Teóricos

## Ambientes e espaço observável

- Ambiente determinístico, totalmente observável → **problema do estado único**
  - O agente “sabe” exatamente o estado em que estará
  - A solução é uma sequência.

- Ambiente determinístico, não acessível → **problema de múltiplos estados**
  - O agente “não sabe” onde está
  - A solução é uma sequência

- Ambiente não determinístico e/ou parcialmente acessível → **problema de contingência**
  - Perceções fornecem novas informações sobre o estado atual
  - Frequentemente intercalam procura e execução

- Espaço de estados desconhecido → **problema de exploração**

- estado unico: sabes os efeitos das ações e o estado atual
- multiplos estados: sabes os efetitos das ações mas nao sabes o estado atual
- contigencia: não sabes os efeitos das ações mas sabes o estado atual
- exploraçao: nao sabes os efeitos das ações nem sabes o estado atual

---

## Tipos de Estratégias de Procura:

#### Procura Não-Informada (cega)
- Usam apenas as informações disponíveis na definição do problema
  - Primeiro em Largura (BFS)
  - Primeiro em Profundidade (DFS)
  - Custo Uniforme
  - Iterativa
  - Bidirecional

#### Procura Informada (heurística)
- Dá-se ao algoritmo “dicas” sobre a adequação de diferentes estados.
  - Gulosa
  - Algoritmo A*

---

## Definições de ambientes:

### Agentes:
- Normalmente, têm apenas um controlo parcial sobre o ambiente, no sentido de que o podem influenciar pelas suas ações;
- Têm de decidir qual a ação a executar para atingir os seus objetivos;
- Podem realizar apenas algumas das suas ações, dependendo da estado do ambiente (condições prévias).

### Informação Perfeita e Imperfeita
- Jogos como Xadrez, Damas, Monopólio são jogos de informação perfeita pois toda a informação relativamente ao estado do jogo está presente para o agente.
- Jogos de informação imperfeita são jogos em que todos os elementos não são sabidos pelos jogadores como Poker, Scrabble, etc.

### Determinístico
- Um ambiente determinístico é aquele em que qualquer ação tem um único efeito garantido;
- O mundo real é, para nós humanos, não determinístico/estocástico. ( Jogos não determinísticos podem ser também considerados jogos de "sorte").

Exemplo:

|                           | Determinístico              | Estocástico             |
|---------------------------|-----------------------------|-------------------------|
| Informação perfeita (totalmente observável) | Xadrez, Go, Damas           | Gamão, Monopólio        |
| Informação imperfeita (parcialmente observável) | Batalha Naval               | Scrabble, Poker, Bridge |

### Acessibilidade
- Um ambiente acessível é aquele em que o agente pode obter informações completas, exatas e atualizadas sobre o estado do ambiente;
- Os ambientes mais moderadamente complexos são inacessíveis.

### Estático VS Dinâmico
- Num ambiente estático, o mundo não se altera enquanto o agente está a deliberar (atuar);
- Um ambiente não estático é dito dinâmico.

### Discreto VS Contínuo
- Num ambiente discreto, existe um número fixo e finito de ações e perceções possíveis;
- Um ambiente não discreto é dito contínuo.

### Episódico
- Num ambiente episódico, o tempo de execução do agente pode ser dividido numa série de intervalos (episódios) que são independentes uns dos outros, no sentido em que o que acontece num episódio não tem qualquer influência sobre os outros episódios

---

## Estratégias de Procura:

### Procura Primeiro em Largura (Breadth-first Search/BFS)
- Estratégia: Todos os nós de menor profundidade são expandidos primeiro
- Bom: Procura muito sistemática
- Mau: Normalmente demora muito tempo e sobretudo ocupa muito espaço

**Propriedades:**
- Completa: Sim, se b (fator de ramificação) for finito
- Tempo: supondo fator de ramificação b então n=1+b+b2+b3+ … +bn = O(bd) é exponencial em d
- Espaço: Guarda cada nó em memória O(bd)
- Ótima: Sim, se o custo de cada passo for 1
  - Em geral só pequenos problemas podem ser resolvidos assim!

### Procura Primeiro em Profundidade (Depth-first Search/DFS)
- Estratégia: Expandir sempre um dos nós mais profundos da árvore
- Bom: Muito pouca memória necessária, bom para problemas com muita soluções
- Mau: Não pode ser usada em árvores com profundidade infinita, pode ficar presa em ramos errados

**Propriedades:**
- Completa: Não, falha em espaços de profundidade infinita, com repetições (loops)
  - Modifique para evitar estados repetidos ao longo do caminho
- Tempo: O(bm), mau se m > d
- Espaço: O(bm), espaço linear
- Ótima: Não (em princípio devolve a 1ª solução que encontra)
  - Por vezes é definida uma profundidade limite (I) e transforma-se em Procura com Profundidade Limitada

### Procura de Custo Uniforme 
- Estratégia:
  - Para cada nó da lista de estados não expandidos, guardar o custo total do caminho do estado inicial para esse nó
  - Expandir sempre o nó com menor custo da lista de estados não expandidos (medido pela função de custo da solução)
- Procura Primeiro em Largura é igual a Procura de Custo Uniforme se g(N)=Depth(N)
- Equivalente a Procura Primeiro em Largura (Breadth-first search), se os custos forem todos iguais
- Implementação: lista de estados não expandidos é uma lista prioritária ordenada pelo custo do caminho
- Temos de garantir que g(sucessor)>= g(N)
- Equivalente ao algoritmo de Dijkstra em geral

**Propriedades:**
- Completa: Sim, se o custo da etapa for maior que alguma constante positiva ε (não queremos sequências infinitas de etapas com um custo total finito)
- Tempo:
  - Número de nós com custo de caminho ≤ custo da solução ideal (C *), O(bC */ ε)
  - Ipode ser maior que O (bd): a procura pode explorar caminhos longos que consistem em pequenos passos antes de explorar caminhos mais curtos que consistem em passos maiores
- Espaço: O(bC */ ε)
- Ótima: Sim 
  - (Em todos os nós N, g(N) é o custo conhecido de ir da raiz até ao nó N.)

### Procura de Iterativa (Aprofundamento Progressivo)
- Se não conhecermos o valor limite máximo, estaremos condenados a uma estratégia de procura em profundidade primeiro e temos que lidar com o problema de eventuais caminhos infinitos.
- A resposta passa pela alteração do principio da procura limitada fazendo variar esse limite entre zero e infinito.
- Usar Procura Primeiro em Profundidade (Depth-First Search) como uma sub-rotina
  - Verificar a raiz
  - Desenvolver um DFS procurando um caminho de comprimento 1
  - Se não houver um caminho de comprimento 1, desenvolver um DFS procurando um caminho de comprimento 2
  - Se não houver um caminho de comprimento 2, desenvolver um DFS procurando um caminho de comprimento 3…
  - Estratégia: Executar procura em profundidade limitada, iterativamente, aumentando sempre o limite da profundidade

**Propriedades:**
- Completa: Yes
- Tempo: (d+1)b0 + d b1 + (d-1)b2 + … + bd = O(bd)
- Espaço: O(bd)
- Ótima: Sim, se o custo for 1.
  - Em geral é a melhor estratégia (não-informada ou cega) para problemas com um grande espaço de procura e em que a profundidade da solução não é conhecida

### Procura Bidirecional
- Estratégia: Executar uma procura para a frente desde o estado inicial e para trás desde o objetivo, simultaneamente.
  - Bom: Pode reduzir enormemente a complexidade no tempo O(bd/2)
  - Problemas: Será possível gerar os predecessores? E se existirem muitos estados objetivo? Como fazer o “matching” entre as duas procuras? Que tipo de procura fazer nas duas metades?
  - Eg., Para encontrar uma rota na Romênia, existe apenas um estado objetivo, portanto, a procura para trás é muito parecida com a procura para a frente; Mas se o objetivo é uma descrição abstrata, como o de que "nenhuma rainha ataca outra rainha" no problema das rainhas n, a procura bidirecional é de difícil uso.

#### Legenda:
- b: o máximo fator de ramificação (o número máximo de sucessores de um nó) da árvore de procura
- d: a profundidade da melhor solução
- m: a máxima profundidade do espaço de estados

---

## Estratégias de Procura
- Procura informada (Heurística) utiliza informação sobre o problema para evitar que o algoritmo de procura fique “perdido vagueando no escuro”
- Estratégia de Procura definida através da escolha da ordem de expansão dos nós
- Procura do Melhor Primeiro (Best-First Search) utiliza uma função de avaliação que devolve um número indicando o interesse de expandir um nó
  - Procura Gulosa (Greedy-Search) – f(n) = h(n) (função que estima a distância à solução)
  - Algoritmo A* - f(n) = g(n) + h(n) (estima o custo da melhor solução que passa por n)
 
### Informada (Procura Gulosa – Greedy-Search)
- Estratégia: Expandir o nó que parece estar mais perto da solução
- h(n) = custo estimado do caminho mais curto do estado n para o objetivo (função heurística)

**Propriedades:**
- Completa? Não, pode entrar em ciclo.
  - Suscetível a falsos arranques
- Complexidade no tempo? O(bm)
  - Mas com uma boa função heurística pode diminuir consideravelmente
- Complexidade no espaço? O(bm)
  - Mantém todos os nós em memória
- Ótima? Não, porque não encontra sempre a solução ótima.
  - Necessário detetar estados repetidos.

### Informada (Procura A*)
- Estratégia:
  - evitar expandir caminhos que são dispendiosos
  - O algoritmo A* combina a procura gulosa com a uniforme, minimizando a soma do caminho já efetuado com o mínimo previsto do que falta até a solução. 
    Usa a função: f(n) = g(n) + h(n)
- g(n) = custo total, até agora, para chegar ao estado n (custo do percurso)
- h(n) = custo estimado para chegar ao objetivo (não deve sobrestimar o custo para chegar à solução (heurística))
- **f(n)= custo estimado da solução menos dispendiosa que passa pelo nó n**
- function A*-SEARCH(problem) returns a solution or failure
    return BEST-FIRST-SEARCH(problem,g+h)
- Algoritmo A* é ótimo e completo.
- Complexidade no tempo exponencial em (erro relativo de h* comprimento da solução)
- Complexidade no espaço: Mantém todos os nós em memória.

___________________
### Algoritmo Minimax

No algoritmo minimax, ao fazer a procura por um estado objetivo (um estado terminal que é a vitória), o jogador Max tentará encontrar as jogadas com as melhores probabilidades de vitória enquanto o Min encontrará as que têm a menor probabilidade do outro jogador vencer.

- O algoritmo MiniMax pode ser aplicado como estratégia de resolução neste tipo de
jogos (determinísticos, com dois adversários e informação perfeita) e consiste em:
  - Gerar a árvore completa até aos estados terminais
  - Aplicar a função utilidade a esses estados
  - Calcular os valores da utilidade até a raiz da árvore, uma camada de cada vez
  - Escolher o movimento com o valor mais elevado.
- Propriedades:
  - Completo? Sim, se a árvore for finita!
  - Ótimo? Sim, contra um adversário ótimo! Senão?
  - Complexidade no Tempo? O(bm)
  - Complexidade no Espaço? O(bm) (exploração primeiro em profundidade)
- Problema:
  - Inviável para qualquer jogo minimamente complexo
- Exemplo:
  - Para o xadrez (b=35, m=100), b^m=35^100=2.5* 10^154
  - Supondo que são analisadas 450 milhões de hipóteses por segundo => 2* 10^138 anos para chegar à solução!

### Algoritmo Minimax (Com prunning)
São removidas opções inviáveis da árvore fazendo possível calcular a decisão minimax exata sem expandir todos os nós na árvore do jogo.
Na parte de minimização do algoritmo são removidos os ramos com soluções maiores que a menor possível de modo a não serem percorridos e o inverso na de maximização.

___________________
## Procura e otimização

Até agora foi abordado neste resumo, na essência, uma única categoria de problemas: ambientes observáveis, determinísticos e conhecidos, onde a solução é uma sequência de ações.
Algoritmos que executam procura puramente local no espaço de estados, avaliando e modificando um ou mais estados atuais, em vez de explorar sistematicamente caminhos a partir de um estado inicial.
Esses algoritmos são adequados para problemas nos quais tudo o que importa é o estado da solução, não o custo do caminho para alcançá-lo.
A resolução de problemas complexos requer a obtenção das melhores soluções possíveis em tempo útil;
Para isso, ao invés da experimentação de todas as soluções possíveis (garantindo a solução ótima), é necessária a identificação de soluções o mais próximas quanto possível da solução ótima, num número limitado de tentativas;
É então, essencial um balanceamento adequado entre:
- Exploration: exploração geral do espaço de procura;
- Exploitation: procura focada nas zonas mais promissoras.
Este balanceamento é normalmente gerido com sucesso através de Meta-heurísticos.

**Uma meta-heurística é um método heurístico para resolver de forma genérica problemas de otimização;
Meta-heurísticas são geralmente aplicadas a problemas para os quais não se conhecem algoritmos eficientes aonde é utilizada uma combinação de escolhas aleatórias e de conhecimento histórico dos resultados anteriores adquiridos pelo método para se guiarem e realizar as suas procuras em vizinhanças dentro do espaço de procura, o que pode evitar ótimos locais**
- Pela sua componente aleatória, são não-determinísticos;
- Não garantem a identificação de :
- uma solução próxima;
- No menor tempo de execução;
- utilizando menos recursos computacionais que as técnicas tradicionais.


### Contínua vs Discreta

### Otimização Contínua
“encontrar os máximos e mínimos de funções, possivelmente sujeitos a restrições”;
“Na otimização contínua, as variáveis no modelo podem assumir qualquer valor dentro de um intervalo de valores, geralmente números reais. ...”.

### Otimização Discreta
“procurar minuciosamente para encontrar um item com propriedades especificadas entre um conjunto de itens”;
“Ao contrário da otimização contínua, as variáveis usadas (ou algumas delas) são restritas a assumir apenas valores discretos, como os inteiros.”.


## Algoritmos de melhoria iterativa

- Algoritmos Iterativos mantém um único estado (corrente) e tentam melhorá-lo.
- Algoritmos de Melhoria Iterativa:
  - Procura Subida da Colina (Hill-Climbing Search)
  - Arrefecimento Simulado (Simulated Annealing)
  - Procura Tabu (Tabu Search)
  - Algoritmos Genéticos (Genetic Algorithms)
  - Colónia de Formigas (Ant Colony Optimization)
  - Enxame de Partículas (Particle Swarm Optimization)
- Estratégia: Começar como uma solução inicial do problema e fazer alterações de forma a melhorar a sua qualidade
### Procura local vs procura global
- Algumas meta-heurísticas aplicam métodos de procura local, onde as novas soluções
  exploradas são “vizinhas” de soluções anteriores (e.g. Simulated Annealing, Tabu Search);
- Outras meta-heurísticas distribuem o processo de procura por todo o espaço de procura (normalmente através de abordagens baseadas em populações).

### Solução única vs Population-based
- As abordagens de solução única, são iterativas, e orientam o processo de procura através da melhoria da solução anterior;
- As abordagens baseadas em populações utilizam uma procura em paralelo por parte de vários membros da população, podendo, ou não, existir a troca de informação entre os indivíduos (e.g. Particle Swarm optimization, Genetic Algorithms, Ant Colony optmization).
### Exemplos de “Individual Based” (solução única)!
### - Hill-Climbing Search:
  - Escolher um estado aleatoriamente do espaço de estados
  - Considerar todos os vizinhos desse estado
  - Escolher o melhor vizinho
  - Repetir o processo até não existirem vizinhos melhores
  - O estado atual é a solução
- Estratégia:
  - Inicia-se num ponto aleatório X e avalia-se esse ponto;
  - Move-se desse ponto X original para um novo ponto Y vizinho do ponto X;
  - se esse novo ponto Y for uma solução melhor do que o ponto original X, fixa-se o ponto Y e inicia-se o processo
  novamente, porém caso seja inferior, volta-se para nosso ponto inicial X e tenta-se visitar um outro vizinho.

Hill Climbing é ótimo para encontrar boas soluções (mínimo/máximo locais) mas dificilmente vai encontrar a melhor solução (a menos que se tenha “sorte” na inicialização do ponto inicial).

### - Simulated Annealing:
  - Semelhante ao Hill-Climbing Search mas admite explorar vizinhos piores
  - Temperatura que vai sendo sucessivamente reduzida define a probabilidade de aceitar soluções piores

### - Tabu Search:
  - Semelhante ao Hill-Climbing Search, explora os estados vizinhos mas elimina os piores (vizinhos tabu)
  - Algoritmo determinístico

### Exemplos de “Population Based” 
Os algoritmos genéticos diferem dos algoritmos tradicionais de otimização em
basicamente quatro aspetos:
- baseiam-se numa codificação do conjunto das soluções possíveis, e não nos parâmetros da otimização em si;
- os resultados são apresentados como uma população de soluções e não como uma
  solução única;
- não necessitam de nenhum conhecimento derivado do problema, apenas de uma forma de avaliação do resultado;
- usam transições probabilísticas e não regras determinísticas.

- Procedimento iterativo que mantém uma população de estruturas
  candidatas a soluções, para domínios específicos;
- A cada incremento de tempo (geração), as estruturas da população atual
  são avaliadas na sua capacidade de serem soluções válidas para o
  domínio do problema;
- É constituída uma nova população de soluções candidatas, baseada na
  sua avaliação e pela aplicação de operadores genéticos.

**Algoritmos Genéticos**
- Definição do estado como um cromossomo
- Gerar soluções (cromossomos) a partir de uma população de estado inicial
- Reprodução, Mutação e Seleção

**Colónia de Formigas (Ant Colony)**
- Iniciando vários estados (colónia de formigas)
- A probabilidade de um caminho ser melhor é determinada pelo número de “formigas” que passam por ele

**Enxame de Partículas (Particle Swarm)**
- Vários estados de partida (enxame)
- A vizinhança é explorada e mantida, a melhor solução e o melhor estado
- “Partículas” caminham na direção da melhor solução encontrada até agora
- A velocidade do movimento depende das distâncias para a melhor solução e o melhor estado e a posição do estado

# Ver slide 10 / 2023.11.21 para ver especificações

