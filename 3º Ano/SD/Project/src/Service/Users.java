package Service;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Users implements Serializable {

    private final HashMap<String, String> database;
    public ReentrantReadWriteLock l = new ReentrantReadWriteLock();

    public Users() {
        this.database = new HashMap<>();
    }

    /*
     * Valida se um email e password estão corretos
     */
    public boolean validatePassword(String email, String password){
        try {
            this.l.readLock().lock();
            return this.database.get(email).equals(password);
        } catch (Exception e){
            return false;
        } finally {
            this.l.readLock().unlock();
        }
    }


    /*
     * Adiciona a conta ao sistema
     */
    public void addAccount(String email, String password){
        try {
            this.l.writeLock().lock();
            this.database.put(email, password);
        } finally {
            this.l.writeLock().unlock();
        }
    }

    /*
     * Verifica se a conta já existe
     */
    public boolean accountExists(String email) {
        try {
            this.l.readLock().lock();
            if(this.database.get(email) != null)
                return true;
        } finally {
            this.l.readLock().unlock();
        }
        return false;
    }

    /*
     * Serialize para o Ficheiro as Contas Existentes
     */
    public void serialize(String fileLocation) throws IOException {
        try {
            //this.l.writeLock().lock();
            FileOutputStream fileStream = new FileOutputStream(fileLocation);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
            outputStream.writeObject(this);
            outputStream.close();
            fileStream.close();
        } finally {
            //this.l.writeLock().unlock();
        }

    }

    /*
     * Lê do ficheiro para a Class
     */
    public static Users deserialize(String fileLocation) throws IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream(fileLocation);
        ObjectInputStream inputStream = new ObjectInputStream(fileStream);
        Users accounts = (Users) inputStream.readObject();
        inputStream.close();
        fileStream.close();
        return accounts;
    }
}
