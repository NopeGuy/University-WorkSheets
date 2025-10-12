package TP1;

import java.io.*;

public class Estados{



    public int dadosGuardar(String filename, Vintage v){
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(v);
            oos.flush();
            oos.close();
        }catch(FileNotFoundException e){
            return 1;
        }catch(IOException e){
            return 2;
        }
        return 0;
    }

    public Vintage dadoscarregar (String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(file);
        Vintage v = (Vintage) ois.readObject();
        ois.close();
        return v;
    }
}