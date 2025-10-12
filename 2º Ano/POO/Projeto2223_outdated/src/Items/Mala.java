package Items;

import Time.Data;
import Transportation.Transportadora;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import static Transportation.Transportadora.readDatabaseTransportadora;

public class Mala extends Artigo {
    private String dimensao; //pode ser pequena média ou grande
    private int ano_colecao;
    private String material;

    //desconto é maior se a mala for maior

    //construtores
    public Mala(){
        super();
        this.dimensao = "";
        this.ano_colecao = 0;
        this.material = "";
    }

    public Mala(Mala malas){
        super(malas);
        this.dimensao = malas.getDimensao();
        this.ano_colecao = malas.getAno_colecao();
        this.material = malas.getMaterial();
    }

    public Mala(String descricao, String marca, String item_id, String transportadora, double preco, double desconto, int num_donos,int stock, String dimensao, int ano_colecao, String material) {
        super(descricao, marca, item_id,transportadora, preco, desconto, num_donos, stock);
        this.dimensao = dimensao;
        this.ano_colecao = ano_colecao;
        this.material = material;
    }

    //getters
    public String getDimensao() {
        return dimensao;
    }

    public int getAno_colecao() {
        return ano_colecao;
    }

    public String getMaterial() {
        return material;
    }
    //setters

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

    public void setAno_colecao(int ano_colecao) {
        this.ano_colecao = ano_colecao;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    //clone

    public Mala clone(){
        return new Mala(this);
    }

    //tostring

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Mala-> ").append(super.toString()).append("Dimensao: ").append(this.dimensao).append(", Ano da colecao: ").append(this.ano_colecao).append(", Material: ").append(this.material);
        return sb.toString();
    }

    public String toString2(){
        return super.toString2() + ":" + this.dimensao + ":" + this.ano_colecao + ":" + this.material;
    }
    //equals

    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Mala)) return false;
        Mala m = (Mala) o;
        return dimensao.equals(m.dimensao) &&
                ano_colecao == m.ano_colecao &&
                material.equals(m.material);
    }

    //Desconto descontoMala dá set ao desconto, calculaPrecoMala calcula o novo preço

    public void calculaDesconto(){
        double dDim,dAno,dMat;
        if(this.getNum_donos()>0) {
            dAno=(Data.tempo.getYear()-this.getAno_colecao());
            if (this.getDimensao().equals("small")) dDim=20;
            else if (this.getDimensao().equals("medium")) dDim=10;
            else dDim=0;
            if(this.getMaterial().equals("pvc")) dMat=20;
            else if(this.getMaterial().equals("fake leather")) dMat=10;
            else dMat=0;
            this.setDesconto(dAno*2+dDim+dMat);
        }
    }
    //calcula o preço final (apenas dependente do tamanho da mala)
    public void calculaPreco(){
        this.calculaDesconto();
        if(this.getDesconto()>=100) this.setPreco(0);
        else this.setPreco(this.getPreco()*(1-this.getDesconto()/100)+this.taxaSatisfacao());
    }

    public static Mala createHandbag() {
        Scanner scanner = new Scanner(System.in);

        String itemId = "HN" + (int) (Math.random() * 100000);
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

        return new Mala(descricao, marca, itemId, transportadora, preco, desconto, numDonos, stock, dimensao, anoColecao, material);
    }

    public static void printHandbagToFile(Mala mala, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(mala.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Handbag to file.");
        }
    }

    public static void printHandbagToFileHistory(String userEmail, Mala mala, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(userEmail + ":" + mala.toString2());
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to print Handbag to file history.");
        }
    }
}
