package bank;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;

import java.util.concurrent.Executors;

// executar com: mvn exec:java -Dexec.mainClass="helloworld.Server"
public class Server extends HelloGrpc.HelloImplBase {

    private final BankJ bankj = new BankJ();


    public static void main(String[] args) throws Exception {
        Grpc.newServerBuilderForPort(12345, InsecureServerCredentials.create())
                .addService(new Server())
                .executor(Executors.newSingleThreadExecutor())
                .build().start().awaitTermination();
    }
}
