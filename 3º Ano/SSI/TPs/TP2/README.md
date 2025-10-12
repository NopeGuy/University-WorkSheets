
# Sistema de Mensagens

Este projecto consiste num sistema de mensagens simples que inclui um cliente e um daemon para a gestão de mensagens e grupos.

## Estrutura do Projecto

- **src/**: Contém os ficheiros fonte `client.c`, `daemon.c`, `group.c`, `message.c`.
- **include/**: Contém os cabeçalhos `client.h`, `daemon.h`, `group.h`, `message.h`.
- **Makefile**: Automatiza a compilação dos componentes do projecto.

## Dependências

O projecto é compilado usando GCC e requer as seguintes ferramentas:

- GCC
- Make

## Compilação

Use o `Makefile` fornecido para compilar os programas. No directório raiz do projecto, execute:

```bash
make
```

Isto compilará o cliente e o daemon, limpará as compilações anteriores e gerará os executáveis `client` e `daemon`.

## Uso

Após a compilação, execute os programas da seguinte maneira:

```bash
./client
./daemon
```

Estes comandos iniciam o cliente e o daemon do sistema de mensagens, respectivamente.
É só necessária a inicialização de um daemon para o número de clientes que quiser.

## Preparação para o uso do programa

### Adicionar e Remover Utilizadores em Linux

Para ser possível utilizar o nosso software, é necessário antes criar vários utilizadores no OS de modo a que seja possível conversar entre eles.

Para adicionar um utilizador em sistemas Linux:

```bash
sudo adduser novo_utilizador
```

Para excluir um utilizador:

```bash
sudo deluser novo_utilizador
```

Para remover um utilizador e o seu directório home:

```bash
sudo deluser --remove-home novo_utilizador
```

---
## Uso do Programa

Após compilar e iniciar o daemon e os clientes, você terá acesso aos seguintes comandos através da console do `client`:

### Gestão de Mensagens:

- `list`: Lista mensagens não lidas.
- `list -a`: Lista todas as mensagens.
- `write group <nome_do_grupo> <mensagem>`: Envia uma mensagem para um grupo.
- `write user <nome_do_utilizador> <mensagem>`: Envia uma mensagem para um usuário.
- `read <id>`: Lê mensagens.
- `answer <id> <mensagem>`: Responde a uma mensagem.
- `delete <id>`: Deleta uma mensagem.

### Gestão de Grupos:

- `group create <nome_do_grupo>`: Cria um grupo.
- `group add <nome_do_grupo> <utilizador>`: Adiciona um usuário ao grupo.
- `group remove <nome_do_grupo> <utilizador>`: Remove um usuário do grupo.
- `group delete <nome_do_grupo>`: Deleta um grupo.
- `group list <nome_do_grupo>`: Lista todos os usuários em um grupo.

### Outros Comandos:

- `exit`: Sai do programa.

Recorda-se de que é necessário inicializar apenas um daemon para o número de clientes desejado.
---