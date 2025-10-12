package TP2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.io.Serializable;
import java.time.LocalDate;

public class Utilizador implements Serializable, Comparable<Utilizador> {

    // Variáveis de classe
    private static long codigo = 1;

    // Métodos de classe
    public static long getCodigo() {
        return codigo;
    }

    public static void setCodigo(long codigo) {
        Utilizador.codigo = codigo;
    }

    public static long criaCodigo() {
        return codigo++;
    }

    // Variáveis de instância
    private long id;
    private String email;
    private String nome;
    private String morada;
    private long nif;
    private String password;
    private int n_vendidos;
    private int n_comprados;

    private Set<String> produtosVendidos; // Contém o id dos artigos vendidos
    private Set<String> produtosVender; // Contém o id dos artigos que tem à venda
    private Set<String> pendentes; // Contém o id dos artigos que estão pendentes, ou seja, que ainda estão no
                                   // carrinho

    private Map<Long, Fatura> faturasVendas; // Mapeamento das faturas pelo id da encomenda
    private Map<Long, Fatura> faturasCompras;// Mapeamento das faturas pelo id da encomenda

    private Set<Long> comprei; // contém o id das encomendas efetuadas;
    private Set<Long> devolvidos; // Set que contém os ids das encomendas devolvidos
    private Encomenda carrinho; // encomenda atual

    // Construtor vazio
    public Utilizador() {
        this.id = Utilizador.criaCodigo();
        this.email = "n/d";
        this.nome = "n/d";
        this.morada = "n/d";
        this.nif = -1; // negativo pois não está associado a ninguém.
        this.password = "n/d";
        this.n_vendidos = 0;
        this.n_comprados = 0;

        this.produtosVendidos = new HashSet<>();
        this.produtosVender = new HashSet<>();
        this.pendentes = new HashSet<>();

        this.faturasVendas = new HashMap<>();
        this.faturasCompras = new HashMap<>();

        this.comprei = new HashSet<>();
        this.devolvidos = new HashSet<>();
        this.carrinho = new Encomenda();
    }

    // Construtor parametrizado
    public Utilizador(String email, String nome, String morada, Long nif2, String password) {
        this.id = Utilizador.criaCodigo();
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif2;
        this.password = password;
        this.n_vendidos = 0;
        this.n_comprados = 0;

        this.produtosVendidos = new HashSet<>();
        this.produtosVender = new HashSet<>();
        this.pendentes = new HashSet<>();

        this.faturasVendas = new HashMap<>();
        this.faturasCompras = new HashMap<>();

        this.comprei = new HashSet<>();
        this.devolvidos = new HashSet<>();
        this.carrinho = new Encomenda();

    }

    // Construtor cópia
    public Utilizador(Utilizador umUtilizador) {
        this.id = umUtilizador.getId();
        this.email = umUtilizador.getEmail();
        this.nome = umUtilizador.getNome();
        this.morada = umUtilizador.getMorada();
        this.nif = umUtilizador.getNif();
        this.password = umUtilizador.getPassword();
        this.n_vendidos = umUtilizador.getN_vendidos();
        this.n_comprados = umUtilizador.getN_comprados();

        this.produtosVendidos = umUtilizador.getProdutosVendidos();
        this.produtosVender = umUtilizador.getProdutosVender();
        this.pendentes = umUtilizador.getPendentes();

        this.faturasVendas = umUtilizador.getFaturasVendas();
        this.faturasCompras = umUtilizador.getFaturasCompras();

        this.comprei = umUtilizador.getComprei();
        this.devolvidos = umUtilizador.getDevolvidos();
        this.carrinho = umUtilizador.getCarrinho();

    }

    // Métodos getters e setters
    public int getN_comprados() {
        return n_comprados;
    }

    public int getN_vendidos() {
        return n_vendidos;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getMorada() {
        return morada;
    }

    public long getNif() {
        return nif;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getProdutosVendidos() {
        Set<String> copia = new HashSet<>(this.produtosVendidos);
        return copia;
    }

    public Set<String> getProdutosVender() {
        Set<String> copia = new HashSet<>(this.produtosVender);
        return copia;
    }

    public Set<String> getPendentes() {
        Set<String> copia = new HashSet<>(this.pendentes);
        return copia;
    }

    public Set<Long> getComprei() {
        Set<Long> copia = new HashSet<>(this.comprei);
        return copia;
    }

    public Set<Long> getDevolvidos() {
        return (new HashSet<>(this.devolvidos));
    }

    public Encomenda getCarrinho() {
        Encomenda copia = new Encomenda(this.carrinho);
        return copia;
    }

    public long getIdCarrinho() {
        return this.carrinho.getId();
    }

    public Map<Long, Fatura> getFaturasVendas() {
        Map<Long, Fatura> copia = new HashMap<>();
        for (Long l : this.faturasVendas.keySet()) {
            copia.put(l, this.faturasVendas.get(l));
        }
        return copia;
    }

    public Fatura get1FaturaVenda(long idEncomenda) {
        return this.faturasVendas.get(idEncomenda);
    }

    public Map<Long, Fatura> getFaturasCompras() {
        Map<Long, Fatura> copia = new HashMap<>();
        for (Long l : this.faturasCompras.keySet()) {
            copia.put(l, this.faturasCompras.get(l));
        }
        return copia;
    }

    public Fatura get1FaturaCompra(long idEncomenda) {
        return this.faturasCompras.get(idEncomenda);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNif(long nif) {
        this.nif = nif;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProdutosVendidos(Set<String> novo) {
        this.produtosVendidos = new HashSet<>(novo);
    }

    public void setProdutosVender(Set<String> novo) {
        this.produtosVender = new HashSet<>(novo);
    }

    public void setPendentes(Set<String> novo) {
        this.pendentes = new HashSet<>(novo);
    }

    public void setN_comprados(int n_comprados) {
        this.n_comprados = n_comprados;
    }

    public void setN_vendidos(int n_vendidos) {
        this.n_vendidos = n_vendidos;
    }

    public void setComprei(Set<Long> comprei) {
        this.comprei = new HashSet<>(comprei);
    }

    public void setDevolvidos(Set<Long> devolvidos) {
        this.devolvidos = new HashSet<>(devolvidos);
    }

    public void setCarrinho(Encomenda carrinho) {
        this.carrinho = carrinho.clone();
    }

    public void setFaturasVendas(Map<Long, Fatura> faturasVendas) {
        this.faturasVendas = new HashMap<>();
        for (Long l : faturasVendas.keySet()) {
            this.faturasVendas.put(l, faturasVendas.get(l).clone());
        }
    }

    public void setFaturasCompras(Map<Long, Fatura> faturasCompras) {
        this.faturasCompras = new HashMap<>();
        for (Long l : faturasCompras.keySet()) {
            this.faturasCompras.put(l, faturasCompras.get(l).clone());
        }
    }

    // Metodo que verifica se dois objetos são iguais
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Utilizador u = (Utilizador) o;

        return (this.id == u.getId() && this.getEmail().equals(u.getEmail())
                && this.nome.equals(u.getNome()) && this.morada.equals(u.getMorada())
                && this.nif == u.getNif() && this.password.equals(u.getPassword())
                && this.produtosVendidos.equals(u.getProdutosVendidos())
                && this.produtosVender.equals(u.getProdutosVender())
                && this.pendentes.equals(u.getPendentes())
                && this.faturasVendas.equals(u.getFaturasVendas())
                && this.faturasCompras.equals(u.getFaturasCompras())
                && this.comprei.equals(u.getComprei())
                && this.devolvidos.equals(u.getDevolvidos())
                && this.carrinho.equals(u.getCarrinho())
                && this.n_comprados == u.getN_comprados()
                && this.n_vendidos == u.getN_vendidos());
    }

    // Método que passa as informações de um utilizador para String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizador:: {");
        sb.append("Código: ").append(this.id);
        sb.append(" Email: ").append(this.email);
        sb.append(" Nome: ").append(this.nome);
        sb.append(" Morada: ").append(this.morada);
        sb.append(" Pass: ").append(this.password);
        sb.append(" Nif: ").append(this.nif);
        sb.append(" Artigos comprados: ").append(this.n_comprados);
        sb.append(" Artigos vendidos: ").append(this.n_vendidos);
        sb.append(" Id dos artigos vendidos: ").append(this.produtosVendidos.toString());
        sb.append(" Id dos artigos a vender: ").append(this.produtosVender.toString());
        sb.append(" Id das artigos pendentes: ").append(this.pendentes.toString());
        sb.append(" Id dos artigos comprados: ").append(this.comprei.toString());
        sb.append(" Id dos artigos devolvidos: ").append(this.devolvidos.toString());
        sb.append(" Faturas dos artigos comprados: ").append(this.faturasCompras.toString());
        sb.append(" Faturas dos artigos vendidos: ").append(this.faturasVendas.toString());
        sb.append(" Carrinho: ").append(this.carrinho.toString()).append(" }");

        return sb.toString();
    }

    // Método que cria uma cópia de um utilizador
    @Override
    public Utilizador clone() {
        Utilizador copia = new Utilizador(this);
        return copia;
    }

    // ---------------------Métodos relativos ao vendedor---------------------------

    // Método que insere um novo artigo para venda
    public String insereNovoArtigo(String idNovo) throws ArtigoExistenteException {
        if (this.produtosVender.contains(idNovo) == true)
            throw new ArtigoExistenteException("O artigo " + idNovo + " já se encontra à venda");

        else {
            this.produtosVender.add(idNovo);
            return idNovo; // retornar o id inserido
        }
    }

    // Método que elimina um artigo que está à venda
    public String eliminaArtigo(String idEliminar) throws ArtigoNaoEncontradoException {
        if (this.produtosVender.contains(idEliminar) == false)
            throw new ArtigoNaoEncontradoException("O artigo " + idEliminar + " não se encontra à venda");

        else {
            this.produtosVender.remove(idEliminar);
            return idEliminar;
        }
    }

    // Método que faz a passagem de um artigo à venda para pendente
    // Usar quando o utilizador puser o artigo no carrinho
    public String aVendaToPendente(String id) throws ArtigoNaoEncontradoException {
        if (this.produtosVender.contains(id) == false)
            throw new ArtigoNaoEncontradoException("O artigo " + id + " não se encontra à venda");

        else {
            this.pendentes.add(id);
            this.produtosVender.remove(id);
            return id;
        }
    }

    // Método que passa um artigo vendido para à venda
    // Caso o artigo seja devolvido
    public String vendidoToaVenda(String id) throws ArtigoNaoEncontradoException {
        if (this.produtosVendidos.contains(id) == false)
            throw new ArtigoNaoEncontradoException("O artigo " + id + " não foi vendido");

        else {
            this.produtosVender.add(id);
            this.produtosVendidos.remove(id);
            return id;
        }
    }

    // Método que faz a passagem de um artigo pendente para vendido.
    // Usar quando o Utilizador finalizar a encomenda.
    public String pendenteToVendido(String id) throws ArtigoNaoEncontradoException {
        if (this.pendentes.contains(id) == false)
            throw new ArtigoNaoEncontradoException("O artigo " + id + " não existe no carrinho");

        else {
            this.produtosVendidos.add(id);
            this.pendentes.remove(id);
            return id;
        }
    }

    // Método que faz a passagem de um artigo pendente para à venda.
    // Usar quando o Utilizador remover um artigo no carrinho.
    public String pendenteToAvenda(String id) throws ArtigoNaoEncontradoException {
        if (this.pendentes.contains(id) == false)
            throw new ArtigoNaoEncontradoException("O artigo " + id + " não existe no carrinho");

        else {
            this.produtosVender.add(id);
            this.pendentes.remove(id);
            return id;
        }
    }

    // Método que calcula o numero de artigos vendidos numa determinada data
    public int numeroArtigos(Set<String> idsArtigos) {
        return (idsArtigos.size());
    }

    // ---------------------Métodos relativos ao
    // comprador---------------------------

    // Método que atualiza o carrinho, ou seja uma encomenda
    public long atualizaEncomenda(LocalDate data) {
        // this.carrinho = new Encomenda();
        // Mudar o idComprador
        this.carrinho.setIdComprador(this.id);
        // Mudar a dataCriação
        this.carrinho.setData(data);
        return (this.carrinho.getId());
    }

    // Método que finaliza a encomenda por indicação do utilizador
    // Retornar a Encomenda.
    public Encomenda finalizaEncomenda(LocalDate data) throws EncomendaExistenteException, CarrinhoVazioException {
        if (this.comprei.contains(this.carrinho.getId()) == true)
            throw new EncomendaExistenteException("A encomenda " + this.carrinho.getId() + " já foi comprada");

        else if (this.carrinho.numeroArtigos() == 0) {
            throw new CarrinhoVazioException("O carrinho está vazio!");
        } else {
            this.comprei.add(this.carrinho.getId());
            this.carrinho.setEstado(Estado.Finalizada);
            this.carrinho.setEstado(Estado.Expedida);
            this.carrinho.setDataEntrega(data);
            this.carrinho.defineTamanho();
            return (this.getCarrinho());
        }
    }

    // Método que elimina todos os artigos que estão no carrinho.
    // Retornar um set com os ids dos artigos
    public Set<String> removeCarrinho() {
        return (this.carrinho.removeTudo());
    }

    // Método que cria um novo Carrinho
    // Usar quando se finalizar uma encomenda
    public void novoCarrinho() {
        this.carrinho = new Encomenda();
    }

    // Método que adiciona um artigo ao carrinho.
    public String adicionaArtigo(String id) throws ArtigoExistenteException {
        return (this.carrinho.adicionaArtigo(id));
    }

    // Método que remove um artigo de uma encomenda
    public String removeArtigo(String id) throws ArtigoNaoEncontradoException {
        return (this.carrinho.removeArtigo(id));
    }

    // Método que retorna um set que contém os ids dos artigos que estão no carrinho
    public Set<String> artigosCarrinho() {
        return (this.carrinho.getEncomenda());
    }

    // Método que verifica se aquela encomenda foi comprada
    public boolean verificaComprei(long idEncomenda) {
        return (this.comprei.contains(idEncomenda));
    }

    // Método que passa uma encomenda de comprei para devolvido
    // Usar quando o utilizador devolver a encomenda
    // Primeiro é preciso verificar se a encomenda pode ser devolvida
    public long compreiToDevolvido(long idEncomenda) throws EncomendaNaoEncontradaException {
        if (this.comprei.contains(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("A encomenda " + idEncomenda + " não foi comprada");

        else {
            this.devolvidos.add(idEncomenda);
            this.comprei.remove(idEncomenda);
            return idEncomenda;
        }
    }

    @Override
    public int compareTo(Utilizador u) {
        if (this.n_comprados == u.getN_comprados())
            return Long.compare(this.id, u.getId());

        else if (this.n_comprados > u.getN_comprados())
            return -1;

        else
            return 1;
    }

    public void adicionaFaturaVendedor(boolean isCompra, long idEncomenda, long nifComprador, String idArtigo,
            double precoArtigo, boolean isNovo, double taxaNovo, double taxaUsado) {
        double taxaVintage = 0;
        if (isNovo)
            taxaVintage -= taxaNovo;
        else
            taxaVintage -= taxaUsado;
        if (this.faturasVendas.containsKey(idEncomenda)) {
            this.faturasVendas.get(idEncomenda).atualizaFatura(idArtigo, precoArtigo, taxaVintage,
                    precoArtigo + taxaVintage);
        } else {
            Set<String> set = new HashSet<>();
            set.add(idArtigo);
            this.faturasVendas.put(idEncomenda, new Fatura(isCompra, idEncomenda, nifComprador, set, precoArtigo,
                    taxaVintage, precoArtigo + taxaVintage));
        }
    }

    public void adicionaFaturaComprador(boolean isCompra, long idEncomenda, long nifComprador, Set<String> artigos,
            double custoProdutos, double precoTransporte, double precoFinal) {
        this.faturasCompras.put(idEncomenda,
                new Fatura(isCompra, idEncomenda, nifComprador, artigos, custoProdutos, precoTransporte, precoFinal));
    }

    public String imprimeFaturasCompras() {
        StringBuilder sb = new StringBuilder();
        for (long l : this.faturasCompras.keySet()) {
            sb.append(this.faturasCompras.get(l));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String imprimeFaturasVendas() {
        StringBuilder sb = new StringBuilder();
        for (long l : this.faturasVendas.keySet()) {
            sb.append(this.faturasVendas.get(l));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String imprime1FaturaCompra(long idEncomenda) {
        return this.faturasCompras.get(idEncomenda).toString();
    }

    public String imprime1FaturaVenda(long idEncomenda) {
        return this.faturasVendas.get(idEncomenda).toString();
    }

}
