package TP2;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class Fatura implements Serializable {
    private static long codigo = 1;

    public static long getCodigo() {
        return codigo;
    }

    public static long criaCodigo() {
        return codigo++;
    }

    public static void setCodigo(long codigo) {
        Fatura.codigo = codigo;
    }

    public long id;
    public boolean compra; // identifica se é uma compra ou uma venda
    public long idEncomenda;
    public long nifComprador;
    private Set<String> artigos; // Guarda os ids dos artigos
    public double custoProdutos;
    public double alteracaoPreco; // Valor que vai para a Vintage (negativo) no caso de uma venda e transporte no
                                  // caso de uma compra
    public double precoFinal;

    // Construtor vazio
    public Fatura() {
        this.id = criaCodigo();
        this.compra = false;
        this.idEncomenda = 0;
        this.nifComprador = 0;
        this.artigos = new HashSet<>();
        this.custoProdutos = 0;
        this.alteracaoPreco = 0;
        this.precoFinal = 0;
    }

    // Construtor parametrizado
    public Fatura(boolean compra, long idEncomenda, long nifComprador, Set<String> artigos, double custoProdutos,
            double alteracaoPreco, double precoFinal) {
        this.id = criaCodigo();
        this.compra = compra;
        this.idEncomenda = idEncomenda;
        this.nifComprador = nifComprador;
        this.artigos = artigos;
        this.custoProdutos = custoProdutos;
        this.alteracaoPreco = alteracaoPreco;
        this.precoFinal = precoFinal;
    }

    // Construtor de cópia
    public Fatura(Fatura umaFatura) {
        this.id = criaCodigo();
        this.compra = umaFatura.isCompra();
        this.idEncomenda = umaFatura.getIdEncomenda();
        this.nifComprador = umaFatura.getNifComprador();
        this.artigos = umaFatura.getArtigos();
        this.custoProdutos = umaFatura.getCustoProdutos();
        this.alteracaoPreco = umaFatura.getAlteracaoPreco();
        this.precoFinal = umaFatura.getPrecoFinal();
    }

    // Getters
    public long getId() {
        return id;
    }

    public boolean isCompra() {
        return compra;
    }

    public long getIdEncomenda() {
        return idEncomenda;
    }

    public long getNifComprador() {
        return nifComprador;
    }

    public Set<String> getArtigos() {
        return (new HashSet<>(this.artigos));
    }

    public double getCustoProdutos() {
        return custoProdutos;
    }

    public double getAlteracaoPreco() {
        return alteracaoPreco;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    public void setIdEncomenda(long idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public void setNifComprador(long nifComprador) {
        this.nifComprador = nifComprador;
    }

    public void setArtigos(Set<String> artigos) {
        this.artigos = new HashSet<>(artigos);
    }

    public void setCustoProdutos(double custoProdutos) {
        this.custoProdutos = custoProdutos;
    }

    public void setAlteracaoPreco(double alteracaoPreco) {
        this.alteracaoPreco = alteracaoPreco;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Fatura f = (Fatura) o;

        return (this.id == f.getId() && this.compra == f.isCompra() && this.idEncomenda == f.getIdEncomenda()
                && this.nifComprador == f.getNifComprador()
                && this.artigos.equals(f.getArtigos()) && this.custoProdutos == f.getCustoProdutos()
                && this.alteracaoPreco == f.getAlteracaoPreco()
                && this.precoFinal == f.getPrecoFinal());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.00");
        sb.append("Fatura ").append(this.id).append(" - ");
        if (this.compra)
            sb.append("Compra\n");
        else
            sb.append("Venda\n");
        sb.append("NIF do comprador: ").append(this.nifComprador).append("\n");
        sb.append("Artigos vendidos: ").append(this.artigos.toString()).append("\n");
        sb.append("\n");
        sb.append("Custo dos produtos: ").append(df.format(this.custoProdutos)).append("€\n");
        if (this.compra)
            sb.append("Custo do transporte: ").append(df.format(this.alteracaoPreco)).append("€\n");
        else
            sb.append("Taxa Vintage: ").append(df.format(this.alteracaoPreco)).append("€\n");
        sb.append("Preço final: ").append(df.format(this.precoFinal)).append("€\n");
        return sb.toString();
    }

    @Override
    public Fatura clone() {
        return new Fatura(this);
    }

    public void atualizaFatura(String idArtigo, double custoProdutos, double alteracaoPreco, double precoFinal) {
        this.artigos.add(idArtigo);
        this.custoProdutos += custoProdutos;
        this.alteracaoPreco += alteracaoPreco;
        this.precoFinal += precoFinal;
    }
}
