package teste24;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Ex2De240104 {

    public static void main(String[] args) throws IOException {

        ServerSocket sc = new ServerSocket(12345);
        ManagerInterface manager = new Managerio();

        while (true) {
            Socket socket = sc.accept();
            Worker worker = new Worker(socket, manager);
            Thread t = new Thread(worker);
            t.start();
        }

    }

    public class Worker implements Runnable {

        Socket socket;
        ManagerInterface manager;

        public Worker(Socket socket, ManagerInterface manager) {
            this.socket = socket;
            this.manager = manager;
        }

        Raid raid;

        @Override
        public void run() {
            try {
                DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                String command;
                while ((command = is.readUTF()) != null) {
                    switch (command) {
                        case "join":
                            os.writeUTF("Write your name");
                            os.flush();
                            String nome = is.readUTF();
                            Integer players = is.readInt();
                            raid = manager.join(nome, players);
                            raid.waitStart();
                        case "leave":
                            raid.leave();
                        default:
                            os.writeUTF("O Lwazi não quer jogar mais worelede ofe uarcrafte");
                            os.flush();
                    }
                }
                is.close();
                os.close();
               socket.close(); 
            } catch (Exception e) {
                System.out.println("Fodeu");
            }
        }
    }
}