package TP1;

import java.io.*;
import java.time.LocalDate;

public abstract class Artigo implements Serializable{
    // Atributos
    public enum Estado{
        NOVO,
        USADO
    }
    public enum Avaliação{
        IMPECÁVEL,
        BOM,
        SATISFATÓRIO,
    }
    private static int nextID = 1;
    private int id;                     // Id do Produto
    private String tipo;                // Tipo do Produto
    private Estado estado;              // Estado do Produto
    private int numeroDonos;            // Número de Donos
    private Avaliação avaliacao;
    private String descricao;           // Descrição do Produto
    private String marca;               // Marca do Produto
    private String codigo;              // Código Alfa-Númerico
    private double precoBase;           // Preço Base
    private double correcaoPreco;       // Correção do Preço
    private String transportadora;      // Transportadora

    public Artigo(){
        this.id = nextID++;
        this.tipo = "";
        this.estado = null;
        this.numeroDonos = 0;
        this.avaliacao = null;
        this.descricao = "";
        this.marca = "";
        this.codigo = "";
        this.precoBase = 0;
        this.correcaoPreco = 0;
        this.transportadora = null;
    }

    public Artigo(String tipo, Estado estado, int numeroDonos, Avaliação avaliacao, String descricao, String marca, String codigo, double precoBase, double correcaoPreco,String transportadora) {
        this.id = nextID++;
        this.tipo = tipo;
        this.estado = estado;
        if(estado == Estado.NOVO){
            this.numeroDonos = 0;
            this.avaliacao = null;
        } else{
            this.numeroDonos = numeroDonos;
            this.avaliacao = avaliacao;
        }
        this.descricao = descricao;
        this.marca = marca;
        this.codigo = codigo;
        this.precoBase = precoBase;
        this.correcaoPreco = correcaoPreco;
        this.transportadora = transportadora;
    }

    public Artigo(Artigo a){
        this.id = a.getId();
        this.tipo = a.getTipo();
        this.estado = a.getEstado();
        this.numeroDonos = a.getNumeroDonos();
        this.avaliacao = a.getAvaliacao();
        this.descricao = a.getDescricao();
        this.marca = a.getMarca();
        this.codigo = a.getCodigo();
        this.precoBase = a.getPrecoBase();
        this.correcaoPreco = a.getCorrecaoPreco();
        this.transportadora = a.getTransportadora();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Avaliação getAvaliacao(){
        return avaliacao;
    }

    public void setAvaliacao(Avaliação avaliacao){
        this.avaliacao = avaliacao;
    }

    public int getNumeroDonos() {
        return numeroDonos;
    }

    public void setNumeroDonos(int numeroDonos) {
        this.numeroDonos = numeroDonos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public double getCorrecaoPreco() {
        return correcaoPreco;
    }

    public void setCorrecaoPreco(double correcaoPreco) {
        this.correcaoPreco = correcaoPreco;
    }


    public String getTransportadora(){
        return this.transportadora;
    }

    public void setTransportadora(String t){
        this.transportadora = t;
    }




    public boolean equals(Object o){
        if(this == o) 
            return true;
        if((o == null) || (this.getClass() != o.getClass())) 
            return false;
        Artigo a = (Artigo) o;
        return (this.id == a.getId() &&
                this.tipo.equals(a.getTipo()) &&
                this.estado == a.getEstado() &&
                this.numeroDonos == a.getNumeroDonos() &&
                this.descricao.equals(a.getDescricao()) &&
                this.marca.equals(a.getMarca()) &&
                this.codigo.equals(a.getCodigo()) &&
                this.precoBase == a.getPrecoBase() &&
                this.correcaoPreco == a.getCorrecaoPreco()) &&
                this.transportadora.equals(a.getTransportadora());
    }

    public String toString(LocalDate data) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id).append("\n");
        sb.append("Tipo: ").append(this.tipo).append("\n");
        sb.append("Estado: ").append(this.estado).append("\n");
        sb.append("Número de Donos: ").append(this.numeroDonos).append("\n");
        sb.append("Avaliação: ").append(this.avaliacao).append("\n");
        sb.append("Descrição: ").append(this.descricao).append("\n");
        sb.append("Marca: ").append(this.marca).append("\n");
        sb.append("Código: ").append(this.codigo).append("\n");
        sb.append("Preço Base: ").append(this.precoBase).append("\n");
        sb.append("Correção do Preço: ").append(this.correcaoPreco).append("\n");
        sb.append("Transportadora: ").append(this.transportadora).append("\n");
        return sb.toString();
    }



    public abstract double precoFinal(LocalDate data);

    public abstract Artigo clone();
}