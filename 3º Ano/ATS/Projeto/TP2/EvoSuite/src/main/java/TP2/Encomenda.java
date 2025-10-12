package TP2;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.time.temporal.ChronoUnit;


public class Encomenda implements Serializable {

    // Variáveis de classe
    private static long codigo = 1;
    private static int diasDevolver = 2;

    // Métodos de classe
    public static long getCodigo() {
        return codigo;
    }
    public static void setCodigo(long codigo) {
        Encomenda.codigo = codigo;
    }

    public static long criaCodigo() {
        return codigo++;
    }

    public static int getDiasDevolver(){
        return diasDevolver;
    }

    public static void setDiasDevolver(int dias){
        diasDevolver = dias;
    }

    // Variavéis de instância

    private Set<String> encomenda; // contém os ids dos artigos
    private long id;
    private long idComprador;
    private Dimensao dimensao; // grande, médio, pequeno
    private Estado estado; // pendente, finalizada, expedida
    private LocalDate dataCriacao;
    private LocalDate dataEntrega; // é preciso???

    // Construtor vazio
    public Encomenda() {
        this.encomenda = new HashSet<>();
        this.id = criaCodigo();
        this.idComprador = -1;
        this.dimensao = Dimensao.Pequeno;
        this.estado = Estado.Pendente;
        this.dataCriacao = LocalDate.now();
        this.dataEntrega = null;
    }

    // Construtor parametrizado
    public Encomenda(Set<String> artigos, long idComprador, Dimensao dimensao, LocalDate data) {
        this.encomenda = new HashSet<>(artigos);
        this.id = criaCodigo();
        this.idComprador = idComprador;
        this.dimensao = dimensao;
        this.estado = Estado.Pendente;
        this.dataCriacao = data;
        this.dataEntrega = null; // uma vez que quando criasse a encomenda esta ainda não chegou
    }

    // Construtor cópia
    public Encomenda(Encomenda umaEncomenda) {
        this.encomenda = umaEncomenda.getEncomenda();
        this.id = umaEncomenda.getId();
        this.idComprador = umaEncomenda.getIdComprador();
        this.dimensao = umaEncomenda.getDimensao();
        this.estado = umaEncomenda.getEstado();
        this.dataCriacao = umaEncomenda.getData();
        this.dataEntrega = umaEncomenda.getDataEntrega();
    }

    // Métodos getters e setters
    public Set<String> getEncomenda() {
        return (new HashSet<>(this.encomenda));
    }

    public long getId() {
        return id;
    }

    public long getIdComprador() {
        return idComprador;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public Estado getEstado() {
        return estado;
    }

    public LocalDate getData() {
        return dataCriacao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }


    public void setEncomenda(Set<String> encomenda) {
        this.encomenda = new HashSet<>(encomenda);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdComprador(long idComprador) {
        this.idComprador = idComprador;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setData(LocalDate data) {
        this.dataCriacao = data;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    // Método que verifica se duas Encomendas são iguais
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Encomenda e = (Encomenda) o;

        return (this.encomenda.equals(e.getEncomenda()) && this.id == e.getId() && this.dimensao.equals(e.getDimensao())
                && this.estado.equals(e.getEstado()) && this.dataCriacao.equals(e.getData())
                && this.dataEntrega.equals(e.getDataEntrega()));
    }

    // Método que transforma o objeto Encomenda numa String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encomenda:: {");
        sb.append(" Artigos: ").append(this.encomenda.toString());
        sb.append(" Código: ").append(this.id);
        this.defineTamanho();
        sb.append(" Dimensão: ").append(this.dimensao.toString());
        sb.append(" Estado: ").append(this.estado.toString());
        if (this.encomenda.size() == 0) {
            sb.append(" Data de criação: N/A ");
        } else {
            sb.append(" Data de criação: ").append(this.dataCriacao.toString());
        }
        if (this.dataEntrega == null) {
            sb.append(" Data de entrega: N/A").append("}");
        } else {
            sb.append(" Data de entrega: ").append(this.dataEntrega.toString()).append("}");
        }
        return sb.toString();
    }

    // Método que cria uma cópia de uma Encomenda
    @Override
    public Encomenda clone() {
        Encomenda copia = new Encomenda(this);

        return copia;
    }

    // Método que adiciona um Artigo a uma Encomenda
    public String adicionaArtigo(String idArtigo) throws ArtigoExistenteException {
        if (this.encomenda.contains(idArtigo) == true)
            throw new ArtigoExistenteException("O artigo " +idArtigo+ " já existe na encomenda");

        else {
            this.encomenda.add(idArtigo);
            return idArtigo;
        }
    }

    // Método que remove um Artigo de uma Encomenda
    public String removeArtigo(String idArtigo) throws ArtigoNaoEncontradoException {
        if (this.encomenda.contains(idArtigo) == false)
            throw new ArtigoNaoEncontradoException("O artigo "+idArtigo+" não existe na encomenda");

        else {
            this.encomenda.remove(idArtigo);
            return idArtigo;
        }
    }

    // Método que remove os Artigos todos de uma Encomenda
    public Set<String> removeTudo(){
        Set<String> remove = new HashSet<>();
        for(String s : this.encomenda)
            remove.add(s);

        this.encomenda.clear();

        return remove;
    }

    // Método que verifica se uma Encomenda pode ser devolvida
    // recebe o numero de dias que até pode devolver
    // Tirei a LocalDate agora do argumento
    public boolean podeDevolver(LocalDate data) {
        if (ChronoUnit.DAYS.between(this.dataEntrega, data) <= Encomenda.diasDevolver && this.estado.equals(Estado.Expedida))
            return true;

        else
            return false;
    }

    // Método que define qual o tamanho da encomenda consoante o numero de artigos que tem
    public void defineTamanho(){
        int numero = this.encomenda.size();
        if(numero <= 1) 
            this.dimensao = Dimensao.Pequeno;

        else if(numero >= 2 && numero <= 5) 
            this.dimensao = Dimensao.Medio;

        else
            this.dimensao = Dimensao.Grande;
    }

    // Método que conta o numeros de artigos numa encomenda
    public int numeroArtigos () {
        return this.encomenda.size();
    }
}
    
