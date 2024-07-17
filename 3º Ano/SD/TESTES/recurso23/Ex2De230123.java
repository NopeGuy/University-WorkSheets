package recurso23;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Ex2De230123 {
    public static void main(String[] args) throws IOException {
        int N = 10;
        int V = 6;
        ServerSocket sc = new ServerSocket(12345);
        Ex1De230123 kartRace = new Ex1De230123(N, V);

        while (true) {
            Socket socket = sc.accept();
            Worker worker = new Worker(socket, kartRace);
            Thread t = new Thread(worker);
            t.start();
        }
    }

    public static class Worker implements Runnable {
        Socket socket;
        Ex1De230123 kartRace;
        int kartnum;

        public Worker(Socket socket, Ex1De230123 kartRace) {
            this.socket = socket;
            this.kartRace = kartRace;
        }

        @Override
        public void run() {
            try (DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

                kartnum = kartRace.reserva();
                os.writeInt(kartnum);
                os.flush();

                String comando;
                while ((comando = is.readUTF()) != null) {
                    
                    switch (comando) {
                        case "preparado":
                            kartRace.preparado(kartnum);
                            break;

                        case "completaVolta":
                            kartRace.completaVolta(kartnum);
                            break;

                        case "voltasCompletas":
                            int[] voltas = kartRace.voltasCompletas();
                            os.writeInt(voltas.length);
                            for (int volta : voltas) {
                                os.writeInt(volta);
                            }
                            os.flush();
                            break;

                        case "fechar":
                            socket.close();
                            return;

                        default:
                            os.writeUTF("Comando Inválido.");
                            os.flush();
                            break;
                    }

                    if (!kartRace.getOneFinished()) {
                        int winner = kartRace.vencedor();
                        os.writeUTF("O vencedor foi " + winner);
                        os.flush();
                        break;
                    }
                }
                System.out.println("Connection Closed!");
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
