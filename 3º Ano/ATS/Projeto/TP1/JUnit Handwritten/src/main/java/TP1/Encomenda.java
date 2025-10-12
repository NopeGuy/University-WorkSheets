package TP1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class Encomenda implements Serializable{
    public enum Dimensao_Embalagem {
        GRANDE, // + de 5 artigos
        MEDIO,  // 2 a 5 artigos
        PEQUENO // 1 artigo
    }
    public enum Estado_Encomenda {
        PENDENTE,
        FINALIZADA,
        EXPEDIDA,
        DEVOLVIDA
    } 
    private static int nextID = 1;
    private String dono;
    private int id;
    private List<Artigo> artigos;
    private Dimensao_Embalagem embalagem;
    private double precoFinal;
    private double custosExpedicao;                 // Dependem da transportadora, na classe Artigo vamos ter de adicionar a transportadora depois
    private Estado_Encomenda estado;
    private LocalDate dataCriacao;
    private int tamanho;  
    private LocalDate prazoLimite; 
    private Map<Integer,String> vendedores;

    public Encomenda() {
        this.id = nextID++;
        this.dono = "";
        this.artigos = new ArrayList<>();
        this.embalagem = null;
        this.precoFinal = 0;
        this.custosExpedicao = 0;
        this.estado = Estado_Encomenda.PENDENTE;
        this.dataCriacao = LocalDate.now();
        this.tamanho = 0;
        prazoLimite = dataCriacao.plusDays(2);
        this.vendedores = new HashMap<>();
    }

    public Encomenda(String dono, List<Artigo> artigos, double custosExpedicao, LocalDate dataCriacao, Map<Integer,String> vendedores) {
        this.precoFinal = 0.0;
        setArtigos(artigos);
        this.id = nextID++;
        this.dono = dono;
        this.custosExpedicao = custosExpedicao;
        this.estado = Estado_Encomenda.EXPEDIDA;
        this.dataCriacao = dataCriacao;
        this.tamanho = artigos.size();
        if (this.tamanho > 5) this.embalagem = Dimensao_Embalagem.GRANDE;
        if (this.tamanho > 1 && this.tamanho <= 5) this.embalagem = Dimensao_Embalagem.MEDIO;
        if (this.tamanho == 1) this.embalagem = Dimensao_Embalagem.PEQUENO;
        this.prazoLimite = dataCriacao.plusDays(2);
        this.vendedores  = vendedores; 

        for (Artigo a : artigos){

            if (a instanceof TShirt){
                TShirt t = (TShirt) a;
                precoFinal += t.precoFinal(dataCriacao);
            }
            else if (a instanceof Sapatilhas){
                Sapatilhas s = (Sapatilhas) a;
                precoFinal += s.precoFinal(dataCriacao);
            }
            else if (a instanceof Malas){
                Malas m = (Malas) a;
                precoFinal += m.precoFinal(dataCriacao);
            }
            if (a.getEstado() == Artigo.Estado.NOVO){this.precoFinal += 0.5;}
            else{this.precoFinal += 0.25;}
        }
        this.precoFinal += this.custosExpedicao;
        
    }

    public Encomenda(Encomenda enc) {
        this.id = enc.getId();
        this.artigos = enc.getArtigos();
        this.embalagem = enc.getEmbalagem();
        this.precoFinal = enc.getPrecoFinal();
        this.custosExpedicao = enc.getCustosExpedicao();
        this.estado = enc.getEstado();
        this.dataCriacao = enc.getDataCriacao();
        this.tamanho =  enc.getTamanho();
        this.prazoLimite = enc.getPrazoLimite();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDono(){
        return this.dono;
    }

    public void setDono(String dono){
        this.dono = dono;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public void setTamanho(int t){
        this.tamanho = t;
    }


    public List<Artigo> getArtigos() {
    ArrayList<Artigo> res = new ArrayList<>();
        for(Artigo a: this.artigos){
            res.add(a.clone());
        }
        return res;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = new ArrayList<>(artigos.size());
        for(Artigo a: artigos){
            this.artigos.add(a.clone());
        }
    }

    public Dimensao_Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Dimensao_Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public double getCustosExpedicao() {
        return custosExpedicao;
    }

    public void setCustosExpedicao(double custosExpedicao) {
        this.custosExpedicao = custosExpedicao;
    }

    public Estado_Encomenda getEstado() {
        return estado;
    }

    public void setEstado(Estado_Encomenda estado) {
        this.estado = estado;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getPrazoLimite(){
        return this.prazoLimite;
    }

    public void setPrazoLimite(LocalDate prazo){
        this.prazoLimite = prazo;
    }


    public Map<Integer,String> getVendedores(){
        return new HashMap<>(vendedores);
    }

    public void setVendedores(Map<Integer,String> vendedores){
        this.vendedores = new HashMap<>(vendedores);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Encomenda enc = (Encomenda) o;
        return  this.id == enc.getId() &&
                this.artigos.equals(enc.getArtigos()) &&
                this.embalagem == enc.getEmbalagem() &&
                this.precoFinal == enc.getPrecoFinal() &&
                this.custosExpedicao == enc.getCustosExpedicao() &&
                this.estado == enc.getEstado() &&
                this.dataCriacao.equals(enc.getDataCriacao());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==========  Encomenda  ==========\n");
        sb.append("ID da Encomenda: " + id + "\n");
        sb.append("Encomenda criada em: " + this.dataCriacao + "\n");
        sb.append("Prazo Limite: " + this.prazoLimite + "\n");
        sb.append("==========   Artigos   ==========\n");
        for (Artigo artigo : artigos) {
            sb.append(artigo.toString(this.dataCriacao) + "\n");
        }
        sb.append("========== Fim Artigos ==========\n");
        sb.append("Dimensão da embalagem: " + embalagem + "\n");
        sb.append("Custo Expedição: " + custosExpedicao + "\n");
        sb.append("Preço final: " + precoFinal + "\n");
        sb.append("Estado da encomenda: " + estado + "\n");
        return sb.toString();
    }

    public Encomenda clone() {
        return new Encomenda(this);
    }
    
}