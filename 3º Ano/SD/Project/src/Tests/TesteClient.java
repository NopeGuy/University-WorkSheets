package Tests;


import Service.ClientDemultiplexer;
import Service.Connection;
import Service.Frame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class TesteClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Fazer login");
        Socket s = new Socket("localhost", 12345);
        Connection c = new Connection(s);
        ClientDemultiplexer cd = new ClientDemultiplexer(c);
        cd.start();

        cd.send(new Frame(1, 0, "luisvilas;;teste".getBytes()));
        System.out.println(new String(cd.receive(2).data));


        new Thread(() -> {
            int i = 0;
            while(i < 10) {
                new Thread(() -> {
                    try {
                        FileInputStream fis = new FileInputStream(new File("C:\\GetDeviceCap.xml"));
                        cd.send(30, 0, fis.readAllBytes());
                        Frame respostaTarefa = cd.receive(31);
                        System.out.println(new String(respostaTarefa.data));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                }).start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
        }).start();
        Thread.sleep(4000);
        new Thread(() -> {
            int i = 0;
            while(i < 10) {
                try {
                    cd.send(40, 0, new byte[0]);
                    Frame respostaTarefa = cd.receive(41);
                    System.out.println(new String(respostaTarefa.data));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
        }).start();



    }
}
