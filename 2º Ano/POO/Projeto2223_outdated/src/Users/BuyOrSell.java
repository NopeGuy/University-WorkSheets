package Users;

import Time.Data;
import Transportation.Encomenda;
import Transportation.Transportadora;

import java.util.Scanner;

import static Time.Data.tempo;

public class BuyOrSell {
    public static void buyOrSellArticle(String userEmail) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        //clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        while(running) {
            System.out.println("Welcome to the shop, today is "+ tempo + ",\nWhat are you here for?: \n 1) Buy \n 2) Sell \n 3) Buy history \n 4) Sell history \n 5) Check and Return packages \n 6) Check Admin Functionality\n\n  0) exit ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    System.exit(0);
                case 1:
                    Buy.buyArticle(userEmail);
                    break;
                case 2:
                    Sell.sellArticle(userEmail);
                    break;
                case 3:
                    Purchases.printUserSales(userEmail, "Files/buyhistory.txt");
                    break;
                case 4:
                    Purchases.printUserSales(userEmail, "Files/sellhistory.txt");
                    break;
                case 5:
                    Pack.packagesMenu(userEmail);
                    break;
                case 6:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Admin functions are:\n 80) Skip 'x' days\n 81) Change package carriers\n 82) Store statistics\n\nPlease enter one of those options to continue with admin functions\n");
                    break;
                case 80:
                    Data.addDays();
                    System.out.println("Date advanced to " + tempo + "\n");
                    Encomenda.atualizarEstadoEncomendas();
                    break;
                case 81:
                    Transportadora.transporteMenu(userEmail);
                    break;
                case 82:
                    Queries.statisticsMenu(userEmail);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}