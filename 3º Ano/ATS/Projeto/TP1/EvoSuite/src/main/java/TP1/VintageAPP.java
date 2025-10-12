package TP1;
import java.io.*;

public class VintageAPP {
    public static void main(String[] args) throws IOException, ClassNotFoundException{


        Vintage v = new Vintage();
        Apresentacao a = new Apresentacao();
        Controlador c = new Controlador();
        c.interpretador(v,a);

        
    }
}
