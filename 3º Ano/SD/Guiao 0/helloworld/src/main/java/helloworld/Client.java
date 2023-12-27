package helloworld;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;

// executar com: mvn exec:java -Dexec.mainClass="helloworld.Client" -Dexec.args="World"
public class Client {
    public static void main(String[] args) throws Exception {
        var channel = Grpc.newChannelBuilder("localhost:12345", InsecureChannelCredentials.create()).build();
        var t = HelloGrpc.newBlockingStub(channel);

        for(String who: args) {
            var request = HelloRequest.newBuilder().setWho(who).build();
            var reply = t.hello(request);
            System.out.println(reply.getGreeting());
        }

        channel.shutdown();
    }
}
