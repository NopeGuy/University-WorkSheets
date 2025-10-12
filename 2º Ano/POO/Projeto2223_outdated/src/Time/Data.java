package Time;

import Transportation.Encomenda;
import java.util.Collection;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.DateTimeException;

public class Data {
    public static LocalDate tempo;

    public static void addDays(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease enter the number of days to skip: ");
        int days = scanner.nextInt();
        tempo=tempo.plusDays(days);
        //falta inserir aqui as verificações das encomendas em transito
    }

    //ideia de como fazer verificação de transito, atualmente não faz nada
    public static void checkEncomendas(Collection<Encomenda> encomendas, LocalDate date) {
        for (Encomenda encomenda : encomendas) {
            if (encomenda.getData_criacao().isBefore(date.minusDays(2))) {
                System.out.println("Encomenda " + encomenda.getEncomendaId() + " is more than 2 days old.");
                encomenda.setEstado("Entregue.");
            }
        }
    }


    public boolean validaData(LocalDate x){ //se validaData==true retira-se do ficheiro "Em transito"
        return tempo.isBefore(x);
    }

    public void setData(LocalDate x){
        tempo=x;
    }

    public static void startTempo() throws DateTimeException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
       /* String animation = ".....        \nUnexpected System Reset\nPlease enter the system date (dd-mm-yyyy):\n->";
        for (int i = 0; i < animation.length(); i++) {
            System.out.print(animation.charAt(i));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
        */
        Scanner scanner = new Scanner(System.in);
        LocalDate novo = null;
        while (novo == null) {
            System.out.print("Please enter the initial date (dd-mm-yyyy): ");
            String input = scanner.nextLine();
            String[] valoresData = input.split("-");
            int[] parsedData = new int[3];
            for (int i = 0; i < valoresData.length; i++) {
                parsedData[i] = Integer.parseInt(valoresData[i]);
            }
            try {
                novo = LocalDate.of(parsedData[2], parsedData[1], parsedData[0]);
            } catch (DateTimeException e) {
                System.out.println("Invalid date format, please enter in the format dd-mm-yyyy");
            }
        }
        tempo = novo;
    }
}
