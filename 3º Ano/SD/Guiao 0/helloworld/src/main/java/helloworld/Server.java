package helloworld;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Executors;

// executar com: mvn exec:java -Dexec.mainClass="helloworld.Server"
public class Server extends HelloGrpc.HelloImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        responseObserver.onNext(
                HelloReply.newBuilder().setGreeting(
                        "Hello "+request.getWho()+"!"
                ).build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws Exception {
        Grpc.newServerBuilderForPort(12345, InsecureServerCredentials.create())
                .addService(new Server())
                .executor(Executors.newSingleThreadExecutor())
                .build().start().awaitTermination();
    }
}
