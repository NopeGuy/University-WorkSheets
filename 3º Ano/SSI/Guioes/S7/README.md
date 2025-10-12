# Respostas das Questões

## Q1

Para verificar que as chaves fornecidas nos ficheiros mencionados (por exemplo, em `MSG_SERVER.key` e `MSG_SERVER.crt`) constituem um par de chaves RSA válido, podemos analisar os detalhes em ambos os ficheiros.

1. **Chave Privada** (`MSG_SERVER.key`):

   - O comando `openssl rsa -text -noout -in MSG_SERVER.key` revela que a chave privada é do tipo **RSA**, como indicado pela linha `RSA Private-Key: (2048 bit)`.
   - O campo "RSA Private-Key" exibe o módulo e os expoentes privados necessários para o funcionamento do algoritmo RSA.

2. **Certificado** (`MSG_SERVER.crt`):
   - O comando `openssl x509 -text -noout -in MSG_SERVER.crt` mostra que o certificado utiliza o algoritmo de assinatura "sha256WithRSAEncryption" e possui uma chave pública RSA de 2048 bits.
   - A secção "Subject Public Key Info" mostra os detalhes da chave pública, incluindo o módulo e o expoente público.
   - No campo "X509v3 extensions", são apresentadas as extensões do certificado, como as restrições de uso da chave.

**Comparação:**

- Podemos observar que tanto a chave privada quanto a chave pública no certificado são do tipo RSA e têm o mesmo tamanho de 2048 bits.
- Além disso, os valores do módulo e do expoente são coerentes entre a chave privada e a chave pública.

Portanto, com base na análise dos detalhes dos ficheiros, podemos afirmar que as chaves fornecidas nos ficheiros `MSG_SERVER.key` e `MSG_SERVER.crt` formam um par de chaves RSA válido.

## Q2

Visualizando o conteúdo dos certificados fornecidos, os campos que devem ser objecto de atenção no procedimento de verificação são os seguintes:

1. **Validade (Validity)**

   - **Not Before:** Data a partir da qual o certificado se torna válido.
   - **Not After:** Data limite até a qual o certificado é considerado válido.
   - **Verificação:** O certificado deve estar dentro do período de validade (entre "Not Before" e "Not After").

2. **Assunto (Subject)**

   - **Subject:** Contém informações sobre o titular do certificado, como organização, localidade e país.
   - **Verificação:** O nome do titular do certificado deve corresponder ao site ou serviço com o qual está a ocorrer a comunicação.

3. **Algoritmo de Assinatura (Signature Algorithm)**

   - Identifica o algoritmo criptográfico utilizado para assinar digitalmente o certificado.
   - **Verificação:** Verificar se o algoritmo utilizado é considerado seguro e confiável (ex.: RSA, SHA-256).

4. **Emissor (Issuer)**

   - Identifica a autoridade certificadora (CA) que emitiu o certificado.
   - **Verificação:** A CA emissora deve ser confiável e possuir boa reputação.
   - Deve ser confirmado se a CA está presente na lista de CAs confiáveis do _browser_ ou sistema operativo.

5. **Uso da Chave (Key Usage)**

   - Indica as finalidades permitidas para a chave pública do certificado.
   - **Verificação:** O uso da chave deve corresponder à finalidade da comunicação (ex.: "Assinatura Digital").

6. **Uso Avançado da Chave (Extended Key Usage)**
   - Especifica usos adicionais da chave pública, além dos listados em "Key Usage".
   - **Verificação:** O uso da chave deve ser compatível com a aplicação (ex.: "Autenticação de Cliente Web TLS" para comunicação segura).
