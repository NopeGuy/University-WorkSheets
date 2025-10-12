package Users;

import Transportation.Encomenda;

import java.util.ArrayList;
import java.util.Scanner;

import static Transportation.Encomenda.removePackageFromFile;
import static Users.BuyOrSell.buyOrSellArticle;

public class Pack {
    public static void packagesMenu(String userEmail){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Encomenda> encomendas;
        encomendas = Encomenda.readPackagesFromFile();
        for (Encomenda encomenda : encomendas) {
            if(encomenda.getBuyerEmail().equals(userEmail)) System.out.println(encomenda.toString2());
        }

        while(running) {
            System.out.println("\nSelect: \n 1)Return Article\n\n  0) Exit:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Type the package ID (The article needs to still be in transit):");
                    String packageID = scanner.next();
                    removePackageFromFile(packageID);
                    break;
                case 0:
                    buyOrSellArticle(userEmail);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
