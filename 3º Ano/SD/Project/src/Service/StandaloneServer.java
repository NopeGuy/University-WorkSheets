package Service;

import sd23.JobFunction;
import sd23.JobFunctionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class StandaloneServer {

    // EM MB
    private double MAX_MEMORY = 1000 * 10e6;


    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 10080);

        String memory = (args.length > 0) ? args[0] : "5000";

        try {
            Integer.parseInt(memory);
        }catch (Exception e){
            System.out.println("Por favor execute com o argumento <memoria>");
        }

        while(1 == 1){
            Connection con = new Connection(s);
            con.sendString(90, -1, memory);


            try (con){

                while(true) {
                    Frame f  = con.receive();

                    System.out.println("Recebii frame com tag = " + f.tag );
                    if( f.tag == 1000 ) {

                        new Thread(() -> {
                            try {
                                long startTime = System.currentTimeMillis();
                                byte[] resp = JobFunction.execute(f.data);
                                long endTime = System.currentTimeMillis();
                                // Resposta
                                con.sendString(1001, f.taskid, (endTime-startTime)/1000 + "," + new String(resp));
                                System.out.println("TaskID = " + f.taskid + " => " + new String(resp));
                            } catch (JobFunctionException e) {
                                System.out.println("JobFunctionException -> " + e.getMessage());
                                try {

                                    con.sendString(1002, f.taskid, e.getMessage());
                                } catch (IOException ex) {
                                    System.out.println("Não foi possível informar o CloudServer acerca do termino da taskid = " + f.taskid);
                                }
                            } catch (IOException e){
                                System.out.println("Não foi possível informar o CloudServer acerca do termino da taskid = " + f.taskid);
                            }
                        }).start();
                    // Get status do Servidor
                    }
                    else if (f.tag == 91) {
                        System.out.println("Este servidor tem ID = " + f.taskid);
                    // Alguma tag não existente
                    } else {
                        con.sendString(-1, 0, "Ainda não foi implementado...");
                    }

                }
            } catch( IOException error ){
                System.out.println("Error com o cliente " + s.getLocalAddress() + " :" + error.getMessage());
            }



        }


    }

}