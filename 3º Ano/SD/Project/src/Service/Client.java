package Service;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Client {
    public static final boolean DEBUG = false;


    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 10080);
        String username = null;
        String choice = "";
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        Connection c = new Connection(s);
        ClientDemultiplexer cd = new ClientDemultiplexer(c);
        cd.start();
        c.send(new Frame(100, -1, new byte[0]));

        while(username == null){
            clearConsole();
            System.out.println("Bem vindo. \n1. Login\n2. Registar\nOpção a seguir: ");
            String opt = stdin.readLine();
            if(opt.equals("1")) {
                System.out.print("Introduza o seu endereço de email: ");
                String email = stdin.readLine();
                System.out.print("Introduza a sua palavra-passe: ");
                String password = stdin.readLine();

                if(DEBUG) System.out.println("Enviar string email;;password");

                cd.sendString(1, 0, email + ";;" + password);
                Frame f = cd.receive(2);
                if (new String(f.data).startsWith("ERRO")){
                    System.out.print(new String(f.data));
                } else {
                    System.out.println("Login com sucesso..");
                    username = email;
                }
            }
            if(opt.equals("2")){
                System.out.println("Introduza o seu username: ");
                String user = stdin.readLine();
                System.out.print("Introduza a sua palavra-passe: ");
                String password = stdin.readLine();
                cd.sendString(2, 0,user + ";;" + password);
                Frame f = cd.receive(3);
                if (new String(f.data).startsWith("ERRO")){
                    System.out.print(new String(f.data));
                } else {
                    System.out.println("Login com sucesso..");
                    username = user;
                }

            }
        }


        while(!choice.equals("9")){
            System.out.printf(
                    "\n\n*MENU DO CLIENTE (username=%s)*\n" +
                            "1. Executar tarefa\n" +
                            "2. Executar tarefas de ficheiro\n" +
                            "3. Ver estado dos Servidores\n" +
                            "9. Sair\n" +
                            "Opção: "
            , username);

            choice = stdin.readLine();

            if (choice.equals("1")) {
                    System.out.println("Introduza a localização do Programa: ");
                    String pn = stdin.readLine();
                    System.out.println("Tarefa enviada para o servidor.\n");
                        new Thread(() -> {
                            boolean tarefaEnviada = false;
                            while (!tarefaEnviada) {

                                try {
                                    FileInputStream fis = new FileInputStream(new File(pn));
                                    cd.send(30, 0, fis.readAllBytes());

                                    Frame respostaTarefa = cd.receive(31);

                                    if (new String(respostaTarefa.data).startsWith("ERRO")) {
                                        System.out.println("Erro do Servidor (tag=" + respostaTarefa.tag + "): " + new String(respostaTarefa.data));
                                    } else {
                                        System.out.printf("Tarefa com id = %d terminou. Resposta: ", respostaTarefa.taskid);
                                        System.out.println(new String(respostaTarefa.data));
                                    }
                                } catch (Exception e) {
                                    System.out.println("Não foi possível enviar a tarefa: " + e.getMessage());
                                }
                                tarefaEnviada = true;

                            }


                        }).start();

            } else if( choice.equals("2")) {
                System.out.println("Introduza a localização do ficheiro de input: ");
                String inputFile = stdin.readLine();
                System.out.println("Introduza a localização do ficheiro de output: ");
                String outputFile = stdin.readLine();
                FileInputStream fis = new FileInputStream(new File(inputFile));
                FileOutputStream fos = new FileOutputStream(new File(outputFile));

                fos.write("id_tarefa,tempo_execução,resultado\n".getBytes(StandardCharsets.UTF_8));

                Scanner sc = new Scanner(fis);
                while(sc.hasNextLine()){
                    String[] lineArgs = sc.nextLine().split(",");
                    int id = Integer.parseInt(lineArgs[0]);

                    new Thread(() -> {
                        boolean tarefaEnviada = false;
                        while (!tarefaEnviada) {
                            try {
                                cd.send(30, 0, lineArgs[2].getBytes());

                                Frame respostaTarefa = cd.receive(31);

                                if (new String(respostaTarefa.data).startsWith("ERRO")) {
                                    System.out.println("Erro do Servidor (tag=" + respostaTarefa.tag + "): " + new String(respostaTarefa.data));
                                } else {
                                    System.out.printf("Tarefa com id = %d terminou. Resposta: ", respostaTarefa.taskid);
                                    //System.out.println(new String(respostaTarefa.data));
                                    String write = id + "," + new String(respostaTarefa.data) ;
                                    fos.write(write.getBytes(StandardCharsets.UTF_8));
                                    fos.write("\n".getBytes());
                                }
                            } catch (Exception e) {
                                System.out.println("Não foi possível enviar a tarefa: " + e.getMessage());
                            }
                            tarefaEnviada = true;

                        }


                    }).start();
                }

            } else if (choice.equals("3")) {
                if(DEBUG) System.out.println("Enviar tag=40");
                cd.send(40, 0, new byte[0]);
                if(DEBUG) System.out.println("Tag=40 enviada");
                System.out.println("Status do Servidor (ATUAL/MAX)\n" +
                        new String(cd.receive(41).data));
            } else if (choice.equals("9")){
                c.close();
            } else{
                System.out.println("Não existe nada com essa opção...");
            }
        }

        System.out.println("A terminar.");

    }

    public final static void clearConsole()
    {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }


}
