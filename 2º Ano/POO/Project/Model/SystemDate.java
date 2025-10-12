package Model;

import java.io.*;
import java.time.LocalDateTime; 

public class SystemDate implements Serializable {
    private static LocalDateTime date;

    public static LocalDateTime getDate() {
        return date;
    }

    public static void setDate(LocalDateTime newDate) {
        date = newDate;
    }

    public static void save(String fileName) throws FileNotFoundException, IOException {
        String filePath = "data/" + fileName;
        try (FileOutputStream fs = new FileOutputStream(filePath);
                ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(date);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        String filePath = "data/" + fileName;
        try (FileInputStream fs = new FileInputStream(filePath);
                ObjectInputStream os = new ObjectInputStream(fs)) {
            date = (LocalDateTime) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}