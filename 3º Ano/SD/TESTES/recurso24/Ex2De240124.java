package recurso24;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import recurso24.Ex1De240124.Manager;
import recurso24.Ex1De240124.Transfer;

public class Ex2De240124 {

    public static void main(String[] args) throws IOException {

        ServerSocket sc = new ServerSocket();
        Ex1De240124 ex1 = new Ex1De240124();
        Manager manager = ex1.new Managerio();
        while (true) {
            Socket client = sc.accept();
            Worker worker = new Worker(client, manager);
            Thread t = new Thread(worker);
            t.start();
        }
    }

    public static class Worker implements Runnable {
        Socket socket;
        Manager manager;

        public Worker(Socket socket, Manager manager) {
            this.socket = socket;
            this.manager = manager;
        }

        @Override
        public void run() {
            try {
                DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                String command;
                while ((command = is.readUTF()) != null) {
                    switch (command) {
                        case "newTransfer":
                            String id = manager.newTransfer();
                            os.writeUTF(id);
                            os.flush();
                            break;

                        case "sendData":
                            String identifierSD = is.readUTF();
                            Transfer tSD = manager.getTransfer(identifierSD);
                            int bytesToEnqueue = is.readInt();
                            int bytesRead;
                            byte[] buffer = new byte[16000];
                            while ((bytesRead = is.read(buffer)) != -1) {
                                byte[] actualData = Arrays.copyOf(buffer, bytesToEnqueue);
                                tSD.enqueue(actualData);
                            }
                            break;

                        case "getData":
                            String identifierGD = is.readUTF();
                            Transfer tGD = manager.getTransfer(identifierGD);
                            byte[] data2 = tGD.dequeue();
                            os.write(data2);
                            os.flush();
                            break;

                        default:
                            break;
                    }
                }
                is.close();
                os.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
