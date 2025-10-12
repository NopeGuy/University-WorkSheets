package TP2;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Gestor_Utilizadores  implements Serializable {
    private Map<Long, Utilizador> utilizadores;
    private Map<String, Long> idUtilizadores;

    // Construtor vazio
    public Gestor_Utilizadores() {
        this.utilizadores = new HashMap<>();
        this.idUtilizadores = new HashMap<>();
    }

    // Construtor parametrizado
    public Gestor_Utilizadores(Map<Long, Utilizador> utilizadores, Map<String, String> passwords, Map<String, Long> idUtilizadores) {
        this.utilizadores = new HashMap<>();
        this.idUtilizadores = new HashMap<>();

        for (Long l : utilizadores.keySet()) {
            this.utilizadores.put(l, utilizadores.get(l).clone());
        }
        for (String s : passwords.keySet()) {
            this.idUtilizadores.put(s, idUtilizadores.get(s));
        }
    }

    // Construtor cópia
    public Gestor_Utilizadores(Gestor_Utilizadores g) {
        this.utilizadores = g.getUtilizadores();
        this.idUtilizadores = g.getIdUtilizadores();
    }

    // Getters
    public Map<Long, Utilizador> getUtilizadores() {
        Map<Long, Utilizador> copia = new HashMap<>();
        for (Long l : this.utilizadores.keySet()) {
            copia.put(l, this.utilizadores.get(l).clone());
        }
        return copia;
    }
    public Map<String, Long> getIdUtilizadores() {
        Map<String, Long> copia = new HashMap<>();
        for (String s : this.idUtilizadores.keySet()) {
            copia.put(s, this.idUtilizadores.get(s));
        }
        return copia;
    }

    //Getters de 1 elemento
    public Utilizador get1Utilizador(long id) {
        return this.utilizadores.get(id).clone();
    }
    public long get1IdUtilizador(String email) {
        return this.idUtilizadores.get(email);
    }

    // Setters
    public void setUtilizadores(Map<Long, Utilizador> utilizadores) {
        this.utilizadores = new HashMap<>();
        for (Long l : utilizadores.keySet()) {
            this.utilizadores.put(l, utilizadores.get(l).clone());
        }
    }
    public void setIdUtilizadores(Map<String, Long> idUtilizadores) {
        this.idUtilizadores = new HashMap<>();
        for (String s : idUtilizadores.keySet()) {
            this.idUtilizadores.put(s, idUtilizadores.get(s));
        }
    }

    //Verificar se existe
    public boolean existeUtilizador(long id) {
        return this.utilizadores.containsKey(id);
    }
    public boolean existeIdUtilizador(String email) {
        return this.idUtilizadores.containsKey(email);
    }

    @Override
    public Gestor_Utilizadores clone() {
        return new Gestor_Utilizadores(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Gestor_Utilizadores g = (Gestor_Utilizadores) o;
        return (this.utilizadores.equals(g.getUtilizadores()) && this.idUtilizadores.equals(g.getIdUtilizadores()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gestor de Utilizadores:: {");
        sb.append(" Utilizadores: ").append(this.utilizadores.toString());
        sb.append(" Emails associados ao id do utilizador correspondente: ").append(this.idUtilizadores.toString()).append("}");
        return sb.toString();
    }

    //Verifica a password de um email e devolve o id do utilizador
    public long verificaPassword(String email, String pass) {
        long id = -2;
        if (existeIdUtilizador(email)) {
            id = get1IdUtilizador(email);
            if (!this.utilizadores.get(id).getPassword().equals(pass))
                id = -1;
        }
        return id;
    }

    // Adiciona um novo utilizador
    public long adicionaUtilizador(String email, String nome, String morada, long nif2, String password) {
        Utilizador u = new Utilizador(email, nome, morada, nif2, password);
        this.utilizadores.put(u.getId(),u);
        this.idUtilizadores.put(email, u.getId());
        return u.getId();
    }

    // Método que devolve um utilizador dado o seu id
    public Utilizador devolveUtilizador(long idUtilizador){
        if(this.utilizadores.containsKey(idUtilizador) == false)
            return null;

        else{
            return (this.utilizadores.get(idUtilizador).clone());
        }
    }

    // Vendedor adiciona novo artigo para venda
    public String vendeArtigo(String idArtigo, long idUtilizador) throws ArtigoExistenteException {
        return this.utilizadores.get(idUtilizador).insereNovoArtigo(idArtigo);
    }

    // Eliminar um artigo que tem à venda
    public String retiraArtigo(String idArtigo, long idUtilizador) throws ArtigoNaoEncontradoException {
        return this.utilizadores.get(idUtilizador).eliminaArtigo(idArtigo);
    }

    //Ver artigos que tenho à venda
    public Set<String> meusArtigos (long idUtilizador){
        return this.utilizadores.get(idUtilizador).getProdutosVender();
    }

    //Adicionar um artigo a uma encomenda
    public void adicionaArtigoEncomenda(String idArtigo, long idVendedor, long idComprador, LocalDate date) throws ArtigoExistenteException, ArtigoNaoEncontradoException {
        if (this.utilizadores.get(idComprador).getCarrinho().numeroArtigos() == 0) {
            this.utilizadores.get(idComprador).atualizaEncomenda(date);
        }
        this.utilizadores.get(idComprador).adicionaArtigo(idArtigo);
        this.utilizadores.get(idVendedor).aVendaToPendente(idArtigo);
    }

    // Remover um artigo de uma encomenda
    public void removeArtigoEncomenda(String idArtigo, long idVendedor, long idComprador) throws ArtigoNaoEncontradoException {
        this.utilizadores.get(idComprador).removeArtigo(idArtigo);
        this.utilizadores.get(idVendedor).pendenteToAvenda(idArtigo);
    }

    //Remover todos os artigos de uma encomenda no comprador
    public Set<String> removeTodosComprador(long idComprador) {
        return this.utilizadores.get(idComprador).removeCarrinho();
    }

    // Método que cria um novo carrinho, logo a seguir a finalizar encomenda
    public void novoCarrinho(long idComprador) {
        this.utilizadores.get(idComprador).novoCarrinho();
    }

    //Passar um artigo de pendente para a venda em cada vendedor
    //Usar depois de remover todos os artigos do carrinho e devolver carrinho para cada id dos vendedores dos artigos
    public void voltaAVenderArtigo(String idArtigo, long idVendedor) throws ArtigoNaoEncontradoException {
        this.utilizadores.get(idVendedor).pendenteToAvenda(idArtigo);
    }

    //Devolve os ids dos artigos que estão no carrinho
    public Set<String> verCarrinho (long idUtilizador) {
        return this.utilizadores.get(idUtilizador).artigosCarrinho();
    }

    public long getIdCarrinho (long idUtilizador){
        return this.utilizadores.get(idUtilizador).getIdCarrinho();
    }

    // Devolve a string da encomenda que constitui o carrinho
    public String getCarrinho(long idUtilizador) {
        //Encomenda e = this.utilizadores.get(idUtilizador).getCarrinho();
        return this.utilizadores.get(idUtilizador).getCarrinho().toString();
    }

    // Devolve os ids das encomendas realizadas por um utilizador
    public Set<Long> getEncomendas(long idUtilizador) {
        return this.utilizadores.get(idUtilizador).getComprei();
    }

    //Devolve os ids das encomendas devolvidas por um utilizador
    public Set<Long> getEncomendasDevolvidas(long idUtilizador) {
        return this.utilizadores.get(idUtilizador).getDevolvidos();
    }

    //Finalizar encomenda
    public Encomenda finalizaEncomenda (long idUtilizador, LocalDate data) throws CarrinhoVazioException, EncomendaExistenteException {
        return this.utilizadores.get(idUtilizador).finalizaEncomenda(data);
    }

    //Passar um artigo de pendente para vendido em cada vendedor
    //Usar depois de finalizar encomenda para cada id dos vendedores dos artigos
    public void passaArtigoParaVendido (String idArtigo, long idVendedor) throws ArtigoNaoEncontradoException {
        this.utilizadores.get(idVendedor).pendenteToVendido(idArtigo);
    }

    public void passaVendidoParaVender(String idArtigo, long idVendedor) throws ArtigoNaoEncontradoException {
        this.utilizadores.get(idVendedor).vendidoToaVenda(idArtigo);
    }

    //Devolver encomenda
    public void devolveEncomenda (long idComprador, long idEncomenda) throws EncomendaNaoEncontradaException {
        this.utilizadores.get(idComprador).compreiToDevolvido(idEncomenda);
    }
    /* public Set<Long> devolveFaturasVendedor(long idUtilizador){
        return this.utilizadores.get(idUtilizador).getFaturasVendedor();
    }*/


     public void adicionaFaturaComprador(long idComprador, boolean isCompra, long idEncomenda, long nifComprador, Set<String> artigos, double custoProdutos, double precoTransporte, double precoFinal){
        this.utilizadores.get(idComprador).adicionaFaturaComprador(isCompra, idEncomenda, nifComprador, artigos, custoProdutos, precoTransporte, precoFinal);
     }
    public void adicionaFaturaVendedor(long idVendedor, boolean isCompra, long idEncomenda, long nifComprador, String idArtigo, double precoArtigo,boolean isNovo, double taxaNovo, double taxaUsado){
        this.utilizadores.get(idVendedor).adicionaFaturaVendedor(isCompra, idEncomenda, nifComprador, idArtigo, precoArtigo, isNovo, taxaNovo, taxaUsado);
    }


    // Método que retorna um set dos artigos vendidos
    public Set<String> artigosVendidos(long idVendedor){
        return(this.utilizadores.get(idVendedor).getProdutosVendidos());
    }

    // Método que altera a variável quantidade vendida entre datas
    public void alteraQuantiVendida(long idVendedor, int quanti){
        this.utilizadores.get(idVendedor).setN_vendidos(quanti);
    }

    // Método que altera a variável quantidade comprada entre datas
    public void alteraQuantiComprada(long idComprador, int quanti){
        this.utilizadores.get(idComprador).setN_comprados(quanti);
    }

    // Método que retorna o conjunto das chaves
    public Set<Long> chaves(){
        Set<Long> chaves = new HashSet<>(this.utilizadores.keySet());
        return chaves;
    }

    public String artigosVendidos2(long idVendedor){
        return this.utilizadores.get(idVendedor).getProdutosVendidos().toString();
    }

    // Método que devolve o NIF de um utilizador
     public long devolveNif (long idUtilizador) {
        return this.utilizadores.get(idUtilizador).getNif();
     }

     //Funções para imprimir faturas
     public String imprimeFaturasCompras (long idUtilizador) {
         return this.utilizadores.get(idUtilizador).imprimeFaturasCompras();
     }
    public String imprimeFaturasVendas (long idUtilizador) {
        return this.utilizadores.get(idUtilizador).imprimeFaturasVendas();
    }
    public String imprime1FaturaCompra (long idEncomenda, long idUtilizador) {
        return this.utilizadores.get(idUtilizador).imprime1FaturaCompra(idEncomenda);
    }
    public String imprime1FaturaVenda (long idEncomenda, long idUtilizador) {
        return this.utilizadores.get(idUtilizador).imprime1FaturaVenda(idEncomenda);
    }
}