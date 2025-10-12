package TP2;
import java.time.LocalDate;

public class Tshirt extends Artigo {

    private Tamanho tamanho;
    private Padrao padrao;
    

    // Construtores
    public Tshirt() {
        super();
        this.tamanho = null;
        this.padrao = null;
    }

    public Tshirt(String descricao, String marca, double preco_base, boolean uso, int estado_uso, int n_utilizadores, Long transportadora, Long vendedor, Tamanho tamanho, Padrao padrao) {
        super(descricao, marca, preco_base, uso, estado_uso, n_utilizadores, transportadora, vendedor);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public Tshirt(Tshirt t) {
        super(t);
        this.tamanho = t.getTamanho();
        this.padrao = t.getPadrao();
    }

    // Getters
    public Tamanho getTamanho() {
        return tamanho;
    }

    public Padrao getPadrao() {
        return padrao;
    }

    // Setters
    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public void setPadrao(Padrao padrao) {
        this.padrao = padrao;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("T-shirt:: {");
        sb.append(super.toString());
        sb.append(" Tamanho: ").append(this.tamanho.toString());
        sb.append(" Padrão: ").append(this.padrao.toString()).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Tshirt t = (Tshirt) o;

        return (super.equals(t) && this.tamanho.equals(t.getTamanho()) && this.padrao.equals(t.getPadrao()));
    }

    @Override
    public Tshirt clone() {
        return new Tshirt(this);
    }

    @Override
    public double preco_atual(LocalDate data){
        if ((!this.padrao.equals(Padrao.liso)) && (!this.getNovo()))
            return (this.getPreco_base()*0.5);
        else return (this.getPreco_base());
    }

}
