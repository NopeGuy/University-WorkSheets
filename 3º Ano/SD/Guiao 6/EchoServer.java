import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        try {
            int soma=0;
            int counter=0;

            ServerSocket ss = new ServerSocket(12345);

            while (true) {

                Socket socket = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                String line;
                while ((line = in.readLine()) != null) {
                    try{
                        System.out.println("Incoming: " + line);
                        soma += Integer.parseInt(line);
                        counter++;
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }

                    out.println(soma);
                    out.flush();
                }
                System.out.println("Sai do while");
                float media = soma/counter;
                out.println(Float.toString(media));
                out.flush();

                soma=0;
                counter=0;

                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}