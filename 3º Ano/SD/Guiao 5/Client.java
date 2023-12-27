import java.util.HashSet;
import java.util.Set;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        int N=5;
        Thread[] threads = new Thread[N];
        WarehouseGreedy w = new WarehouseGreedy();
        Set<String> s = new HashSet<>();
        s.add("Tilhas");

        for(int i=0; i<N; i++){
            int fI=i;
            threads[i] = new Thread(()->{
                try{
                    //Thread.sleep(1000*fI);
                    System.out.println("Criei a thread: " + fI);
                    w.consume(s);
                    System.out.println("Consegui consumir! [Thread " + fI + "]");

                } catch (Exception ignored) {}
            });
        }

        for(int i=0; i<N; i++){
            threads[i].start();
        }

        Thread.sleep(1000);
        w.supply("Tilhas", 1);
        System.out.println("DEI SUPPLY");

        for(int i=0; i<N; i++){
            threads[i].join();
        }
    }
}