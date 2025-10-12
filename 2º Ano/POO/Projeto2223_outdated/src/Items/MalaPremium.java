package Items;

import Time.Data;
import Transportation.Transportadora;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static Transportation.Transportadora.readDatabaseTransportadora;

public class MalaPremium extends Mala{
    //malas premium, o preço sobre x% por ano
    private String autor;
    
    public MalaPremium(){
        super();
        this.autor = "";
    }

    public MalaPremium(MalaPremium malaPremium){
        super(malaPremium);
        this.autor = malaPremium.getAutor();
    }

    public MalaPremium(String descricao, String marca, String item_id, String transportadora, double preco, double desconto, int num_donos, int stock, String dimensao, int ano_colecao, String material, String autor) {
        super(descricao, marca, item_id,transportadora, preco, desconto, num_donos, stock, dimensao, ano_colecao, material);
        this.autor = autor;
    }

    //getter and setter

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public MalaPremium clone() {
        return new MalaPremium(this);
    }

    public void calculaPreco(){
        this.setDesconto(2*(Data.tempo.getYear()-this.getAno_colecao()));
        this.setPreco(this.getPreco()*(1+this.getDesconto())/100+this.taxaSatisfacao());
    }

    public static MalaPremium createPremiumHandbag() {
        Scanner scanner = new Scanner(System.in);

        String itemId = "HP" + (int) (Math.random() * 100000);
        System.out.print("Item ID will be: " + itemId+ "\n");

        System.out.print("Enter handbag description: ");
        String descricao = scanner.nextLine().trim();

        System.out.print("Enter handbag brand: ");
        String marca = scanner.nextLine().trim();


        List<Transportadora> transportes = readDatabaseTransportadora();
        Transportadora.printTransportadoras(transportes);
        String transportadora = "";
        while (true) {
            System.out.print("Enter transport company name: ");
            transportadora = scanner.nextLine().trim();
            boolean exists = false;
            for (Transportadora t : transportes) {
                if (t.getNome().equals(transportadora)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                break;
            } else {
                System.out.println("Transport company not found. Please enter a valid transport company.");
            }
        }

        System.out.print("Enter price: ");
        double preco = Double.parseDouble(scanner.nextLine().trim());

        double desconto = 0.0;

        System.out.print("Enter number of previous owners: ");
        int numDonos = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        while(stock == 0){
            System.out.println("Stock can't be 0");
            System.out.print("Enter stock: ");
            stock = Integer.parseInt(scanner.nextLine().trim());
        }

        System.out.print("Enter dimension: 1)small 2)medium 3)large: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        String dimensao = "";
        switch (choice) {
            case 1:
                dimensao = "small";
                break;
            case 2:
                dimensao = "medium";
                break;
            case 3:
                dimensao = "large";
                break;
            default:
                System.out.println("Invalid dimension. Setting to small.");
                dimensao = "small";
                break;
        }

        System.out.print("Enter year of collection: ");
        int anoColecao = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter material: 1)pvc 2)fake leather 3)leather: ");
        choice = Integer.parseInt(scanner.nextLine().trim());
        String material = "";
        switch (choice) {
            case 1:
                material = "pvc";
                break;
            case 2:
                material = "fake leather";
                break;
            case 3:
                material = "leather";
                break;
            default:
                System.out.println("Invalid dimension. Setting to pvc.");
                material = "pvc";
                break;
        }

        System.out.print("Enter handbag author: ");
        String autor = scanner.nextLine();

        return new MalaPremium(descricao, marca, itemId, transportadora, preco, desconto, numDonos, stock, dimensao, anoColecao, material, autor);
    }

    public static void printPremiumHandbagToFile(MalaPremium malapre, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(malapre.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Handbag to file.");
        }
    }

    public static void printPremiumHandbagToFileHistory(String userEmail, MalaPremium malapre, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(userEmail + ":" + malapre.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Handbag to file history.");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Premium ").append(super.toString()).append(", Autor: ").append(this.autor);
        return sb.toString();
    }
    
    @Override
    public String toString2() {
        return super.toString2() + ":" + this.autor;
    }
}
