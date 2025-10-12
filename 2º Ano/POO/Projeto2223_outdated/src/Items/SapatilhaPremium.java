package Items;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import Time.Data;
import Transportation.Transportadora;

import static Transportation.Transportadora.readDatabaseTransportadora;

public class SapatilhaPremium extends Sapatilha{
    private String autor;
    //sapatilhas premium têm autores reconhecidos que aumentam o preço com o passar do anos

    public SapatilhaPremium(){
        super();
        this.autor = "";
    }

    public SapatilhaPremium(SapatilhaPremium sapatilhaPremium){
        super(sapatilhaPremium);
        this.autor = sapatilhaPremium.getAutor();
    }

    public SapatilhaPremium(String descricao, String marca, String item_id,String transportadora, double preco, double desconto, int num_donos,int stock, int tamanho, boolean atacadores, String cor, int ano_colecao, String autor) {
        super(descricao, marca, item_id,transportadora, preco, desconto, num_donos, stock, tamanho, atacadores, cor, ano_colecao);
        this.autor = autor;
    }

    //getter and setter

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public SapatilhaPremium clone() {
        return new SapatilhaPremium(this);
    }

    public void calculaPreco(){
        this.setDesconto(2*(Data.tempo.getYear()-this.getAno_colecao()));
        this.setPreco(this.getPreco()*(1+this.getDesconto()/100)+this.taxaSatisfacao());
    }

    public static SapatilhaPremium createPremiumShoe() {
        Scanner scanner = new Scanner(System.in);

        String itemId = "SP" + (int) (Math.random() * 100000);
        System.out.print("Item ID will be: " + itemId + "\n");

        System.out.print("Enter shoe description: ");
        String descricao = scanner.nextLine().trim();

        System.out.print("Enter shoe brand: ");
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

        System.out.print("Enter shoe size: ");
        int tamanho = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter shoe laces 1)Has 2)Doesn't has them: ");
        boolean atacadoresBool = true;
        int atacadores = Integer.parseInt(scanner.nextLine().trim());
        if(atacadores == 2){ atacadoresBool = false;}

        System.out.print("Enter shoe color(lower case only): ");
        String cor = scanner.nextLine().trim();

        System.out.print("Enter year of collection: ");
        int ano_colecao = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.print("Enter shoe author: ");
        String autor = scanner.nextLine();

        return new SapatilhaPremium(descricao, marca, itemId, transportadora, preco, desconto, numDonos, stock, tamanho, atacadoresBool, cor, ano_colecao, autor);
    }

    public static void printPremiumShoeToFile(SapatilhaPremium sapatilhapre, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(sapatilhapre.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Shoe to file.");
        }
    }

    public static void printPremiumShoetoFileHistory(String userEmail, SapatilhaPremium sapatilhapre, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(userEmail + ":" + sapatilhapre.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Shoe to file history.");
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
