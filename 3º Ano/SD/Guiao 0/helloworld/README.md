### Via IntelliJ
**Nota:** Vídeo de configuração disponível na diretoria.

1. Abrir a pasta `helloworld` com IntelliJ.

2. Em caso de necessidade, especificar a versão do JDK.

3. De seguida, seguir as [instruções relativamente à compilação de projetos com Maven no IntelliJ](https://www.jetbrains.com/help/idea/delegate-build-and-run-actions-to-maven.html#build_through_maven), nomeadamente:
>
    From the main menu select File | Settings | Build, Execution, Deployment | Build Tools | Maven.

    Click "Maven" and from the list, select "Runner".

    On the "Runner" page, select "Delegate IDE build/run actions to maven".

4. Marcar as seguintes diretorias como "Sources Root" usando a opção "Mark Directory as" do menu de contexto:

    `./target/generated-sources/protobuf/java`

    `./target/generated-sources/protobuf/grpc-java`


### Via Terminal

1. Compilar o projeto com:

    `mvn compile`

2. Executar o servidor com:

    `mvn exec:java -Dexec.mainClass="helloworld.Server"`

3. Executar o cliente com:

    `mvn exec:java -Dexec.mainClass="helloworld.Client" -Dexec.args="World"`
