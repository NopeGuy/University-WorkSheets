package TP2;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/*
 * Não sei se sou usar isto para mim n faz grande sentido da forma que organizamos
 */

public class Vintage implements Serializable {
    private Gestor_Artigos gestor_Artigos;
    private Gestor_Utilizadores gestor_Utilizadores;
    private Gestor_Transportadoras gestor_Trasportadoras;
    private GestorEncomendas gestor_Encomendas;
    private long utilizador;
    private LocalDate data;

    public Vintage() {
        this.gestor_Utilizadores = new Gestor_Utilizadores();
        this.gestor_Artigos = new Gestor_Artigos();
        this.gestor_Trasportadoras = new Gestor_Transportadoras();
        this.gestor_Encomendas = new GestorEncomendas();
        this.utilizador = -1;
        this.data = LocalDate.now();
    }

    public static Vintage load() {
        Vintage vintage = Vintage.carregarEstado();
        if (vintage == null) {
            vintage = new Vintage();
        }
        return vintage;
    }

    private void add_admin() throws ArtigoExistenteException{
            this.adicionaUtilizador("admin", "admin", "1", 1, "admin");

            String id = this.gestor_Artigos.criar_artigo_mala("Mala de viagem grande", "Samsonite", 299.99, false, 0,
                    0, 1, 1, 70, "Poliéster", 1950, true, 4.5f);
            String id1 = this.gestor_Artigos.criar_artigo_mala("Mala de ombro grande", "Le Postiche", 149.90, false, 9,
                    25, 2, 1, 80, "Couro sintético", 2022, false, 4.7f);
            String id2 = this.gestor_Artigos.criar_artigo_mala("Mochila escolar", "JanSport", 139.99, true, 7, 20, 2,
                    1L, 50, "Poliéster", 2023, false, 3.5f);
            String id3 = this.gestor_Artigos.criar_artigo_mala("Mala de viagem média", "Samsonite", 249.99, false, 8,
                    18, 2, 1, 60, "Poliéster", 2021, false, 4.2f);
            String id4 = this.gestor_Artigos.criar_artigo_mala("Mala de mão com rodinhas", "Delsey", 199.99, false, 9,
                    22, 2, 1, 50, "Couro", 2022, false, 4.8f);
            String id5 = this.gestor_Artigos.criar_artigo_mala("Mala de ombro pequena", "Guess", 119.90, true, 6, 12, 2,
                    1, 30, "Couro sintético", 2023, false, 3.2f);
                this.gestor_Utilizadores.vendeArtigo(id, this.gestor_Utilizadores.get1IdUtilizador("admin"));
                this.gestor_Utilizadores.vendeArtigo(id1,this.gestor_Utilizadores.get1IdUtilizador("admin") );
                this.gestor_Utilizadores.vendeArtigo(id2, this.gestor_Utilizadores.get1IdUtilizador("admin"));
                this.gestor_Utilizadores.vendeArtigo(id3, this.gestor_Utilizadores.get1IdUtilizador("admin"));
                this.gestor_Utilizadores.vendeArtigo(id4, this.gestor_Utilizadores.get1IdUtilizador("admin"));
                this.gestor_Utilizadores.vendeArtigo(id5, this.gestor_Utilizadores.get1IdUtilizador("admin"));
           
    }
    private void add_trasportadoras(){
        this.adicinonarTransportadora("Fedex", 10, 20, 30, 0.23, 0.50, true, 3);
        this.adicinonarTransportadora("Feliz", 1, 2, 3, 0.23, 0.15, false, 1);
    }
    private void add_poo() throws ArtigoExistenteException {
        this.adicionaUtilizador("joaogamer", "joao", "Braga", 123456, "joao");
        String id = this.gestor_Artigos.criar_artigo_sapatilhas("Confortáveis", "Nike AIR", 250, true, 2, 10, 1L, 2, 38, true, "Branca", 2022, true, 0.0);
        String id1 = this.gestor_Artigos.criar_artigo_sapatilhas("Leves", "Puma", 80, true, 0, 0, 2L, 2L, 37, false, "Azul", 2023, false, 0.0);
        String id2 = this.gestor_Artigos.criar_artigo_sapatilhas("Resistentes", "Reebok", 90, false, 1, 5, 2L, 2L, 39, true, "Cinza", 2022, false, 20.0);
        String id3 = this.gestor_Artigos.criar_artigo_sapatilhas("Estilosas", "Vans", 70, true, 0, 0, 2L, 2, 40, true, "Vermelha", 2023, false, 0.0);
        String id4 = this.gestor_Artigos.criar_artigo_sapatilhas("Duráveis", "New Balance", 110, false, 2, 8, 2L, 2L, 38, false, "Rosa", 2022, false, 0.0);
        String id5 = this.gestor_Artigos.criar_artigo_sapatilhas("Moda casual", "Converse", 60, true, 0, 0, 2L, 2L, 37, false, "Cinza/Preto", 2023, false, 10.0);
            this.gestor_Utilizadores.vendeArtigo(id, this.gestor_Utilizadores.get1IdUtilizador("joaogamer"));
            this.gestor_Utilizadores.vendeArtigo(id1,this.gestor_Utilizadores.get1IdUtilizador("joaogamer") );
            this.gestor_Utilizadores.vendeArtigo(id2, this.gestor_Utilizadores.get1IdUtilizador("joaogamer"));
            this.gestor_Utilizadores.vendeArtigo(id3, this.gestor_Utilizadores.get1IdUtilizador("joaogamer"));
            this.gestor_Utilizadores.vendeArtigo(id4, this.gestor_Utilizadores.get1IdUtilizador("joaogamer"));
            this.gestor_Utilizadores.vendeArtigo(id5, this.gestor_Utilizadores.get1IdUtilizador("joaogamer"));
    

    }

    // Ver onde faz o try
    public void povoamento() throws ArtigoExistenteException {
        if (this.gestor_Utilizadores.existeIdUtilizador("admin") == false) {
            this.add_trasportadoras();
             this.add_admin();
        }
        if (this.gestor_Utilizadores.existeIdUtilizador("joaogamer") == false) this.add_poo();
        
        
    }

    public Vintage(Gestor_Artigos gestor_Artigos, Gestor_Utilizadores gestor_Utilizadores,
            GestorEncomendas gestorEncomendas, Gestor_Transportadoras gestor_Transportadoras, Menu menu,
            int utilizador) {
        this.gestor_Artigos = gestor_Artigos;
        this.gestor_Utilizadores = gestor_Utilizadores;
        this.gestor_Trasportadoras = gestor_Transportadoras;
        this.gestor_Encomendas = gestorEncomendas;
        this.utilizador = utilizador;

    }

    public Vintage(Gestor_Artigos gestor_Artigos, Gestor_Utilizadores gestor_Utilizadores,
            GestorEncomendas gestorEncomendas, Gestor_Transportadoras gestor_Transportadoras, Menu menu) {
        this.gestor_Artigos = gestor_Artigos;
        this.gestor_Utilizadores = gestor_Utilizadores;
        this.gestor_Trasportadoras = gestor_Transportadoras;
        this.gestor_Encomendas = gestorEncomendas;

    }

    public Vintage(Vintage a) {
        this.gestor_Artigos = a.getGestor_Artigos();
        this.gestor_Utilizadores = a.getGestor_Utilizadores();
        this.utilizador = a.getUtilizador();
    }

    public Gestor_Artigos getGestor_Artigos() {
        return this.gestor_Artigos.clone();
    }

    public Gestor_Utilizadores getGestor_Utilizadores() {
        return this.gestor_Utilizadores.clone();
    }

    public long getUtilizador() {
        return this.utilizador;
    }

    public LocalDate getData() {
        return data;
    }

    public GestorEncomendas getGestor_Encomendas() {
        return gestor_Encomendas;
    }

    public Gestor_Transportadoras getGestor_Trasportadoras() {
        return gestor_Trasportadoras;
    }

    public void setGestor_Artigos(Gestor_Artigos gestor_Artigos) {
        this.gestor_Artigos = gestor_Artigos;
    }

    public void setGestor_Utilizadores(Gestor_Utilizadores gestor_Utilizadores) {
        this.gestor_Utilizadores = gestor_Utilizadores;
    }

    public void setUtilizador(long utilizador) {
        this.utilizador = utilizador;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setGestor_Encomendas(GestorEncomendas gestor_Encomendas) {
        this.gestor_Encomendas = gestor_Encomendas;
    }

    public void setGestor_Trasportadoras(Gestor_Transportadoras gestor_Trasportadoras) {
        this.gestor_Trasportadoras = gestor_Trasportadoras;
    }

    public boolean existeEmail(String email) {

        return this.gestor_Utilizadores.existeIdUtilizador(email);
    }

    public boolean existeIdUtilizador(long id) {
        return this.gestor_Utilizadores.existeUtilizador(id);
    }

    public boolean verificaPassword(String email, String password) {
        long id = this.gestor_Utilizadores.verificaPassword(email, password);
        if (id < 0)
            return false;
        else {
            this.setUtilizador(id);
            return true;
        }
    }

    // Todos os artigos a venda
    public String todosArtigos() {
        return this.gestor_Artigos.mostrar_gestor(data);
    }

    public String artigoID(String id) {
        return this.gestor_Artigos.getArtigos().get(id).mostrar(data);
    }

    public void adicionaUtilizador(String email, String nome, String morada, long nif, String password) {
        this.utilizador = this.gestor_Utilizadores.adicionaUtilizador(email, nome, morada, nif, password);
    }

    public boolean existeArtigoId(String id) {
        return this.gestor_Artigos.pesquisar_artigo_id(id); // id do artigo deveria ser string mas está como int
    }

    public void login_utlizador(String email) {

        //for (Long e : this.gestor_Utilizadores.getUtilizadores().keySet()) {
        //}
        // for()
        this.utilizador = this.gestor_Utilizadores.get1IdUtilizador(email);
    }
    // Filtros Sapatilhas

    public String filtraAMarcaSapatilhas(String marca) {
        return this.gestor_Artigos.pesquisar_artigos_marca_sapatilha(marca);
    }

    public String filtraSapatilhasNovo(Boolean novo) {
        return this.gestor_Artigos.pesquisar_artigos_novo_sapatilhas(novo);
    }

    public String filtraSapatilhasTamanho(int tamanho) {
        return this.gestor_Artigos.pesquipesquisar_artigos_tamanho_sapatilhas(tamanho);
    }

    public String filtraSapatilhasCor(String cor) {
        return this.gestor_Artigos.pesquisar_artigos_cor(cor);
    }

    // Filtros Tshirts

    public String filtraTshirtsMarca(String marca) {
        return this.gestor_Artigos.pesquisar_artigos_marca_tshirt(marca);
    }

    public String filtraTshirtsNovo(Boolean novo) {
        return this.gestor_Artigos.pesquisar_artigos_novo_tshirt(novo);
    }

    public String filtraTshirtsTamanho(Tamanho tamanho) {
        return this.gestor_Artigos.pesquipesquisar_artigos_tamanho__tshit(tamanho);
    }

    public String filtraTshirtsPadrao(Padrao padrao) {
        return this.gestor_Artigos.pesquisar_artigos_padrao(padrao);
    }

    // Filtos Malas
    public String filtraMalasMarca(String marca) {
        return this.gestor_Artigos.pesquisar_artigos_marca_mala(marca);
    }

    public String filtraMalasNovo(Boolean novo) {
        return this.gestor_Artigos.pesquisar_artigos_novo_mala(novo);
    }

    public String filtraMalasDimensao(int dimensao) {
        return this.gestor_Artigos.pesquisar_artigos_dimensão_mala(dimensao);
    }

    public String filtraMalasMaterial(String material) {
        return this.gestor_Artigos.pesquisar_artigos_material(material);
    }

    public String filtraMalasColecao(int ano_colecao) {
        return this.gestor_Artigos.pesquisar_artigos_ano_colecao(ano_colecao);
    }

    // Continuar os filtros

    public String imprimeTransportadoras() {
        return this.gestor_Trasportadoras.toString();
    }

    public String imprimeTransportadorasNormais() {
        return this.gestor_Trasportadoras.transportadorasNormais();
    }

    public boolean existeTranspotadora(long id_transporadora) {
        return this.gestor_Trasportadoras.existe_transpotadora(id_transporadora);
    }

    public boolean existe_transpotadoraNormal(long id_transporadora) {
        return this.gestor_Trasportadoras.existe_transpotadoraNormal(id_transporadora);
    }

    public boolean existe_transpotadoraPremium(long id_transporadora) {
        return this.gestor_Trasportadoras.existe_transpotadoraPremium(id_transporadora);
    }

    public void criaSapatilha(String descricao, String marca, double preco, boolean uso, int estado,
            int n_utilizadores, Long transportadora, int tamanho, boolean atacadores, String cor, int data,
            boolean premium, double desconto) throws ArtigoExistenteException {
        String id = this.gestor_Artigos.criar_artigo_sapatilhas(descricao, marca, preco, uso, estado,
                n_utilizadores, transportadora, this.utilizador, tamanho, atacadores, cor, data, premium, desconto);
        this.gestor_Utilizadores.vendeArtigo(id, this.utilizador);
    }

    public void criaTshirt(String descricao, String marca, double preco, boolean uso, int estado,
            int n_utilizadores, long transportadora, Tamanho tamanho, Padrao padrao) throws ArtigoExistenteException {
        String id = this.gestor_Artigos.criar_artigo_tshirt(descricao, marca, preco, uso, estado, n_utilizadores,
                transportadora, this.utilizador, tamanho, padrao);
        this.gestor_Utilizadores.vendeArtigo(id, this.utilizador);
    }

    public void criaMala(String descricao, String marca, double preco, boolean uso, int estado, int n_utilizadores,
            long transportadora, int dimensao, String material, int ano, Boolean premium, float valorizacao)
            throws ArtigoExistenteException {
        String id = this.gestor_Artigos.criar_artigo_mala(descricao, marca, preco, uso, estado, n_utilizadores,
                transportadora, this.utilizador, dimensao, material, ano, premium, valorizacao);
        this.gestor_Utilizadores.vendeArtigo(id, this.utilizador);
    }

    public long artigoDisponivel(String id_artigo) throws ArtigoNaoEncontradoException {
        return (this.gestor_Artigos.colocar_disponivel_artigo(id_artigo));
    }

    public long artigoIndisponivel(String id_artigo) throws ArtigoNaoEncontradoException {
        return this.gestor_Artigos.tirar_disponivel_artigo(id_artigo);
    }

    public String imprimeCarrinho() {
        return this.gestor_Utilizadores.getCarrinho(this.utilizador);
    }

    public void adicionarArtigoCarrinho(String id) throws ArtigoNaoEncontradoException, ArtigoExistenteException {
        this.artigoIndisponivel(id);
        long vendedor = this.gestor_Artigos.id_artigo_para_id_vendedor(id);
        this.gestor_Utilizadores.adicionaArtigoEncomenda(id, vendedor, this.utilizador, data);
    }

    public void adicionarArtigoCarrinhoPorID(String id_artigo, long id_utilizador, LocalDate data)
            throws ArtigoNaoEncontradoException, ArtigoExistenteException {
        this.artigoIndisponivel(id_artigo);
        long vendedor = this.gestor_Artigos.id_artigo_para_id_vendedor(id_artigo);
        this.gestor_Utilizadores.adicionaArtigoEncomenda(id_artigo, vendedor, id_utilizador, data);
    }

    public void removerTodosArtigosCarrinho() throws ArtigoNaoEncontradoException {
        Set<String> artigos = this.gestor_Utilizadores.removeTodosComprador(this.utilizador);
        for (String s : artigos) {
            long vendedor = this.artigoDisponivel(s);
            this.gestor_Utilizadores.voltaAVenderArtigo(s, vendedor);
        }
    }

    public void removerArtigosCarrinho(String id) throws ArtigoNaoEncontradoException {
        long vendedor = this.artigoDisponivel(id);
        this.gestor_Utilizadores.removeArtigoEncomenda(id, vendedor, utilizador);

    }

    public String encomendasRealizadas() {
        return this.gestor_Encomendas.encomendasToString(this.gestor_Utilizadores.getEncomendas(this.utilizador));
    }

    public String encomendasDevolvidas() {
        return (this.gestor_Encomendas
                .encomendasToString(this.gestor_Utilizadores.getEncomendasDevolvidas(this.utilizador)));
    }

    public boolean encomendasDevolver(long l) throws EncomendaNaoEncontradaException,
            EncomendaDevolucaoImpossivelException, ArtigoNaoEncontradoException {
        Set<String> artigos = this.gestor_Encomendas.podeDevolvida(l, data);
        if (artigos == null) {
            return false;
        } else {
            long vendedor;
            for (String s : artigos) {
                vendedor = this.artigoDisponivel(s);
                this.gestor_Artigos.alteraDataToInicio(s);
                this.gestor_Utilizadores.passaVendidoParaVender(s, vendedor);
            }
            this.gestor_Utilizadores.devolveEncomenda(this.utilizador, l);
            return true;
        }
    }

    public int tamanhoEncomenda(long idEncomenda) {
        return (this.gestor_Encomendas.tamanhoEncomenda(idEncomenda));
    }

    public String meusArtigos() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.gestor_Utilizadores.meusArtigos(this.utilizador)) {
            sb.append(this.gestor_Artigos.getArtigos().get(s).mostrar(data));
        }
        return sb.toString();
    }

    public void adicinonarTransportadora(String nome, double valor_pequenas, double valor_medio, double valor_grande,
            double impostos, double margemlucro, boolean premium, int formula) {
        this.gestor_Trasportadoras.criaTransportadora(nome, valor_pequenas, valor_medio, valor_grande, impostos,
                margemlucro, premium, formula);
    }

    public double finalizarEncomenda() throws CarrinhoVazioException, ArtigoNaoEncontradoException,
            EncomendaExistenteException, EncomendaNaoEncontradaException {
        long nif = this.gestor_Utilizadores.devolveNif(this.utilizador);
        // Preço
        List<Long> transportadoras = new ArrayList<Long>();
        double preco_artigos = 0;
        for (String s : this.gestor_Utilizadores.verCarrinho(this.utilizador)) {
            // Alterar data
            // ---------------------------------------------------------------------------------------------
            Artigo artigo = this.gestor_Artigos.getArtigos().get(s);
            preco_artigos += artigo.preco_atual(this.data);
            long idTransportadora = artigo.getIdTransportadora();
            transportadoras.add(idTransportadora);
            this.gestor_Utilizadores.passaArtigoParaVendido(s, artigo.getIdVendedor());
            this.gestor_Utilizadores.adicionaFaturaVendedor(artigo.getIdVendedor(), false,
                    this.gestor_Utilizadores.getIdCarrinho(this.utilizador), nif, artigo.getCodigo_barras(),
                    artigo.preco_atual(this.data), artigo.getNovo(), Gestor_Artigos.getTaxaNovo(),
                    Gestor_Artigos.getTaxaUsado());
        }
        double preco_transporte = this.gestor_Trasportadoras.precoTransporte(transportadoras);
        double preco = preco_artigos + preco_transporte;

        // Carrinho
        Encomenda encomenda = this.gestor_Utilizadores.finalizaEncomenda(this.utilizador, this.data);
        // Alterar data, acho que funciona
        this.gestor_Artigos.alteraData(encomenda);
        this.gestor_Encomendas.adicionaEncomenda(encomenda);
        this.gestor_Encomendas.FinalizadaToExpedida(encomenda.getId(), this.data); // adicionei 03/05
        this.gestor_Utilizadores.novoCarrinho(this.utilizador);
        // adicionar nas encomendas realizadas

        // Faturação
        this.gestor_Utilizadores.adicionaFaturaComprador(this.utilizador, true, encomenda.getId(), nif,
                encomenda.getEncomenda(), preco_artigos, preco_transporte, preco);
        // Alterar as faturas dos vendedores para maps idEncomenda, Fatura para
        // facilitar a adição

        return preco;
    }

    public double finalizarEncomendaPorId(Long id_utilizador, LocalDate data) throws CarrinhoVazioException,
            ArtigoNaoEncontradoException, EncomendaExistenteException, EncomendaNaoEncontradaException {
        long nif = this.gestor_Utilizadores.devolveNif(id_utilizador);
        // Preço
        List<Long> transportadoras = new ArrayList<Long>();
        double preco_artigos = 0;
        for (String s : this.gestor_Utilizadores.verCarrinho(id_utilizador)) {
            // Alterar data
            // ---------------------------------------------------------------------------------------------
            Artigo artigo = this.gestor_Artigos.getArtigos().get(s);
            preco_artigos += artigo.preco_atual(data);
            long idTransportadora = artigo.getIdTransportadora();
            transportadoras.add(idTransportadora);
            this.gestor_Utilizadores.passaArtigoParaVendido(s, artigo.getIdVendedor());
            this.gestor_Utilizadores.adicionaFaturaVendedor(artigo.getIdVendedor(), false,
                    this.gestor_Utilizadores.getIdCarrinho(id_utilizador), nif, artigo.getCodigo_barras(),
                    artigo.preco_atual(data), artigo.getNovo(), Gestor_Artigos.getTaxaNovo(),
                    Gestor_Artigos.getTaxaUsado());
        }
        double preco_transporte = this.gestor_Trasportadoras.precoTransporte(transportadoras);
        double preco = preco_artigos + preco_transporte;

        // Carrinho
        Encomenda encomenda = this.gestor_Utilizadores.finalizaEncomenda(id_utilizador, data);
        // Alterar data, acho que funciona
        this.gestor_Artigos.alteraData(encomenda);
        this.gestor_Encomendas.adicionaEncomenda(encomenda);
        this.gestor_Encomendas.FinalizadaToExpedida(encomenda.getId(), data); // adicionei 03/05
        this.gestor_Utilizadores.novoCarrinho(id_utilizador);
        // adicionar nas encomendas realizadas

        // Faturação
        this.gestor_Utilizadores.adicionaFaturaComprador(id_utilizador, true, encomenda.getId(), nif,
                encomenda.getEncomenda(), preco_artigos, preco_transporte, preco);
        // Alterar as faturas dos vendedores para maps idEncomenda, Fatura para
        // facilitar a adição

        return preco;
    }

    // Funções para imprimir faturas
    public String imprimeFaturasCompras() {
        return this.gestor_Utilizadores.imprimeFaturasCompras(this.utilizador);
    }

    public String imprimeFaturasVendas() {
        return this.gestor_Utilizadores.imprimeFaturasVendas(this.utilizador);
    }

    public String imprime1FaturaCompra(long idEncomenda) {
        return this.gestor_Utilizadores.imprime1FaturaCompra(idEncomenda, this.utilizador);
    }

    public String imprime1FaturaVenda(long idEncomenda) {
        return this.gestor_Utilizadores.imprime1FaturaVenda(idEncomenda, this.utilizador);
    }

    // ---------------------------- Estado --------------------------------

    public void salvarEstado() throws IOException{
            FileOutputStream fileOut = new FileOutputStream("dados.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.writeObject(Artigo.getCodigo());
            out.writeObject(Artigo.getContador());
            out.writeObject(Encomenda.getCodigo());
            out.writeObject(Fatura.getCodigo());
            out.writeObject(Transportadora.getCodigo());
            out.writeObject(Utilizador.getCodigo());
            // Save contador in class Artigo

            // out.writeObject(Artigo.getContador());
            out.close();
            fileOut.close();
    }

    public static Vintage carregarEstado() {
        try {
            FileInputStream fileIn = new FileInputStream("dados.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // Load the vintage
            Vintage v = (Vintage) in.readObject();
            Artigo.setCodigo((String) in.readObject());
            Artigo.setContador((long) in.readObject());
            Encomenda.setCodigo((long) in.readObject());
            Fatura.setCodigo((long) in.readObject());
            Transportadora.setCodigo((long) in.readObject());
            Utilizador.setCodigo((long) in.readObject());

            in.close();
            fileIn.close();
            return v;
        } catch (IOException | ClassNotFoundException i) {
            return null;
        }

    }
    // ---------------------------- Querys ---------------------------------

    public String vendedorFacturou() {
        double max = 0;
        double temp;
        Utilizador vendedor = null;
        for (Utilizador u : this.gestor_Utilizadores.getUtilizadores().values()) {
            temp = this.gestor_Artigos.precoFaturou(u.getProdutosVendidos(), this.data);
            if (max < temp) {
                max = temp;
                vendedor = u;
            }
        }
        if (vendedor == null) {
            return "Não há vendedores";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String resultado = vendedor.getNome() + " Faturou " + df.format(max) + "€" + "\n";
        return resultado;

    }

    public String vendedorFacturouTempo(LocalDate data_inicio, LocalDate dia_final) {
        double max = 0;
        double temp;
        Utilizador vendedor = null;
        for (Utilizador u : this.gestor_Utilizadores.getUtilizadores().values()) {

            temp = this.gestor_Artigos.precoFaturouTempo(u.getProdutosVendidos(), data_inicio, dia_final);
            if (max < temp) {
                max = temp;
                vendedor = u;
            }
        }
        if (vendedor == null) {
            return "Não há vendedores";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String resultado = vendedor.getNome() + " Faturou " + df.format(max) + "€" + "\n";
        return resultado;
    }

    public String VintageFaturou() {
        double facturou = this.gestor_Artigos.vintageFaturou();
        String resultado = "A Vintage faturou " + facturou + "€" + "\n";
        return resultado;
    }

    public String TransportadoraMaiorVolume() throws TransportadoraNaoEncontradaException {
        String resultado = this.gestor_Trasportadoras.fatorouMais();
        return resultado;
    }

    public String compradorMaior(int i, LocalDate data_inicio, LocalDate dia_final)
            throws EncomendaNaoEncontradaException {
        int quantidade;
        Set<Utilizador> utilizador = new TreeSet<>();
        for (Utilizador u : this.gestor_Utilizadores.getUtilizadores().values()) {
            quantidade = 0;
            for (long e : u.getComprei()) {
                Encomenda encomenda = this.gestor_Encomendas.getEncomenda(e);
                if (encomenda.getData().isAfter(data_inicio) && encomenda.getData().isBefore(dia_final))
                    quantidade += this.gestor_Encomendas.EncomendaToArtigos(e).size();
            }
            u.setN_comprados(quantidade);
            utilizador.add(u);
        }
        String resultado = compradorMaiorString(utilizador, i);
        return resultado;

    }

    // Versão 2
    public String maiorComprador(int n, LocalDate data_inicio, LocalDate dia_final) {
        int quantidade = 0;
        Set<Long> kSet = this.gestor_Utilizadores.chaves();
        Set<Utilizador> ordem = new TreeSet<>();

        for (Long l : kSet) {
            Set<Long> encomendas = this.gestor_Utilizadores.getEncomendas(l);
            quantidade = this.gestor_Artigos.entreDatas(this.gestor_Encomendas.encomendasToArtigos(encomendas),
                    data_inicio, dia_final);
            this.gestor_Utilizadores.alteraQuantiComprada(l, quantidade);
            ordem.add(this.gestor_Utilizadores.devolveUtilizador(l));
        }
        String resultado = compradorMaiorString(ordem, n);
        return resultado;
    }

    private String compradorMaiorString(Set<Utilizador> ordem, int n) {
        StringBuilder sb = new StringBuilder();
        Iterator<Utilizador> it = ordem.iterator();

        if (n > ordem.size())
            n = ordem.size();

        while (it.hasNext() && n > 0) {
            Utilizador u = it.next();
            sb.append(u.getNome() + " comprou " + u.getN_comprados() + " artigos" + "\n");
            n--;
        }
        return sb.toString();
    }

    public String vendedorMaior(int n, LocalDate data_inicio, LocalDate dia_final) {
        int quantidade;
        Set<Utilizador> ordem = new TreeSet<>(new ComparatorVendas());
        for (Utilizador u : this.gestor_Utilizadores.getUtilizadores().values()) {
            quantidade = 0;
            quantidade = this.gestor_Artigos.entreDatas(u.getProdutosVendidos(), data_inicio, dia_final);
            u.setN_vendidos(quantidade);
            ordem.add(u);
        }
        String resultado = vendedorMaiorString(ordem, n);
        return resultado;

    }

    // Versão 2
    public String maiorVendedor(int n, LocalDate data_inicio, LocalDate dia_final) {
        int quantidade = 0;
        Set<Long> kSet = this.gestor_Utilizadores.chaves();
        Set<Utilizador> ordem = new TreeSet<>(new ComparatorVendas());

        for (Long l : kSet) {
            quantidade = this.gestor_Artigos.entreDatas(this.gestor_Utilizadores.artigosVendidos(l), data_inicio,
                    dia_final);
            this.gestor_Utilizadores.alteraQuantiVendida(l, quantidade);
            ordem.add(this.gestor_Utilizadores.devolveUtilizador(l));
        }
        String resultado = vendedorMaiorString(ordem, n);
        return resultado;
    }

    private String vendedorMaiorString(Set<Utilizador> ordem, int n) {
        StringBuilder sb = new StringBuilder();
        Iterator<Utilizador> it = ordem.iterator();

        if (ordem.size() < n)
            n = ordem.size();

        while (it.hasNext() && n > 0) {
            Utilizador u = it.next();
            sb.append(u.getNome() + " vendeu " + u.getN_vendidos() + " artigos" + "\n");
            n--;
        }
        return sb.toString();
    }

    public String imprimeTransportadoraspremium() {
        return this.gestor_Trasportadoras.transportadorasPremium();
    }

    public String imprimeFormulas() {
        return this.gestor_Trasportadoras.formulasDisponiveis();
    }

    public String imprimeFormulasNormais() {
        return this.gestor_Trasportadoras.formulasNormais();
    }

    public void alteraFormula(long idTransportadora, int formula) {
        this.gestor_Trasportadoras.alteraFormula(idTransportadora, formula);
    }

    // ----------------------------- Avançar o tempo ----------------------------
    public void avancarTempo(LocalDate dia) {
        this.setData(dia);
    }

    // ----------------------------------------------
    public String faturas_compras() {
        return this.gestor_Utilizadores.imprimeFaturasCompras(this.utilizador);

    }

    public String faturas_venda() {
        return this.gestor_Utilizadores.imprimeFaturasVendas(this.utilizador);
    }

    // ---------------------------------------------
    // Os prints são para sair par por agora, mas n sei como vaomos fazer os prints
    // +q é suposto só fazermos no menu
    public boolean comprarArtigo (Long id_utilizador, String id_artigo, LocalDate data) {
        try {
            this.adicionarArtigoCarrinhoPorID(id_artigo, id_utilizador, data);
        } catch (ArtigoNaoEncontradoException e) {
            return false;
        } catch (ArtigoExistenteException e) {
            return false;
        }
        try {
            this.finalizarEncomendaPorId(id_utilizador, data);
        } catch (ArtigoNaoEncontradoException e) {
            return false;
        } catch (CarrinhoVazioException e) {
            return false;
        } catch (EncomendaExistenteException e) {
            return false;
        } catch (EncomendaNaoEncontradaException e) {
            return false;
        }
        return true;

    }

    public String encomendasEmitidasVendedor(long id_vendedor) {
        return this.gestor_Utilizadores.imprimeFaturasVendas(id_vendedor);

    }

    public boolean ColocarvenderSapatilha(long id_utilizador, LocalDate data, String descricao, String marca,
            double preco_base, boolean uso, int estado_uso, int n_utilizadores, Long transportadora, long vendedor,
            int tamanho, boolean atacadores, String cor, int anoColecao, boolean premium, double desconto)
            throws ArtigoExistenteException {
        String id = this.gestor_Artigos.criar_artigo_sapatilhas(descricao, marca, preco_base, uso, estado_uso,
                n_utilizadores, transportadora, id_utilizador, tamanho, atacadores, cor, anoColecao, premium, 0);
        this.gestor_Utilizadores.vendeArtigo(id, id_utilizador);
        return true;
    }

    // Estamos aqui a imprimir coisas que não podemos
    public boolean ColocarVenderTshit(long id_utilizador, LocalDate data, String descricao, String marca,
            double preco_base, boolean uso, int estado_uso, int n_utilizadores, long transportadora, Tamanho tamanho,
            Padrao padrao) {
        String id = this.gestor_Artigos.criar_artigo_tshirt(descricao, marca, preco_base, uso, estado_uso,
                n_utilizadores,
                transportadora, id_utilizador, tamanho, padrao);
        try {
            this.gestor_Utilizadores.vendeArtigo(id, id_utilizador);
        } catch (ArtigoExistenteException e) {
            return false;
        }
        return true;

    }

    // Estamos aqui a imprimir coisas que não podemos
    public boolean ColocarVenderMala(long id_utilizador, LocalDate data, String descricao, String marca,
            double preco_base, boolean uso, int estado_uso, int n_utilizadores, long transportadora, int dimensao,
            String material, int ano, boolean premium, float valorizacao) {
        String id = this.gestor_Artigos.criar_artigo_mala(descricao, marca, preco_base, uso, estado_uso, n_utilizadores,
                transportadora, id_utilizador, dimensao, material, ano, premium, valorizacao);
        try {
            this.gestor_Utilizadores.vendeArtigo(id, id_utilizador);
        } catch (ArtigoExistenteException e) {
            return false;
        }

        return true;
    }
}

/*
 * // Para remover o carrinho todo
 * // Colcar o artigo a vende
 * // Criar uma finção que tire o id dos vendedores apartir dos artigos
 * ------------------------------------ Gestao de artigos
 * // Aqui um loop do id do artigo e o id de vendedor
 * // adicinonar o artigo disponivel
 * this.gestor_Utilizadores().removerArtigoEncomenda(artigo_adicionar, id
 * Vendedor ,this.utilizador);
 * case 4:
 * // Colocar o artigo disponivel a venda
 * this.gestor_Utilizadores.removerArtigoEncomenda(artigo_adicionar, id Vendedor
 * ,this.utilizador);
 * break;
 * case 5:
 * 
 * this.menu.imprimir(encomendasToString(this.gestor_Encomendas.getEncomendas())
 * );
 * break;
 * case 6:
 * // DE ID DE ARTIGO PARA ID DE TRANSPOORTADORA
 * 
 * long encomenda =this.menu.encomeda_id();
 * 
 */
