# Sistema de Comunicação Criptografada

## Descrição

Este sistema implementa um protocolo de comunicação segura entre um cliente e um servidor, utilizando criptografia avançada. O objetivo é garantir a confidencialidade, integridade e autenticação das mensagens trocadas.

## Componentes

- `msg_client.py`: Responsável pela iniciação da comunicação, geração de chaves, encriptação e envio de mensagens ao servidor.
- `msg_server.py`: Aguarda por conexões de clientes, desencripta as mensagens recebidas e processa de acordo com a lógica de negócios.

## Cliente de Comunicação

O cliente de comunicação implementado (`msg_client.py`) é responsável por estabelecer uma conexão segura com o servidor, trocar certificados digitais, enviar e receber mensagens de forma criptografada. Aqui estão alguns aspectos importantes do cliente:

- **Validação de Certificados**: O cliente valida os certificados digitais recebidos do servidor e rejeita a conexão se o certificado do servidor não for considerado válido.

- **Processamento de Mensagens**: O cliente processa mensagens recebidas do servidor e permite ao utilizador enviar novas mensagens. Ele criptografa as mensagens antes de enviá-las e descriptografa as mensagens recebidas do servidor.

- **Funcionalidade de Ajuda**: O cliente possui uma funcionalidade de ajuda que fornece instruções de uso do programa para o utilizador.

- **Gestão de Certificados**: O cliente carrega o seu próprio certificado e chave privada de um ficheiro P12 (`userdata.p12` por padrão), que são utilizados para autenticação com o servidor.

- **Comunicação Assíncrona**: O cliente utiliza a biblioteca `asyncio` para realizar operações de I/O de forma assíncrona, permitindo uma comunicação eficiente com o servidor.

- **Encerramento de Conexão**: Após a troca de mensagens, o cliente encerra a conexão com o servidor de forma adequada.

## Servidor de Comunicação

O servidor de comunicação (`msg_server.py`) é responsável por aguardar conexões de clientes, validar certificados digitais, processar mensagens recebidas e enviar respostas criptografadas. Abaixo estão alguns aspectos-chave do servidor:

- **Validação de Certificados**: Antes de aceitar uma conexão de cliente, o servidor valida o certificado digital fornecido pelo cliente, garantindo a autenticidade do mesmo.

- **Processamento de Mensagens**: O servidor processa as mensagens recebidas dos clientes, executando a lógica de negócios necessária e enviando respostas apropriadas. Ele também mantém um registo das mensagens recebidas e processadas.

- **Comunicação Assíncrona**: Assim como o cliente, o servidor utiliza a biblioteca `asyncio` para realizar operações de I/O de forma assíncrona, garantindo uma comunicação eficiente com os clientes.

- **Encerramento de Conexão**: Após concluir a troca de mensagens com um cliente, o servidor encerra adequadamente a conexão, libertando recursos e preparando-se para aceitar novas conexões.

- **Armazenamento de Mensagens**: O servidor mantém uma lista de mensagens recebidas e processadas, permitindo consultas futuras e garantindo a entrega correta das mensagens aos destinatários.

- **Manipulação de Exceções**: O servidor implementa tratamento de exceções para lidar com possíveis erros durante a validação de certificados, operações criptográficas e comunicação com os clientes.

## Opções de Design

- **Criptografia Assimétrica**: Utilizada para encriptar e desencriptar as mensagens, garantindo que apenas o destinatário pretendido possa ler o conteúdo da mensagem.
- **Validação de Certificados**: Implementada para assegurar a autenticidade das partes envolvidas na comunicação.
- **Porta de Comunicação e Tamanho da Mensagem**: Definidos como 7777 e 9999 bytes respectivamente, para padronizar a comunicação.

## Trabalho Realizado

- Implementação do protocolo de comunicação utilizando a biblioteca `cryptography.hazmat` para as operações criptográficas.
- Estabelecimento de uma conexão segura através da validação de certificados digitais.
- Manipulação de exceções para lidar com erros de criptografia e validação.

## Segurança

- O sistema foi desenhado com foco na segurança das mensagens, utilizando práticas recomendadas de criptografia.
- Cada mensagem é criptografada antes do envio e descriptografada apenas pelo destinatário, garantindo a confidencialidade.

## Requisitos

- Python 3.x
- Biblioteca `cryptography`

## Como Usar

1. Inicie o servidor executando `python msg_server.py`.
2. Conecte o cliente ao servidor executando `python msg_client.py` com os argumentos necessários.

## Conclusão

Este sistema oferece uma solução robusta para comunicação segura entre um cliente e um servidor, incorporando práticas avançadas de criptografia para proteger as informações trocadas.
