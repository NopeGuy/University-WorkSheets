package TP2;
import java.time.LocalDate;

public class Mala extends Artigo{
    private int dimensao; //em cm
    private String material;
    private int ano_colecao;
    private boolean premium;
    private float valorizacao;

    //Construtores
    public Mala() {
        super ();
        this.dimensao = 0;
        this.material = "";
        this.ano_colecao = 0;
        this.premium = false;
        this.valorizacao = 0;
    }
    public Mala(String descricao, String marca, Double preco_base, boolean uso, int estado_uso, int n_utilizadores, Long transportadora, Long vendedor, int dimensao, String material, int ano_colecao, boolean premium, float valorizacao) {
        super (descricao, marca, preco_base, uso, estado_uso, n_utilizadores, transportadora, vendedor);
        this.dimensao = dimensao;
        this.material = material;
        this.ano_colecao = ano_colecao;
        this.premium = premium;
        this.valorizacao = valorizacao;
    }
    public Mala(Mala m) {
        super (m);
        this.dimensao = m.getDimensao();
        this.material = m.getMaterial();
        this.ano_colecao = m.getAno_colecao();
        this.premium = m.isPremium();
        this.valorizacao = m.getValorizacao();
    }

    //Getters
    public int getDimensao() {
        return dimensao;
    }
    public String getMaterial() {
        return material;
    }
    public int getAno_colecao() {
        return ano_colecao;
    }
    public boolean isPremium() {
        return premium;
    }
    public float getValorizacao() {
        return valorizacao;
    }

    //Setters
    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setAno_colecao(int ano_colecao) {
        this.ano_colecao = ano_colecao;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public void setValorizacao(float valorizacao) {
        this.valorizacao = valorizacao;
    }

    @Override
    //Falta alterar para a mala
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mala:: {");
        sb.append(super.toString());
        sb.append(" Dimensão: ").append(this.dimensao);
        sb.append(" Material: ").append(this.material);
        sb.append(" Ano da Coleção: ").append(this.ano_colecao);
        sb.append(" Premium: ").append(this.premium);
        sb.append(" Valorização: ").append(this.valorizacao).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Mala m = (Mala) o;

        return (super.equals(m) && this.dimensao == m.getDimensao() && this.material.equals(m.getMaterial()) && this.ano_colecao == m.getAno_colecao() && this.premium == m.isPremium() && this.valorizacao == m.getValorizacao());
    }

    @Override
    public Mala clone (){
        return new Mala(this);
    }


    @Override
    public double preco_atual(LocalDate data){
        double preco = this.getPreco_base();
        if (this.premium) {
            int anos=0;
            if(this.getData_venda().equals(LocalDate.MIN) == true)
                anos = data.getYear() - this.ano_colecao;

            else
                anos = this.getData_venda().getYear() - this.ano_colecao;

            while(anos > 0){
                preco *= (1 + (this.getValorizacao() / 100));
                anos--;
            }

        }
        else if (!this.getNovo())
            preco -= preco * this.getN_utilizadores() / (this.dimensao * this.getEstado_uso()); //verificar fórmula a utilizar
        if (preco <= 0.01) preco = 0.01;
        return preco;
    }


}
