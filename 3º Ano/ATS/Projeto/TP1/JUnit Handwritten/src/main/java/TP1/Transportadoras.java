package TP1;
import java.io.*;

public class Transportadoras implements Serializable{
    private String nome;
    private double imposto;
    private double lucro;
    private boolean premium; 
    private double volFaturacao;
    private int formula;


    public Transportadoras() {
        this.nome = "";
        this.imposto = 0.3;
        this.lucro = 0.10;
        this.premium = false;
        this.volFaturacao = 0.0;
        this.formula = 1;
    }

    public Transportadoras(String nome, double imposto, double lucro, boolean premium, double volFaturacao) {
        this.nome = nome;
        this.imposto = imposto;
        this.lucro = lucro;
        this.premium = premium;
        this.volFaturacao = volFaturacao;
        this.formula = 1;       // a transportadora é criada já com a formula pré definida
    }

    public Transportadoras(Transportadoras t) {
        this.nome = t.getNome();
        this.imposto = t.getImposto();
        this.lucro = t.getLucro();
        this.premium = t.isPremium();
        this.volFaturacao = t.getVolFaturacao();
        this.formula = t.getFormula();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }
    
    
    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
    
    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public double getVolFaturacao(){
        return this.volFaturacao;
    }

    public void setVolFaturacao(double vol){
        this.volFaturacao = vol;
    }

    public void addVolFaturacao(double vol){
        this.volFaturacao+=vol;
    }

    public int getFormula(){
        return this.formula;
    }

    public void setFormula(int formula){
        this.formula = formula;
    }





    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Imposto: ").append(this.imposto).append("\n");
        sb.append("Lucro: ").append(this.lucro).append("\n");
        sb.append("Premium: ").append(this.premium).append("\n");
        sb.append("# Formula: ").append(this.formula).append("\n");
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Transportadoras t = (Transportadoras) o;
        return this.nome.equals(t.getNome()) &&
                this.imposto == t.getImposto() &&
                this.lucro == t.getLucro() &&
                this.premium == t.isPremium() && 
                this.volFaturacao == t.getVolFaturacao() && 
                this.formula == t.getFormula();
    }

    public Transportadoras clone() {
        return new Transportadoras(this);
    }




    public double calculaPrecoExpedicao(Long dimensao){
        double preco = 0.0;
        int valorBase = 0;
        if  (dimensao > 4) valorBase = 50;
        if (dimensao > 1 && dimensao < 5) valorBase = 30;
        if (dimensao == 1) valorBase = 10;

        if (formula == 1){
            preco = (valorBase * this.lucro * (1 + this.imposto)) * 0.9;
        }
        else if (formula == 2){
            preco = (valorBase + this.lucro * this.imposto) * 0.5;
        }
        else if (this.formula == 3){
            preco = (1 + this.lucro) + this.imposto * valorBase;
        }
        else if (this.formula == 4){
            preco = this.imposto * 5 + valorBase - this.lucro * 5;
        }

        this.volFaturacao+= preco;
        return preco;
    }

    public void altera(Double lucro, Double imposto, int formula){
        this.lucro = lucro;
        this.imposto = imposto;
        this.formula = formula;
    }


}























