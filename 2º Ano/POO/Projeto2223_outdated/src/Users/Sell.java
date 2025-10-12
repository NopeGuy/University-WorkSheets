package Users;

import Items.*;

import java.util.Scanner;

import static Items.Mala.printHandbagToFile;
import static Items.Mala.printHandbagToFileHistory;
import static Items.MalaPremium.printPremiumHandbagToFile;
import static Items.MalaPremium.printPremiumHandbagToFileHistory;
import static Items.Sapatilha.printShoeToFile;
import static Items.Sapatilha.printShoetoFileHistory;
import static Items.SapatilhaPremium.printPremiumShoeToFile;
import static Items.SapatilhaPremium.printPremiumShoetoFileHistory;
import static Items.Tshirt.printTshirtToFile;
import static Items.Tshirt.printTshirtToFileHistory;
import static Users.BuyOrSell.buyOrSellArticle;

public class Sell {
    public static void sellArticle(String userEmail) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        String ARTICLES_FILE = "Files/stock/" + userEmail + ".txt";
        String SELL_FILE = "Files/sellhistory.txt";

        while(running) {
            System.out.println("Select which article to sell: \n 1) Shoes \n 2) Premium Shoes \n 3) Handbags \n 4) Premium Handbags \n 5) T-Shirts \n\n  0) Exit:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Sapatilha nova1 = Sapatilha.createShoe();
                    printShoeToFile(nova1, ARTICLES_FILE);
                    printShoetoFileHistory(userEmail, nova1, SELL_FILE);

                    break;
                case 2:
                    SapatilhaPremium nova2 = SapatilhaPremium.createPremiumShoe();
                    printPremiumShoeToFile(nova2, ARTICLES_FILE);
                    printPremiumShoetoFileHistory(userEmail, nova2, SELL_FILE);
                    break;
                case 3:
                   Mala nova3 = Mala.createHandbag();
                    printHandbagToFile(nova3, ARTICLES_FILE);
                    printHandbagToFileHistory(userEmail, nova3, SELL_FILE);
                    break;
                case 4:
                    MalaPremium nova4 = MalaPremium.createPremiumHandbag();
                    printPremiumHandbagToFile(nova4, ARTICLES_FILE);
                    printPremiumHandbagToFileHistory(userEmail, nova4, SELL_FILE);
                    break;
                case 5:
                    Tshirt nova5 = Tshirt.createTshirt();
                    printTshirtToFile(nova5, ARTICLES_FILE);
                    printTshirtToFileHistory(userEmail, nova5, SELL_FILE);
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
