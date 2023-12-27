import java.util.HashSet;
import java.util.Set;

public class Servidor {

    public static void main(String[] args){
        WarehouseGreedy h = new WarehouseGreedy();
        new Thread(() -> {
            try {
                while(1 == 1){
                    System.out.printf("Vou fazer supply na T=%d\n", 1);
                    h.supply("Leite", 10);
                    h.supply("Donuts", 5);
                    System.out.printf("Supply Feito na T=%d\n", 1);
                    Thread.sleep(5000);
                }

            } catch (Exception e) {}

        }).start();

        for(int i = 0; i<5; i++){
            int fI = i;
            new Thread(() -> {
                while(1 == 1) {
                    try {
                        Set<String> items = new HashSet<>();
                        items.add("Leite");
                        items.add("Donuts");
                        h.consume(items);

                        System.out.printf("Consumidor consumiu na T=%d\n", fI);

                    } catch (Exception e) {}
                    System.out.printf("await retornou (T=%d)\n", 1);
                }

            }).start();
        }

    }
}