package Items;


import Transportation.Transportadora;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static Transportation.Transportadora.readDatabaseTransportadora;

public class Tshirt extends Artigo{
    private String tamanho;
    private String padrao; //pode ser lisa, riscas ou palmeiras

    //Lisas nunca têm desconto, as restantes têm 50% de desconto se forem usadas

    //construtores
    public Tshirt(){
        super();
        this.tamanho = "";
        this.padrao = "";
    }

    public Tshirt(Tshirt tshirt){
        super(tshirt);
        this.tamanho = tshirt.getTamanho();
        this.padrao = tshirt.getpadrao();
    }

    public Tshirt(String descricao, String marca, String item_id,String transportadora, double preco, double desconto, int num_donos,int stock, String tamanho, String padrao) {
        super(descricao, marca, item_id,transportadora, preco, desconto, num_donos, stock);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    //getters

    public String getTamanho() {
        return tamanho;
    }

    public String getpadrao() {
        return padrao;
    }

    //setters

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public void setpadrao(String padrao) {
        this.padrao = padrao;
    }

    //clone

    public Tshirt clone(){
        return new Tshirt(this);
    }
    //tostring

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Tshirt-> ").append(super.toString()).append("Tamanho: ").append(this.tamanho).append(", Padrao: ").append(this.padrao);
        return sb.toString();
    }

    public String toString2(){
        return super.toString2() + ":" + this.tamanho + ":" + this.padrao;
    }
    //equasls

    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())) return false;
        Tshirt tshirt = (Tshirt) o;
        return (super.equals(tshirt) && this.tamanho.equals(tshirt.getTamanho()) && this.padrao.equals(tshirt.getpadrao()));
    }

    //metodo desconto

    public void calculaPreco() {
        if (this.getNum_donos() > 0) {
            if (this.padrao.equals("lisa")) this.setDesconto(0);
            else this.setDesconto(50);
            this.setPreco(this.getPreco() * (1 - this.getDesconto() / 100)+this.taxaSatisfacao());
        }
    }

    public static Tshirt createTshirt() {
        Scanner scanner = new Scanner(System.in);

        String itemId = "TN" + (int) (Math.random() * 100000);
        System.out.print("Item ID will be: " + itemId + "\n");

        System.out.print("Enter t-shirt description: ");
        String descricao = scanner.nextLine().trim();

        System.out.print("Enter t-shirt brand: ");
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

        System.out.print("Enter a shirt size: 1)S, 2)M, 3)L, 4)XL: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        String tamanho = "";
        switch (choice) {
            case 1:
                tamanho = "S";
                break;
            case 2:
                tamanho = "M";
                break;
            case 3:
                tamanho = "L";
                break;
            case 4:
                tamanho = "XL";
                break;
            default:
                System.out.println("Invalid dimension. Setting to M.");
                tamanho = "M";
                break;
        }

        System.out.print("Enter a shirt pattern: 1)plain, 2)stripes, 3)palm trees: ");
        choice = Integer.parseInt(scanner.nextLine().trim());
        String padrao = "";
        switch (choice) {
            case 1:
                padrao = "plain";
                break;
            case 2:
                padrao = "stripes";
                break;
            case 3:
                padrao = "palm trees";
                break;
            default:
                System.out.println("Invalid input. Setting to plain.");
                tamanho = "plain";
                break;
        }

        return new Tshirt(descricao, marca, itemId, transportadora, preco, desconto, numDonos, stock, tamanho, padrao);
    }

    public static void printTshirtToFile(Tshirt tshirt, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(tshirt.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print T-Shirt to file.");
        }
    }

    public static void printTshirtToFileHistory(String userEmail, Tshirt tshirt, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(userEmail + ":" + tshirt.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print T-Shirt to file history.");
        }
    }
}
