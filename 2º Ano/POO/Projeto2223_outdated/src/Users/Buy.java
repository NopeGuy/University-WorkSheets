package Users;

import Items.Artigo;
import Time.Data;
import Transportation.Encomenda;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static Transportation.Encomenda.addEncomendaToFile;
import static Users.BuyOrSell.buyOrSellArticle;
import static Users.Purchases.getAllSales;
import static Users.Purchases.removeItemFromUserStock;

public class Buy {
    public static void buyArticle(String userEmail) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        Encomenda encomenda = new Encomenda(new ArrayList<>(),0,0.0,"Em transito", Data.tempo,userEmail, ""); //verificar a transportadora
        ArrayList<Artigo> cart = new ArrayList<>();
        ArrayList<Artigo> stock = getAllSales(userEmail);
        String itemId;

        while(running) {
            System.out.println("Select if you want to:\n 1) See the stock\n 2) Add an item to your shopping cart\n 3) Remove an item from your shopping cart\n 4) Display shopping cart\n 5) Buy shopping cart\n\n  0) exit:");

            int choice;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine(); // consume the invalid input
                }
            }


            switch (choice) {
                case 1:
                    System.out.println("Stock:\n");
                    for (Artigo artigo : stock) {
                    System.out.println(artigo.toString() + "\n");
                }
                    break;
                case 2:
                    System.out.println("Select the item you want to add to your shopping cart typing the id: ");
                    itemId = scanner.next();
                    boolean itemFound = false;
                    for (Artigo artigo : stock) {
                        if (Objects.equals(artigo.getItem_id(), itemId)) {
                            cart.add(artigo);
                            System.out.println("Item added to cart.");
                            itemFound = true;
                            break;
                        }
                    }
                    if (!itemFound) {
                        System.out.println("Inserted ID doesn't exist.\n");
                    }
                    break;
                case 3:
                    System.out.println("Select the item you want to remove from your shopping cart typing the id: ");
                    itemId = scanner.next();
                    for (Artigo artigo : cart) {
                        if (Objects.equals(artigo.getItem_id(), itemId)) {
                            cart.remove(artigo);
                            System.out.println("Item removed from cart.");
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("\nYour shopping cart:");
                    for (Artigo artigo : cart) {
                        System.out.println(artigo.toString());
                    }
                    System.out.println("\n");
                    break;
                case 5:
                    double totalPrice = encomenda.calcularPrecoFinal(cart);
                    System.out.println("Your total comes to: " + totalPrice + "€, do you want to proceed? \n[1) Yes, 2) No]");
                    int choice2 = scanner.nextInt();
                    boolean proceed = true;
                    switch (choice2) {
                        case 1:
                            break;
                        case 2:
                            System.out.println("Your order has been cancelled.");
                            proceed = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (proceed) {
                        for (Artigo artigo : cart) {
                            totalPrice += artigo.getPreco();
                        }
                        int id = (int) (Math.random() * 100000);
                        encomenda.setEncomendaId(String.valueOf(id));
                        encomenda.setColecao(cart);
                        encomenda.setDimensao(cart.size());
                        encomenda.setPrecoFinal(totalPrice);
                        encomenda.setEstado("Em transito");
                        encomenda.setDataCriacao(Data.tempo);
                        addEncomendaToFile(userEmail, encomenda);
                        for (Artigo artigo : cart) {
                            removeItemFromUserStock(userEmail, artigo.getItem_id());
                        }
                        System.out.println("Your order has been placed. Thank you for your purchase.\n");
                        running = false;
                    }
                    break;
                case 0:
                    buyOrSellArticle(userEmail);
                    cart = new ArrayList<>();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
