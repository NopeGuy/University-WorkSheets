import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Lista para armazenar as threads
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Increment thing = new Increment(i);
            Thread myThread = new Thread(thing);
            threads.add(myThread);
            myThread.start();
        }

        // Aguarda a conclusão de cada thread
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Fim");
    }
}