# LCC

## Teste 14 de Maio de 2024

1. Explique porque a invocação concorrente de operações que envolvam vário objetos pode causar problemas, mesmo que cada objeto se proteja individualmente com código de controlo de concorrência interno. Ilustre o problema com um exemplo e descreva uma técnica de controlo de concorrência que o resolva.
**R:** Explique porque a invocação concorrente de operações que envolvam vários objetos pode causar problemas, mesmo que cada objeto se proteja individualmente com código de controlo de concorrência interno. Ilustre o problema com um exemplo e descreva uma técnica de controlo de concorrência que o resolva.
Problema:

A invocação concorrente de operações envolvendo vários objetos pode causar problemas como deadlock e inconsistência de estado, mesmo quando cada objeto se protege individualmente com mecanismos de sincronização. O problema surge quando duas ou mais threads tentam adquirir locks em múltiplos objetos ao mesmo tempo.
Exemplo de Deadlock:

Considere duas threads que tentam transferir dinheiro entre duas contas bancárias AccountA e AccountB. Cada conta tem um lock para proteger suas operações de depósito e saque.

    Thread 1: Tenta transferir dinheiro de AccountA para AccountB.
        Adquire o lock de AccountA.
        Em seguida, tenta adquirir o lock de AccountB.

    Thread 2: Tenta transferir dinheiro de AccountB para AccountA.
        Adquire o lock de AccountB.
        Em seguida, tenta adquirir o lock de AccountA.

Se Thread 1 adquirir o lock de AccountA enquanto Thread 2 adquirir o lock de AccountB, ambas as threads ficarão bloqueadas esperando pelo lock que a outra possui, resultando em um deadlock.
Técnica de Controle de Concorrência:

Uma técnica para resolver esse problema é o uso de um protocolo de ordenação de locks, onde todos os locks são adquiridos em uma ordem global fixa.


2. Explique os motivos porque o await() numa Condition em Java deve ser efetuado com o Lock correspondente adiquirido; nomeadamento porque o comportamento do await() foi pensado de modo a não ser o programador a ter que libertar o Lock antes do await().
**R:** Explique os motivos porque o await() numa Condition em Java deve ser efetuado com o Lock correspondente adquirido; nomeadamente porque o comportamento do await() foi pensado de modo a não ser o programador a ter que libertar o Lock antes do await().
Motivos:

    Libertação Automática do Lock:
        Quando uma thread chama await(), ela libera automaticamente o lock associado à Condition. Isso é importante para permitir que outras threads possam adquirir o lock e proceder com suas operações, garantindo que a condição pela qual a thread inicial está esperando possa ser eventualmente satisfeita.

    Reaquisicão do Lock:
        Após ser sinalizada (signal ou signalAll), a thread que estava em await() deve reaquirir o lock antes de continuar a execução. Isso assegura que a thread retome a execução com exclusividade sobre o recurso protegido pelo lock, mantendo a consistência dos dados.

    Evitar Erros de Programação:
        Requerer que o await() seja chamado com o lock já adquirido evita a possibilidade de erros onde o programador esqueça de liberar o lock, o que poderia levar a deadlocks ou inconsistências de estado. Esse design simplifica o uso correto de Condition e Lock, promovendo práticas de codificação seguras e robustas.



## Exame 4 de Junho de 2024

1. Se estivermos a usar monitores, podemos ser tentados a resolver qualquer problema de sincronização com apenas uma variável de condição. Explique que problemas tem tal abordagem, e dê um exemplo em que será preferível usar mais do que uma variável de condição.
**R:** Consumidores esperam por produtores produzir (caso não haja mais o que consumir) e produtores esperam por consumidores consumir  (caso haja um limite de produção), visto que há duas condições é mais eficiente criar duas do que estar a acordar sempre todos os consumidores e produtores quando o consumidor consome e produtor produz.


# LEI

## Exame 15 de Julho de 2023

1. Compare as estratégias 1-thread-por-ligação e 1-thread-por-pedido do ponto de vista da eficiência.
**R:** Na estratégia de 1-thread-por-ligação, garantimos um sistema com baixo overhead, mas pode causar um desperdício de recursos, pois ao parar uma Thread podemos deixar o sistema inativo; enquanto que na 1-thread-por-pedido, obtemos um sistema de maior overhead, mas que explora paralelismo de tarefas (pois executa várias tarefas simultâneamente do mesmo cliente). Ambas as estratégias tem as suas utilidades, por exemplo, 1-thread-por-pedido pode ser útil quando temos vários clientes a fazer vários pedidos longos, enquanto que 1-thread-por-ligação seria mais útil em sistemas com pouco tráfego de rede.


2. Identifique a dificuldade principal na serialização de estruturas de dados ligadas e resuma a técnica genérica normalmente usada para a ultrapassar.
**R:** A dificuldade principal na serialização de estruturas de dados ligadas, é manter a integridade dos apontadores; já que, não é incomum que os dados acabem por ser distorcidos e causar ciclos infinitos, especialmente se diferentes apontadores estiverem a apontar para os mesmos dados. A maneira de evitar isto é atribuir um identificador único a cada apontador, garantindo a integridade dos dados.


3. Explique como é que o protocolo 2PC (two phase commit) resolve uma falha de um participante durante a segunda fase.
**R:** No protocolo 2PC durante a 2ª fase, caso aconteça algum tipo de erro durante a mesma o sistema pode recuperar da falha, fazendo com que o participante faça uma query ao coordenador sobre estado em que se encontrava antes da falha (que é salvaguardado na memória), como na 1ª fase o participante precisa de votou afirmativamente sobre o commit, então essa informação é sabida pelo coordenandor, que depois trato de reinformar o participante. Desta forma os participantes conseguem recuperar do erro e reiniciar o seu objetivo.


4. Considere um serviço de gestão de uma fila de espera semelhante ao descrito no grupo II mas assegurado por, em vez de um único, um grupo de servidores. Identifique aquele que lhe parece ser o problema de sistemas distribuídos mais importante neste caso e proponha, justificando, uma solução típica.
**R:** O problema trata-se na consistência dos dados entre os diferentes servidores, portanto, a melhor solução seria utilizar um algoritmo de consenso distribuido, como o de Paxos. Isto porque mesmo que hajam falhas em alguns servidores, o algoritmo de Paxos consegue fazer com que os restantes consigam recuperar e retrasnmitir a informação perdida. Mantendo a consistência dos dados do sistema distribuido e fazendo com que o sistema nunca pare com a falha de um ou qualquer servidor.
 




## Teste 4 Janeiro de 2024

1. Como é que o algoritmo de exclusão mútua de Lamport (padaria ou bakery) garante um processo que pretende entrar na secção crítica não é ultrapassado mais do que uma vez por qualquer outro processo? Dê exemplos.
**R:** O algoritmo da padaria de Lamport garante que um processo não seja ultrapassado mais de uma vez por qualquer outro através de um sistema de numeração semelhante a senhas de padaria. Cada processo pega um número maior que os números atualmente em uso, e os processos com números menores têm prioridade. Em caso de empate, o processo com identificador menor é escolhido. Isso assegura que os processos esperem sua vez de maneira justa, pois um processo só será ultrapassado por processos com números menores ou PIDs menores, garantindo que ele eventualmente entre na seção crítica. Assim, o algoritmo mantém uma ordem justa e evita ultrapassagens repetidas.


2. Considere o diagrama ao lado que representa os processos p1 a p5 que trocam mensagens enquanto usam relógios lógicos escalares com valores inicias de 5,1,1,2,2, respetivamente. Indique o valor do relógio para cada uma das mensagens (a)-(f), justificando.
**R:** a-5, b-1, c-7, d-2, e-3, f-1


3. Qual é a estratégia partilhada pela disseminação epidémica e pelo Chord DHT para serem viáveis com um número muito grande de participantes? Justifique.
**R:** Ambas são viáveis para um grande número de participantes devido ao fato de serem descentralizadas, o que significa que não há um ponto único de falha. No caso da disseminação epidêmica, cada nó transmite a informação apenas para um subconjunto de nós vizinhos, que por sua vez retransmitem para outros nós, garantindo que a informação se propague eficientemente sem que cada nó precise conhecer todos os outros. No caso do Chord DHT, a descentralização é alcançada através da organização dos nós em um anel, onde cada nó mantém informações apenas sobre um pequeno número de outros nós (seus sucessores e predecessores), facilitando a localização de dados através de saltos logarítmicos no anel.


4. Considere uma agência de viagens em que o utilizador pode reservar transportes e estadias nos sistemas de diferentes transportadoras e hoteis alojados num mesmo centro de dados. Como podemos num sistema distribuído garantir que o utilizador reserva exatamente o programa completo? E se os sistemas em questão estiverem em centros de dados distantes?
**R:** Para garantir que o utilizador reserva exatamente o programa completo (voo e hotel), é necessário utilizar um protocolo que assegure a atomicidade das operações distribuídas, como a Two-Phase Commit (2PC) . No caso da 2PC, um coordenador inicializa a transação e solicita a todos os participantes que preparem a reserva. Se todos os participantes confirmarem que podem realizar a reserva, o coordenador envia um comando de commit para todos, assegurando que todas as reservas são efetuadas ou nenhuma é realizada.


## Exame 24 de Janeiro 2024

1. "Não posso utilizar o algoritmo de Peterson num programa com muitos threads porque vai ficar bloqueado." Discuta esta afirmação.
**R:** O algoritmo de Peterson é desenhado de forma a fornecer exclusão mútua entre dois processos escolhendo um como sua "vítima" e parando a sua execução. Devido a esse detalhe ele não cumpre o seu objetivo quando aplicado a mais de duas threads pois só bloqueia a sua vítima deixando todas as outras executarem, não criando necessáriamente bloqueios num programa mas não realiza a sua função de exclusão mútua, podendo criar dados incorretos (devido a race conditions como várias threads acederem às mesmas variáveis) ou até deadlocks.


2. Justifique a migração de código do servidor para o cliente em sistemas distribuídos.
**R:** Com a migração de código de servidor para cliente num sistema distribuído, é possível reduzir a carga do servidor aumentando a sua eficiência de recursos e a velocidade na resposta de pedidos pois não há também tantas comunicações entre os servidores e clientes diminuindo assim a latência, tornando também assim o sistema mais escalável e reduzindo a chance de falha ao não haver tanto código com chance de falha no mesmo. (Dar exemplos?)


3. Considere o algoritmo distribuído de exclusão mútua baseado em relógios lógicos (Ricart-Agrawala). Justifique, neste con- texto, uma vantagem e uma desvantagem da utilização de relógios lógicos.
**R:** Uma vantagem do uso de relógios lógicos no algoritmo de exclusão mútua de Ricart-Agrawala é a correta ordenação dos eventos, garantindo que todos os pedidos sejam processados numa ordem consistente sem a necessidade de sincronização de relógios físicos. Isso permite a utilização de lock sem a espera de um round-trip em certos casos, pois processos atrasados podem realizar suas tarefas sem esperar por outros que tenham seu relógio lógico mais avançado. Uma desvantagem é que o algoritmo é susceptível a falhas, pois a paragem de um único participante pode causar o bloqueio no algoritmo e a paragem do sistema.


4. Um sistema de gestão de cloud computing como o Kubernetes tem uma arquitetura cliente-servidor e coloca máquinas virtuais (pedidos) a máquinas físicas (reursos) dependendo dos disponibilidades e necessidades (memória, CPUs, discos, etc). Qual lhe parece ser o problema de sistemas distribuídos principal para garantir que este serviço está sempre disponível, mesmo quando as máquinas físicas podem falhar? Proponha uma solução apoiada nos algoritmos estudados, justificando.
**R:** O problema é garantir a disponibilidade apesar das falhas em algumas das máquinas que compõem o sistema. A solução é a implementação de um algoritmo como o de Paxos, que garante que o sistema como um todo continua funcional mesmo com a falha de um ou vários componentes, através da replicação do estado do sistema entre vários nós, estando todos os nós cientes do estado dos outros e preparados para recuperar a carga perdida com eventuais falhas.