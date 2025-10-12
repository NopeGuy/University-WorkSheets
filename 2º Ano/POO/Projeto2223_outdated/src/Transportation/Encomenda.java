package Transportation;

import Items.*;
import Users.Purchases;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static Time.Data.tempo;

public class Encomenda {
    private String encomendaId;
    private Collection<Artigo> colecao;
    private int dimensao;
    private double preco_final;
    private String estado;
    private LocalDate data_criacao;
    private String buyerEmail;

    // Construtor
    public Encomenda(Collection<Artigo> colecao, int dimensao, double preco_final, String estado, LocalDate data_criacao,String buyerEmail, String id) {
        this.colecao = new ArrayList<>(colecao);
        this.dimensao = dimensao;
        this.preco_final = preco_final;
        this.estado = estado;
        this.data_criacao = data_criacao;
        this.buyerEmail=buyerEmail;
        this.encomendaId=id;
    }
    // Construtor de cópia
    public Encomenda(Encomenda encomenda) {
        this(encomenda.colecao, encomenda.dimensao, encomenda.preco_final, encomenda.estado, encomenda.data_criacao,encomenda.buyerEmail, encomenda.encomendaId);
    }

    // Construtor vazio
    public Encomenda() {
        this(new ArrayList<>(), 0, 0.0, "", LocalDate.now(),"", "");
    }

    public static ArrayList<Encomenda> readPackagesFromFile() {
        ArrayList<Encomenda> encomendas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Files/orders.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";");

                String[] items = parts[1].split("|");
                String buyerEmail = parts[0];
                String ID = parts[2];
                int dimensao = Integer.parseInt(parts[3]);
                double preco_final = Double.parseDouble(parts[4]);
                String estado = parts[5];
                LocalDate data = LocalDate.parse(parts[6]);

                Collection<Artigo> artigos = new ArrayList<>();

                Encomenda encomenda = new Encomenda(artigos, dimensao, preco_final, estado, data, buyerEmail, ID);
                encomendas.add(encomenda);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encomendas;
    }

    public static void removePackageFromFile(String packageID) {
        File inputFile = new File("Files/orders.txt");
        File tempFile = new File("Files/temp.txt");
        boolean encontrou=false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 3 && parts[2].equals(packageID) && parts[5].equals("Em transito")){
                    System.out.println("\nPackage removed.\n");
                    encontrou=true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading/writing the file: " + e.getMessage());
            return;
        }

        if (!inputFile.delete()) {
            System.err.println("Could not delete the input file.");
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.err.println("Could not rename the temporary file.");
        }
        if(!encontrou) System.out.println("\nValid package not found.");
    }


    public void setColecao(Collection<Artigo> colecao) {
        if (colecao == null) {
            throw new IllegalArgumentException("A coleção de artigos não pode ser nula.");
        }
        this.colecao = new ArrayList<Artigo>();

        for(Artigo a : colecao){
            this.colecao.add(a.clone());
        }
    }

    // Métodos de acesso
    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.data_criacao = dataCriacao;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrecoFinal(double preco_final) {
        this.preco_final = preco_final;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }
    public void setEncomendaId(String encomendaId) {
        this.encomendaId = encomendaId;
    }


    public String getEncomendaId() {
        return encomendaId;
    }

    public Collection<Artigo> getColecao() {
        ArrayList<Artigo> ret = new ArrayList<Artigo>();
        for (Artigo a : this.colecao)
            ret.add(a.clone());
        return ret;
    }

    public int getDimensao() {
        return dimensao;
    }

    public double getPreco_final() {
        return preco_final;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public String getBuyerEmail(){return buyerEmail;}

    // Métodos de modificação

    public double calcularPrecoFinal(ArrayList<Artigo> colecao){
        Transportadora t =  new Transportadora();
        double precoEnvio = 0.0;
        double total = 0.0;
        List<Transportadora> transportes = t.readDatabaseTransportadora();

        for (Artigo artigo : colecao) {
            for (int i = 0; i < transportes.size(); i++){
                t = transportes.get(i);
                if (artigo.getTransportadora().equals(t.getNome())){
                    t.setTamanho(t.getTamanho() +1) ;
                }
            }
            if(artigo instanceof Mala) ((Mala) artigo).calculaPreco();
            if(artigo instanceof MalaPremium) ((MalaPremium) artigo).calculaPreco();
            if(artigo instanceof Sapatilha) ((Sapatilha) artigo).calculaPreco();
            if(artigo instanceof SapatilhaPremium) ((SapatilhaPremium) artigo).calculaPreco();
            if(artigo instanceof Tshirt) ((Tshirt) artigo).calculaPreco();
            total += artigo.getPreco();
        }

        for (Transportadora trans : transportes){
            precoEnvio += trans.calculaTaxa(trans);
        }

        total += precoEnvio;


        return total;
    }
    public void adicionarArtigo(Artigo artigo) {
        if(artigo.getStock()>0){
        if(colecao.add(artigo.clone())){
            this.dimensao++;
            this.preco_final += artigo.getPreco();
            artigo.setStock(artigo.getStock()-1);
            }
        }
    }

    public void removerArtigo(Artigo artigo) {
        if(colecao.remove(artigo)){
            this.dimensao--;
            this.preco_final -= artigo.getPreco();
            artigo.setStock(artigo.getStock()+1);
        }
    }

    public static void addEncomendaToFile(String userEmail, Encomenda encomenda) {
        try {
            FileWriter writer = new FileWriter("Files/orders.txt", true);
            StringBuilder collectionLine = new StringBuilder();
            collectionLine.append("|");
            for (Artigo a : encomenda.getColecao()) {
                collectionLine.append(a.toString2()).append("|");
            }
            String orderLine = userEmail + ";" + "[" + collectionLine + "]" + ";" + encomenda.getEncomendaId() + ";"
                    + encomenda.getDimensao() + ";" + encomenda.getPreco_final() + ";" + encomenda.getEstado() + ";"
                    + encomenda.getData_criacao() + "\n";
            writer.write(orderLine);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void atualizarEstadoEncomendas(){
        String inputFile = "Files/orders.txt";
        LocalDate twoDaysAgo = tempo.minusDays(2);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Em transito")) {
                    String[] parts = line.split(";");
                    String dateString = parts[6];
                    LocalDate dataCriacao = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    if (dataCriacao.isBefore(twoDaysAgo)) {
                        line = line.replace("Em transito", "Entregue");
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(inputFile)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos sobrescritos
    @Override
    public Encomenda clone() {
        try {
            Encomenda clone = (Encomenda) super.clone();
            clone.colecao = new ArrayList<>(colecao);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Encomenda)) return false;
        Encomenda e = (Encomenda) o;
        return Objects.equals(colecao, e.colecao) &&
                this.dimensao==e.dimensao &&
                Double.compare(preco_final, e.preco_final) == 0 &&
                Objects.equals(estado, e.estado) &&
                Objects.equals(data_criacao, e.data_criacao);
    }
    @Override
    public String toString() {
        return  ";" + colecao +
                ";" + encomendaId +
                ";" + dimensao +
                ";" + preco_final +
                ";" + estado +
                ";" + data_criacao;
    }
    public String toString2() {
        return "Encomenda{" +
                "ID: " + encomendaId +
                ", colecao: " + colecao +
                ", dimensao: " + dimensao +
                ", preco_final: " + preco_final +
                ", estado: " + estado +
                ", data_criacao: " + data_criacao +
                '}';
    }
}
