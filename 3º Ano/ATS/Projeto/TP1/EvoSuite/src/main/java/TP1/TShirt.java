package TP1;

import java.time.LocalDate;

public class TShirt extends Artigo {
    public enum Tamanho {
        S, 
        M, 
        L, 
        XL
    }

    public enum Padrao {
        LISO, 
        RISCAS, 
        PALMEIRAS
    }

    private Tamanho tamanho;
    private Padrao padrao;

    public TShirt() {
        super();
        this.tamanho = null;
        this.padrao = null;
    }

    public TShirt(Tamanho tamanho, Padrao padrao, String tipo, Estado estado, int numeroDonos, Avaliação avaliacao, String descricao, String marca, String codigo, double precoBase, double correcaoPreco, String t) {
        super(tipo, estado, numeroDonos, avaliacao, descricao, marca, codigo, precoBase, correcaoPreco, t);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public TShirt(TShirt t) {
        super(t);
        this.tamanho = t.getTamanho();
        this.padrao = t.getPadrao();
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Padrao getPadrao() {
        return padrao;
    }

    public void setPadrao(Padrao padrao) {
        this.padrao = padrao;
    }

    public boolean equals(Object o) {
        if(this == o) 
            return true;
        if((o == null) || (this.getClass() != o.getClass())) 
            return false;
        TShirt t = (TShirt) o;
        return (this.tamanho == t.getTamanho() &&
                this.padrao == t.getPadrao());
    }



    public String toString(LocalDate data) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString(data));
        sb.append("Tamanho: ").append(this.tamanho).append("\n");
        sb.append("Padrao: ").append(this.padrao).append("\n");
        sb.append("Preço Final: ").append(this.precoFinal(data)).append("\n");
        return sb.toString();
    }

    public TShirt clone() {
        return new TShirt(this);
    }

    public double precoFinal(LocalDate data) {
        double precoFinal = super.getPrecoBase();
        if (this.padrao == Padrao.RISCAS || this.padrao == Padrao.PALMEIRAS) {
            if (super.getNumeroDonos() > 0){precoFinal = super.getPrecoBase() * 0.5;}
        }
            return precoFinal;
    }
}