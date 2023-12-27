import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerThreaded {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(12345);
            EchoServerThreaded server = new EchoServerThreaded();

            while (true) {
                Socket socket = ss.accept();
                ClientHandler clientHandler = server.new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int soma = 0;
        int counter = 0;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                try {
                    System.out.println("Incoming: " + line);
                    soma += Integer.parseInt(line);
                    counter++;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                out.println(soma);
            }

            if (counter > 0) {
                float media = (float) soma / counter;
                out.println(Float.toString(media));
            } else {
                out.println("Nenhum número válido foi inserido.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

}
