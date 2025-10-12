import Time.Data;
import Users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Users.BuyOrSell.buyOrSellArticle;
import static Users.User.*;

public class UserSellerManager {

    private static final String USERS_FILE = "Files/users.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            boolean loggedIn = false;
            String userId = "";
            String userEmail = "";
            // Para inicializar em que data começa
            Data.startTempo();
            while (!loggedIn) {
                System.out.println("\\Welcome to Vintagio/");
                System.out.println("Choose an option:");
                System.out.println(" 1. Log in");
                System.out.println(" 2. Create new user");
                System.out.println(" 0. Quit");
                try {
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 0:
                            System.out.println("Exiting program...");
                            System.exit(0);
                        case 1:
                            System.out.println("Enter user email:");
                            userEmail = scanner.next();
                            ArrayList<User> usersList = readUsers(USERS_FILE);
                            if (loginUser(userEmail, usersList)) {
                                System.out.println("Logged in as user with email: " + userEmail + "\n");
                                loggedIn = true;
                                running = false;
                            } else {
                                System.out.println("Invalid user email. Please try again.\n");
                            }
                            break;
                        case 2:
                            userId = createUser(USERS_FILE);
                            System.out.println("User created with id " + userId + "\n");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Invalid input. Please enter a number.\n");
                    scanner.next();
                }
            }
            buyOrSellArticle(userEmail);
        }
    }
}
