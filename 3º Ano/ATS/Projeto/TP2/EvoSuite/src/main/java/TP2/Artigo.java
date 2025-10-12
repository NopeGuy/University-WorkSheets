package TP2;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;

public abstract class Artigo implements Serializable {

    // Variável de classe
    public static String codigo = "A";
    public static long contador = 1;

    public static String getCodigo() {
        return codigo;
    }

    public static long getContador(){
        return contador;
    }

    public static void setCodigo(String code) {
        codigo = code;
    }
    public static void setContador(long contador) {
        Artigo.contador = contador;
    }

    public static String criaCodigo() {
        String novoCodigo = codigo + contador;
        contador++;
        return novoCodigo;
    }

    private String descricao;
    private String marca;
    private String codigo_barras;
    private double preco_base;
    // private float preco_atual;
    private boolean novo;
    private int estado_uso;
    private int n_utilizadores;
    private long idTransportadora;
    private long idVendedor;
    private boolean disponivel;

    // Querys
    private LocalDate data_venda;

    // 0 - Quando é novo, 1 - Quando é usado
    public abstract double preco_atual(LocalDate data);

    public abstract Artigo clone();

    public Artigo() {
        this.descricao = "Artigo invalido";
        this.marca = "Artigo invalido";
        this.codigo_barras = Artigo.criaCodigo();
        this.preco_base = 0;
        this.novo = false;
        this.estado_uso = -1; // Não definido
        this.n_utilizadores = -1;
        this.idTransportadora = -1;
        this.idVendedor = -1;
        this.disponivel = false;
        this.data_venda = LocalDate.MIN;

    }

    // No menu perguntar ao vendedor qual a transportadora do artigo
    public Artigo(String descricao, String marca, double preco_base, boolean novo, int estado_uso,
            int n_utilizadores, Long idTransportadora, Long idVendedor) {
        // this.id_vendedor = id_vendedor;
        this.codigo_barras = Artigo.criaCodigo();
        this.descricao = descricao;
        this.marca = marca;
        this.preco_base = preco_base;
        this.novo = novo;
        this.estado_uso = estado_uso;
        this.n_utilizadores = n_utilizadores;
        this.idTransportadora = idTransportadora;
        this.idVendedor = idVendedor;
        this.disponivel = true;
        this.data_venda = LocalDate.MIN;
        // this.codigo_barras = this.codigo_barras + 1;

    }

    public Artigo(Artigo umArtigo) {
        // this.id_vendedor = umArtigo.getId_vendedor();
        this.codigo_barras = umArtigo.getCodigo_barras();
        this.descricao = umArtigo.getDescricao();
        this.marca = umArtigo.getMarca();
        this.preco_base = umArtigo.getPreco_base();
        this.novo = umArtigo.getNovo();
        this.estado_uso = umArtigo.getEstado_uso();
        this.n_utilizadores = umArtigo.getN_utilizadores();
        this.idTransportadora = umArtigo.getIdTransportadora();
        this.idVendedor = umArtigo.getIdVendedor();
        this.disponivel = umArtigo.getDisponivel();
        this.data_venda = umArtigo.getData_venda();

    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPreco_base(double preco_base) {
        this.preco_base = preco_base;
    }
    /*
     * public void setUso_estado(boolean novo, int estado_uso, int n_utilizadores) {
     * this.novo = novo;
     * this.estado_uso = estado_uso;
     * this.n_utilizadores = n_utilizadores;
     * 
     * }
     */

    public void setPreco_atual(float preco_atual) {
        // this.preco_atual = preco_atual;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public void setEstado_uso(int estado_uso) {
        this.estado_uso = estado_uso;
    }

    public void setN_utilizadores(int n_utilizadores) {
        this.n_utilizadores = n_utilizadores;
    }

    public void setTransportadora(long transportadora) {
        this.idTransportadora = transportadora;
    }

    public void setVendedor(long vendedor) {
        this.idVendedor = vendedor;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setData_venda(LocalDate data_venda) {
        this.data_venda = data_venda;
    }

    /*
     * public void setId_vendedor(int id_vendedor) {
     * this.id_vendedor = id_vendedor;
     * }
     */

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean getNovo() {
        return novo;
    }

    public int getEstado_uso() {
        return estado_uso;
    }

    public String getMarca() {
        return marca;
    }

    public int getN_utilizadores() {
        return n_utilizadores;
    }

    public double getPreco_base() {
        return preco_base;
    }

    public long getIdVendedor() {
        return idVendedor;
    }

    public long getIdTransportadora() {
        return idTransportadora;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public LocalDate getData_venda() {
        return data_venda;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Artigo a = (Artigo) obj;

        return (this.codigo_barras.equals(a.getCodigo_barras()) && this.descricao.equals(a.getDescricao())
                && this.descricao.equals(a.getMarca()) && this.preco_base == a.getPreco_base()
                && this.novo == a.getNovo() && this.estado_uso == a.getEstado_uso()
                && this.n_utilizadores == a.getN_utilizadores() && this.idVendedor == a.getIdVendedor()
                && this.idTransportadora == a.getIdTransportadora()
                && this.disponivel == a.getDisponivel()
                && this.data_venda.equals(a.getData_venda()));
        // return (this.codigo_barras == ((Artigo) obj).getCodigo_barras());
        // this.preco_base == a.getPreco_atual()

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Artigo:: {");
        sb.append(" Código de barras: ").append(this.codigo_barras);
        sb.append(" Descrição: ").append(this.descricao);
        sb.append(" Marca: ").append(this.marca);
        sb.append(" Preço base: ").append(this.preco_base);
        sb.append(" Novo: ").append(this.novo);
        sb.append(" Estado de uso: ").append(this.estado_uso);
        sb.append(" Número de utilizadores: ").append(this.n_utilizadores);
        sb.append(" Disponivel: ").append(this.disponivel);
        sb.append(" Id da transportadora: ").append(this.idTransportadora);
        sb.append(" Id do vendedor: ").append(this.idVendedor);
        if (this.data_venda.equals(LocalDate.MIN) == false) {
            sb.append(" Data de venda: ").append(this.data_venda.toString());
        }
        sb.append(" }");
        // Falta definir a data de venda
        return sb.toString();
    }

    public String mostrar(LocalDate data) {
        DecimalFormat df = new DecimalFormat("0.00");
        String precoFormatado = df.format(this.preco_atual(data));
        return (this.toString() + " Preço atual: " + precoFormatado + "€ " + '\n');


    }

    /*
     * @Override
     * public Artigo clone() {
     * Artigo artigo = new Artigo(this);
     * return (artigo);
     * 
     * }
     */
}