package TP2;
import java.time.LocalDate;

public class Sapatilha extends Artigo{
    private int tamanho;
    private boolean atacadores; // possui ou não
    private String cor;
    private int anoColecao; // data de lançamento
    private boolean premium;
    private double desconto; // em percentagem

    // Construtores
    public Sapatilha() {
        super();
        this.tamanho = 0;
        this.atacadores = false;
        this.cor = "";
        this.anoColecao = 0;
        this.premium = false;
        this.desconto = 0;
    }

    public Sapatilha(String descricao, String marca, Double preco_base, boolean uso, int estado_uso, int n_utilizadores,
            Long transportadora, Long vendedor, int tamanho, boolean atacadores, String cor, int anoColecao,
            boolean premium, double desconto) {
        super(descricao, marca, preco_base, uso, estado_uso, n_utilizadores, transportadora, vendedor);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.anoColecao = anoColecao;
        this.premium = premium;
        this.desconto = desconto;
    }

    public Sapatilha(Sapatilha s) {
        super(s);
        this.tamanho = s.getTamanho();
        this.atacadores = s.isAtacadores();
        this.cor = s.getCor();
        this.anoColecao = s.getAnoColecao();
        this.premium = s.isPremium();
        this.desconto = s.getDesconto();
    }

    // Getters
    public int getTamanho() {
        return tamanho;
    }

    public boolean isAtacadores() {
        return atacadores;
    }

    public String getCor() {
        return cor;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public boolean isPremium() {
        return premium;
    }

    public double getDesconto() {
        return desconto;
    }

    // Setters
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setAnoColecao(int dataColecao) {
        this.anoColecao = dataColecao;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sapatilhas:: {");
        sb.append(super.toString());
        sb.append(" Tamanho: ").append(this.tamanho);
        sb.append(" Atacadores: ").append(this.atacadores);
        sb.append(" Cor: ").append(this.cor);
        sb.append(" Data da Coleção: ").append(this.anoColecao);
        sb.append(" Premium: ").append(this.premium);
        sb.append(" Desconto: ").append(this.desconto).append("}");
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Sapatilha s = (Sapatilha) o;

        return (super.equals(s) && this.tamanho == s.getTamanho() && this.atacadores == s.isAtacadores()
                && this.cor.equals(s.getCor()) && this.anoColecao == s.getAnoColecao()
                && this.premium == s.isPremium() && this.desconto == s.getDesconto());
    }

    @Override
    public Sapatilha clone() {
        return new Sapatilha(this);
    }

    @Override
    public double preco_atual(LocalDate data) {
        double preco = this.getPreco_base();
        if (this.premium)
            if (this.getData_venda().equals(LocalDate.MIN) == true) {
                preco += preco * (data.getYear() - this.anoColecao) / 10;
            } else {
                preco += preco * (this.getData_venda().getYear() - this.anoColecao) / 10;
            }
        else if (!this.getNovo()) {
            preco -= preco * this.getN_utilizadores() / (10 * this.getEstado_uso()); // verificar fórmula a utilizar
            preco *= (100 - this.desconto) / 100;
        } else if (this.tamanho > 45 && this.getNovo())
            preco *= (100 - this.desconto) / 100;
        if (preco <= 0.01)
            preco = 0.01;
        return preco;
    }
}
