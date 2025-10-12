package Service;

import java.io.IOException;

public class Task {

    public int taskID;
    public byte[] data;

    public Connection c;
    public int executorServer = -1;

    public Task(int id, byte[] programa, Connection c) {
        this.taskID = id;
        this.data = programa;
        this.c = c;
    }


    public int getMemory(){ return this.data.length; }

    // Only used in StandaloneServer before
    public void executeTask(){

        try{
            if(Client.DEBUG) System.out.printf("Thread %d vai executar a TaskID = %d\n", Thread.currentThread().getId(), this.taskID);
            byte[] out = sd23.JobFunction.execute(this.data);
            //this.c.sendData(31, taskID, out);
        } catch (Exception exc) {
            // ERRO
        }


    }
}