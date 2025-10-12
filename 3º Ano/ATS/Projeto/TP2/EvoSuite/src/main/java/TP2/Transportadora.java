package TP2;
import java.io.Serializable;

public class Transportadora implements Serializable {

    // Variável de classe
    private static long codigo = 1;

    // Métodos de classe
    public static long getCodigo() {
        return codigo;
    }

    // :)
    public static void setCodigo(long codigo) {
        Transportadora.codigo = codigo;
    }

    public static long criaCodigo() {
        return codigo++;
    }

    private long id;
    private String nome;
    private double valor_pequenas;
    private double valor_medio;
    private double valor_grande;
    private double impostos;
    private double margemlucro;
    private double totalLucro;
    private boolean premium;
    private int formula; // tem de ser 1,2,3

    // Construtor vazio
    public Transportadora() {
        this.id = criaCodigo();
        this.nome = "Transportadora Invalida";
        this.valor_pequenas = 0;
        this.valor_medio = 0;
        this.valor_grande = 0;
        this.impostos = 0;
        this.margemlucro = 0;
        this.totalLucro = 0;
        this.premium = false;
        this.formula = 0;

    }

    // Construtor parametrizado
    public Transportadora(String nome, double valor_pequenas, double valor_medio, double valor_grande, double impostos,
            double margemlucro, boolean premium, int formula) {
        this.id = criaCodigo();
        this.nome = nome;
        this.valor_pequenas = valor_pequenas;
        this.valor_medio = valor_medio;
        this.valor_grande = valor_grande;
        this.impostos = impostos;
        this.margemlucro = margemlucro;
        this.totalLucro = 0;
        this.premium = premium;
        this.formula = formula;
        // this.totalLucro = totalLucro;
    }

    // Construtor cópia
    public Transportadora(Transportadora transportadora) {
        this.id = transportadora.getId();
        this.nome = transportadora.getNome();
        this.valor_grande = transportadora.getValor_grande();
        this.valor_medio = transportadora.getValor_medio();
        this.valor_pequenas = transportadora.getValor_pequenas();
        this.margemlucro = transportadora.getMargemlucro();
        this.impostos = transportadora.getImpostos();
        this.totalLucro = transportadora.getTotalLucro();
        this.premium = transportadora.isPremium();
        this.formula = transportadora.getFormula();

    }

    // Métodos getters e setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor_pequenas(double valor_pequenas) {
        this.valor_pequenas = valor_pequenas;
    }

    public void setValor_medio(double valor_medio) {
        this.valor_medio = valor_medio;
    }

    public void setValor_grande(double valor_grande) {
        this.valor_grande = valor_grande;
    }

    public void setMargemlucro(double margemlucro) {
        this.margemlucro = margemlucro;
    }

    public void setTotalLucro(double totalLucro) {
        this.totalLucro += totalLucro;
    }

    public void setImpostos(double impostos) {
        this.impostos = impostos;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }

    public double getImpostos() {
        return impostos;
    }

    public double getMargemlucro() {
        return margemlucro;
    }

    public double getValor_grande() {
        return valor_grande;
    }

    public double getValor_medio() {
        return valor_medio;
    }

    public double getValor_pequenas() {
        return valor_pequenas;
    }

    public String getNome() {
        return nome;
    }

    public long getId() {
        return id;
    }

    public double getTotalLucro() {
        return totalLucro;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public int getFormula() {
        return formula;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Transportadora t = (Transportadora) obj;

        return (this.id == t.getId() && this.nome.equals(t.getNome()) && this.valor_grande == t.getValor_grande()
                && this.valor_medio == t.getValor_medio()
                && this.valor_pequenas == t.valor_pequenas && this.impostos == t.getImpostos()
                && this.margemlucro == t.margemlucro && this.totalLucro == t.getTotalLucro()
                && this.premium == t.isPremium() && this.formula == t.getFormula());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transportadora:: {");
        sb.append(" Id: ").append(this.id);
        sb.append(" Nome: ").append(this.nome);
        sb.append(" Valor da encomenda pequena: ").append(this.valor_pequenas);
        sb.append(" Valor da encomenda média: ").append(this.valor_medio);
        sb.append(" Valor da encomenda grande: ").append(this.valor_grande);
        sb.append(" Imposto: ").append(this.impostos);
        sb.append(" Margem de lucro: ").append(this.margemlucro);
        sb.append(" É premium: ").append(this.premium);
        sb.append(" Formula atual: ").append(this.formula);
        sb.append(" Total lucro: ").append(this.totalLucro).append("}");
        return sb.toString();
    }

    @Override
    public Transportadora clone() {
        Transportadora copia = new Transportadora(this);
        return copia;
    }

    // Método que calcula o preço de transporte 1
    public double preco_transportadora1(int quant_artigos) {
        double valorbase = 0;
        if (quant_artigos == 1)
            valorbase = this.getValor_pequenas();
        else if (quant_artigos >= 2 && quant_artigos <= 5)
            valorbase = this.getValor_medio();
        else if (quant_artigos > 5)
            valorbase = this.getValor_grande();
        double preco = ((valorbase * this.getMargemlucro() * (1 + this.getImpostos())) * 0.9);
        this.setTotalLucro(preco);

        return preco;
    }

    // Método que calcula o preço de transporte 2;
    // Apenas altero o 0.9 para 1.1
    public double preco_transportadora2(int quant_artigos) {
        double valorbase = 0;
        if (quant_artigos == 1)
            valorbase = this.getValor_pequenas();
        else if (quant_artigos >= 2 && quant_artigos <= 5)
            valorbase = this.getValor_medio();
        else if (quant_artigos > 5)
            valorbase = this.getValor_grande();
        double preco = (valorbase * (1 + this.getMargemlucro() + this.getImpostos()) * 0.7)  ;
        this.setTotalLucro(preco);

        return preco;
    }

    // Método que pode ser usado para as transportadoras premium
    // Apenas altero o 0.9 para 1.5
    public double preco_transportadora3(int quant_artigos) {
        double valorbase = 0;
        if (quant_artigos == 1)
            valorbase = this.getValor_pequenas();
        else if (quant_artigos >= 2 && quant_artigos <= 5)
            valorbase = this.getValor_medio();
        else if (quant_artigos > 5)
            valorbase = this.getValor_grande();
        double preco = ((valorbase * this.getMargemlucro() * (1 + this.getImpostos())) * 1.5);
        this.setTotalLucro(preco);

        return preco;
    }

    public String formula1() {
        String resultado = "(ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 0,9";
        return resultado;
    }

    public String formula2() {
        String resultado = "ValorBase * (1 + MargemLucroTransportadora + Imposto) * 0,7";
        return resultado;
    }

    public String formula3() {
        String resultado = "(ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 1,5";
        return resultado;
    }

}
