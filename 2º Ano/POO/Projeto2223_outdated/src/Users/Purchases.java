package Users;

import Items.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Purchases {
    public static void printUserSales(String email, String FILE) {
        try {
            Scanner scanner = new Scanner(new File(FILE));
            String itemType = "";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(":");

                if (values[0].equals(email)) {
                    itemType = values[3].substring(0, 2);

                    switch (itemType) {
                        case "TN":
                            System.out.println("T-Shirt");
                            System.out.println("Description: " + values[1] + ", Brand: " + values[2] + ", Item ID: " + values[3] + ", Transport Company: " + values[4]
                                    + ", Price: " + values[5] + ", Discount: " + values[6] + ", Previous Owners: " + values[7] + ", Stock: " + values[8] + "\n"
                                    + "Size: " + values[9] + ", Pattern: " + values[10] + "\n");
                            break;
                        case "SN":
                            System.out.println("Shoe");
                            System.out.println("Description: " + values[1] + ", Brand: " + values[2] + ", Item ID: " + values[3] + ", Transport Company: " + values[4]
                                    + ", Price: " + values[5] + ", Discount: " + values[6] + ", Previous Owners: " + values[7] + ", Stock: " + values[8] + "\n" +
                                    "Size: " + values[9] + ", Has shoelaces: " + values[10] + ", Color: " + values[11] + ", Collection Year: " + values[12] + "\n");
                            break;
                        case "SP":
                            System.out.println("Premium Shoe");
                            System.out.println("Description: " + values[1] + ", Brand: " + values[2] + ", Item ID: "
                                    + values[3] + ", Transport Company: " + values[4]
                                    + ", Price: " + values[5] + ", Discount: " + values[6] + ", Previous Owners: "
                                    + values[7] + ", Stock: " + values[8] + "\n" +
                                    "Size: " + values[9] + ", Has shoelaces: " + values[10] + ", Color: " + values[11]
                                    + ", Collection Year: " + values[12] + ", Author: " + values[13] +"\n");
                            break;
                        case "HN":
                            System.out.println("Handbag");
                            System.out.println("Description: " + values[1] + ", Brand: " + values[2] + ", Item ID: " + values[3] + ", Transport Company: " + values[4]
                                    + ", Price: " + values[5] + ", Discount: " + values[6] + ", Previous Owners: " + values[7] + ", Stock: " + values[8] + "\n" +
                                    "Size: " + values[9] + ", Collection Year: " + values[10] + ", Material: " + values[11] + "\n");
                            break;
                        case "HP":
                            System.out.println("Premium Handbag");
                            System.out.println("Description: " + values[1] + ", Brand: " + values[2] + ", Item ID: "
                                    + values[3] + ", Transport Company: " + values[4]
                                    + ", Price: " + values[5] + ", Discount: " + values[6] + ", Previous Owners: "
                                    + values[7] + ", Stock: " + values[8] + "\n" +
                                    "Size: " + values[9] + ", Collection Year: " + values[10] + ", Material: "
                                    + values[11] + ", Author: " + values[12] + "\n");
                            break;
                        default:
                            System.out.println("Unknown item type: " + itemType);
                            break;
                    }
                }
            }
            if (itemType.equals("")) {
                System.out.println("No items found.");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE);
        }
    }

    public static ArrayList<Artigo> getAllSales(String userEmail) {
        ArrayList<Artigo> allItems = new ArrayList<>();
        String[] stockFiles = new File("Files/stock").list();

        assert stockFiles != null;
        for (String stockFile : stockFiles) {
            if (stockFile.equals(userEmail + ".txt")) continue; // Skip the user's own stock file

            try (Scanner scanner = new Scanner(new File("Files/stock/" + stockFile))) {
                while (scanner.hasNextLine()) {
                    String[] itemDetails = scanner.nextLine().split(":");
                    String descricao = itemDetails[0];
                    String marca = itemDetails[1];
                    String itemId = itemDetails[2];
                    String transport = itemDetails[3];
                    Double preco = Double.parseDouble(itemDetails[4]);
                    Double desconto = Double.parseDouble(itemDetails[5]);
                    int num_donos = Integer.parseInt(itemDetails[6]);
                    int stock = Integer.parseInt(itemDetails[7]);

                    if (itemId.startsWith("SN")) {
                        int tamanho = Integer.parseInt(itemDetails[8]);
                        boolean atacadores = Boolean.parseBoolean(itemDetails[9]);
                        String cor = itemDetails[10];
                        int ano_colecao = Integer.parseInt(itemDetails[11]);
                        allItems.add(new Sapatilha(descricao, marca, itemId, transport, preco, desconto, num_donos, stock, tamanho, atacadores, cor, ano_colecao));
                    } else if (itemId.startsWith("SP")) {
                        int tamanho = Integer.parseInt(itemDetails[8]);
                        boolean atacadores = Boolean.parseBoolean(itemDetails[9]);
                        String cor = itemDetails[10];
                        int ano_colecao = Integer.parseInt(itemDetails[11]);
                        String autor = itemDetails[12];
                        allItems.add(new SapatilhaPremium(descricao, marca, itemId, transport, preco, desconto, num_donos, stock, tamanho, atacadores, cor, ano_colecao, autor));
                    }else if (itemId.startsWith("TN")) {
                        String tamanho = itemDetails[8];
                        String padrao = itemDetails[9];
                        allItems.add(new Tshirt(descricao, marca, itemId, transport, preco, desconto, num_donos, stock, tamanho, padrao));
                    } else if (itemId.startsWith("HN")) {
                        String dimensao = itemDetails[8];
                        int ano_colecao = Integer.parseInt(itemDetails[9]);
                        String material = itemDetails[10];
                        allItems.add(new Mala(descricao, marca, itemId, transport, preco, desconto, num_donos, stock, dimensao, ano_colecao, material));
                    } else if (itemId.startsWith("HP")) {
                        String dimensao = itemDetails[8];
                        int ano_colecao = Integer.parseInt(itemDetails[9]);
                        String material = itemDetails[10];
                        String autor = itemDetails[11];
                        allItems.add(new MalaPremium(descricao, marca, itemId, transport, preco, desconto, num_donos, stock, dimensao, ano_colecao, material, autor));
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
        return allItems;
    }

    public static String extractItem(String input) {
        String[] parts = input.split("->");

        if (parts.length > 1) {
            String itemDetails = parts[1];
            int lastCommaIndex = itemDetails.lastIndexOf(",");
            int nextArrowIndex = input.indexOf("->", input.indexOf("->") + 1);

            if (lastCommaIndex >= 0 && nextArrowIndex >= 0) {
                itemDetails = itemDetails.substring(0, lastCommaIndex);
            }

            return parts[0] + "->" + itemDetails;
        }

        return "";
    }


    public static void removeItemFromUserStock(String userEmail, String itemId) {
        File folder = new File("Files/stock/");
        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                try {
                    List<String> lines = new ArrayList<>();
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] values = line.split(":");

                        if (values[2].equals(itemId)) {
                            int stock = Integer.parseInt(values[7]);

                            if (stock == 1) {
                                continue; // skip this line
                            } else {
                                stock--;
                                values[7] = Integer.toString(stock);
                                line = String.join(":", values);
                                FileWriter historyWriter = new FileWriter("Files/buyhistory.txt", true);
                                historyWriter.write(userEmail + ":" + line + "\n");
                                historyWriter.close();
                            }
                        }
                        lines.add(line);
                    }
                    scanner.close();
                    FileWriter writer = new FileWriter(file);

                    for (String line : lines) {
                        writer.write(line + "\n");
                    }

                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}