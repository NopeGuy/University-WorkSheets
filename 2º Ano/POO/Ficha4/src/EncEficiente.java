import java.time.LocalDate;
import java.util.ArrayList;

public class EncEficiente {
    private String nome;
    private int NIF;
    private String morada;
    private int numero;
    private LocalDate data;
    private ArrayList<LinhaEncomenda> encomendas;

    //Construtores
    public EncEficiente(String nomeCliente, int nif, String moradaCliente, int numeroEncomenda,
                        LocalDate dataEncomenda, ArrayList<LinhaEncomenda> lista) {
        this.nome = nomeCliente;
        this.NIF = nif;
        this.morada = moradaCliente;
        this.numero = numeroEncomenda;
        this.data = dataEncomenda;
        this.encomendas = new ArrayList<LinhaEncomenda>(lista);
    }
    public EncEficiente() {
        this("", 0,"", 0,LocalDate.now(),new ArrayList<LinhaEncomenda>());
    }
    public EncEficiente(EncEficiente enc) {
        this(enc.nome, enc.NIF, enc.morada, enc.numero, enc.data, enc.encomendas);
    }

    //Métodos de Instância
    public EncEficiente clone() {
        return new EncEficiente(this);
    }
    public boolean equals(Object p) {
        if (p == this) return true;

        if (p == null || this.getClass() != p.getClass()) return false;

        EncEficiente enc = (EncEficiente) p;
        return this.nome.equals(enc.nome) && (this.NIF==enc.NIF) &&
                this.morada.equals(enc.morada) && (this.numero==enc.numero) &&
                this.data.equals(enc.data) && this.encomendas.equals(enc.encomendas);
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Nome:"); sb.append(this.nome);
        sb.append("\nNIF: "); sb.append(this.NIF);
        sb.append("\nMorada: "); sb.append(this.morada);
        sb.append("\nNumero de encomenda: "); sb.append(this.numero);
        sb.append("\nData: "); sb.append(this.data.toString());
        sb.append("\nLista de encomendas: "); sb.append(this.encomendas.toString());

        return sb.toString();
    }
    public double calculaValorTotal() {
        return this.encomendas.stream().mapToDouble(LinhaEncomenda::calculaValorLinhaEnc).sum();
    }
    public double calculaValorDesconto() {
        return this.encomendas.stream().mapToDouble(LinhaEncomenda::calculaValorDesconto).sum();
    }
    public int numeroTotalProdutos() {
        return this.encomendas.stream().mapToInt(LinhaEncomenda::getQuantidade).sum();
    }
    public boolean existeProdutoEncomenda(String refProduto) {
        return this.encomendas.stream().anyMatch(enc->enc.getReferencia().equals(refProduto));
    }
    public void adicionaLinha(LinhaEncomenda linha) {
        this.encomendas.add(linha.clone());
    }
    public void removeProduto(String codProduto) {
        this.encomendas.removeIf(enc->enc.getReferencia().equals(codProduto));
    }

    //setters e getters
    public void setNome(String n) {
        this.nome = n;
    }
    public void setNIF(Integer nif) {
        this.NIF = nif;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public void setData(LocalDate date) {
        this.data = date;
    }
    public void setEncomendas(ArrayList<LinhaEncomenda> lista) {
        this.encomendas = new ArrayList<LinhaEncomenda>();
        for (LinhaEncomenda linha : lista)
            this.encomendas.add(linha.clone());
    }
    public void setMorada(String morada) {
        this.morada = new String(morada);
    }
    public String getNome() {
        return this.nome;
    }
    public String getMorada() {
        return this.morada;
    }
    public Integer getNumero() {
        return this.numero;
    }
    public Integer getNIF() {
        return this.NIF;
    }
    public LocalDate getData() {
        return this.data;
    }
    public ArrayList<LinhaEncomenda> getEncomendas() {
        ArrayList<LinhaEncomenda> ret = new ArrayList<LinhaEncomenda>();
        for (LinhaEncomenda linha : this.encomendas)
            ret.add(linha.clone());
        return ret;
    }

}
