package TP2;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class Controlador implements Serializable {
    private Menu menu;
    private Vintage vintage;

    // Construtores
    public Controlador() {
        this.menu = new Menu();
        this.vintage = new Vintage();
    }

    // é preciso fazer clone?
    public Controlador(Menu menu, Vintage vintage) {
        this.menu = menu;
        this.vintage = vintage;
    }

    public Controlador(Controlador a) {
        this.menu = a.getMenu();
        this.vintage = a.getVintage();
    }


    public Menu getMenu() {
        return menu;
    }

    public Vintage getVintage() {
        return vintage;
    }

    public void inicio_controlador() {
        this.povoar();
        int e;
        do {        
            this.menu.tempo_vintage(this.vintage.getData().toString());
            e = this.menu.iniciar_menu();

            switch (e) {
                case 1:
                    // Login -------------------------------
                    if (this.login() == true) {
                        //this.menu.clear();
                        this.escolha();
                    }
                    // this.menu.clear();
                    break;
                case 2:
                    // Registo -------------------------------
                    this.registar();
                    // this.menu.clear();
                    break;
                case 3:
                    this.escolha_query();
                    // this.menu.clear();
                    break;
                case 4:
                    this.escolha_transportadora();
                    break;
                // this.menu.clear();
                case 5:
                    LocalDate data = this.menu.tempo_avançar(this.vintage.getData());
                    this.vintage.avancarTempo(data);
                    this.menu.tempo_avançado();
                    break;
                case 6:
                this.parsing();
                break;
                case 7:
                    this.menu.sair_print();
                    this.salvarEstado();
                    break;
                default:
                    break;
            }
        } while (e != 7);

    }

    public void povoar(){
        try {
            this.vintage.povoamento();
        } catch (ArtigoExistenteException e) {
            this.menu.imprimir(e.getMessage());
        }
    }
    public void salvarEstado(){
        try {
            this.vintage.salvarEstado();
        } catch (IOException e) {
            this.menu.imprimir(e.getMessage());
        }
    }

    public boolean login() {
        // this.menu.clear();
        this.menu.login();
        String email = this.menu.pedir_email();
        boolean email_correto = this.vintage.existeEmail(email);
        if (email_correto == false) {
            // this.menu.clear();
            this.menu.email_nao_existe();
            return false;
        } else {
            String password = this.menu.pedir_password();
            boolean password_correta = this.vintage.verificaPassword(email, password);
            while (password_correta == false) { // verificar se a password está correta
                password = this.menu.pedir_password();
                password_correta = this.vintage.verificaPassword(email, password);

            } // Vai retornar o id do utilizador se for -1

            this.vintage.login_utlizador(email); // Aqui faz set do utilizador que está na vinatge, passando o email
            this.menu.login_sucesso();
            return true;
        }

    }

    public void registar() {
        // this.menu.clear();
        this.menu.registo();
        String nome_registo = this.menu.pedir_nome();
        String morada_registo = this.menu.pedir_morada();
        long nif = this.menu.pedir_nif();
        String email_registo = this.menu.pedir_email();
        Boolean email_existe = this.vintage.existeEmail(email_registo);
        while (email_existe == true) { // falta verificar se o email já existe
            this.menu.email_ja_existe_print();
            email_registo = this.menu.pedir_email();
            email_existe = this.vintage.existeEmail(email_registo);
        }
        String password_registo = this.menu.pedir_password();
        this.vintage.adicionaUtilizador(email_registo, nome_registo, morada_registo, nif, password_registo); // Adicionar
                                                                                                             // o
                                                                                                             // utilizador
                                                                                                             // ao
                                                                                                             // gestor
                                                                                                             // de
                                                                                                             // utilizadores
        this.menu.registo_sucesso();

    }

    public void escolha() {
        int e;
        do {
            e = this.menu.menu_escolha();
            switch (e) {
                case 1:

                    // Comprar -------------------------------
                    this.menu.imprimir(this.vintage.todosArtigos()); // Isto ainda precicamos de ver como vamos fazer o
                                                                     // modo paginação aqui e os artigos que estão a
                                                                     // venda
                    this.adicionar_artigo_carrinho();

                    break;
                case 2:
                    this.escolha_pesquica();
                    break;
                case 3:
                    this.escolha_venda();
                    break;
                case 4:
                    this.escolha_carrinho();
                    break;
                case 5:
                    this.escolha_faturas();
                    break;
                case 6:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 6);

    }

    public void escolha_faturas() {
        int e;
        do {
            e = this.menu.menu_faturas();

            switch (e) {
                case 1:
                    this.menu.faturas_compras();
                    this.menu.imprimir(this.vintage.faturas_compras());
                    break;
                case 2:
                    this.menu.faturas_vendas();
                    this.menu.imprimir(this.vintage.faturas_venda());
                    break;
                case 3:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 3);
    }

    public void escolha_pesquica() {
        int e;
        do {
            e = this.menu.menu_pesquisas();
            switch (e) {
                case 1:
                    // Pesquisa por id
                    String id = this.menu.pedir_id_artigo();
                    boolean artigo_existe = this.vintage.existeArtigoId(id);
                    if (artigo_existe == false)
                        this.menu.artigo_n_encontrado();
                    this.menu.imprimir(this.vintage.artigoID(id));
                    break;
                case 2:
                    // Pesquisa por tipo
                    this.escolha_tipo();
                    break;
                case 3:
                    // Sair
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 3);

    }

    public void escolha_tipo() {
        int e;
        do {
            e = this.menu.menu_pesquisas_tipo();

            switch (e) {
                case 1:
                    this.escolha_sapatilhas();
                    break;
                case 2:
                    this.escolha_tshit();
                    break;
                case 3:
                    this.escolha_malas();
                    break;
                case 4:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 4);
    }

    public void escolha_sapatilhas() {
        int e;
        do {
            e = this.menu.menu_sapatilha_pesquisa();

            switch (e) {
                case 1:
                    String marca = this.menu.pedir_marca();
                    this.menu.imprimir(this.vintage.filtraAMarcaSapatilhas(marca));

                    break;
                case 2:
                    Boolean novo = this.menu.pedir_novo();
                    this.menu.imprimir(this.vintage.filtraSapatilhasNovo(novo));
                    break;
                case 3:
                    int tamanho = this.menu.pedir_tamanho_sapatilha();
                    this.menu.imprimir(this.vintage.filtraSapatilhasTamanho(tamanho));
                    break;
                case 4:
                    String cor = this.menu.pedir_cor();
                    this.menu.imprimir(this.vintage.filtraSapatilhasCor(cor));
                    break;
                case 5:
                    // podemos colcar isto a pesquisar por todos os parametros mas vamos com calma
                default:
                    break;
            }
        } while (e != 5);
    }

    public void escolha_tshit() {
        int e;

        do {
            e = this.menu.menu_tshirt_pesquisa();

            switch (e) {
                case 1:
                    String marca = this.menu.pedir_marca();
                    this.menu.imprimir(this.vintage.filtraTshirtsMarca(marca));
                    break;
                case 2:
                    Boolean novo = this.menu.pedir_novo();
                    this.menu.imprimir(this.vintage.filtraTshirtsNovo(novo));
                    break;
                case 3:
                    Tamanho tamanho = this.menu.pedir_tamanho_tshirt();
                    this.menu.imprimir(this.vintage.filtraTshirtsTamanho(tamanho));
                    break;
                case 4:
                    Padrao padrao = this.menu.pedir_padrao();
                    this.menu.imprimir(this.vintage.filtraTshirtsPadrao(padrao));
                    break;
                case 5:
                    this.menu.sair_print();
                    // podemos colcar isto a pesquisar por todos os parametros mas vamos com calma

                default:
                    break;
            }
        } while (e != 5);

    }

    // -------------------------------------------- Tenho de adicionar um metedo de
    // dizer se o produto não está disponivel, neste momento só está a imprimir
    public void escolha_malas() {
        int e;
        do {
            e = this.menu.menu_malas_pesquisa();
            switch (e) {
                case 1:
                    String marca = this.menu.pedir_marca();
                    String modelo = this.vintage.filtraMalasMarca(marca);
                    if (modelo == null)
                        this.menu.artigo_n_encontrado();
                    else
                        this.menu.imprimir(modelo);
                    break;
                case 2:
                    Boolean novo = this.menu.pedir_novo();
                    this.menu.imprimir(this.vintage.filtraMalasNovo(novo));
                    break;
                case 3:
                    int dimensao = this.menu.pedir_dimensao();
                    this.menu.imprimir(this.vintage.filtraMalasDimensao(dimensao));
                    break;
                case 5:
                    String material = this.menu.pedir_material();
                    this.menu.imprimir(this.vintage.filtraMalasMaterial(material));
                    break;
                case 4:
                    int ano_colecao = this.menu.pedir_ano_colecao(this.vintage.getData());
                    this.menu.imprimir(this.vintage.filtraMalasColecao(ano_colecao));
                    // podemos colcar isto a pesquisar por todos os parametros mas vamos com calma
                case 6:
                default:
                    break;
            }
        } while (e != 6);

    }

    public void escolha_venda() {
        int e;
        do {
            e = this.menu.menu_venda();
            switch (e) {
                case 1:
                    this.menu.meusArtigos();
                    String meusartigos = this.vintage.meusArtigos();
                    // if (this.vintage.meusArtigos == null) this.menu.artigo_n_encontrado();
                    this.menu.imprimir(meusartigos);
                    // this.menu.artigo_retirado();
                    break;
                case 2:
                    this.escolha_venda_tipo();
                    this.menu.artigo_a_venda();
                    break;
                case 3:
                    this.menu.print_tirar_produtos_venda();
                    this.menu_venda_retirar_artigo();
                    break;
                case 4:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 4);

    }

    public void escolha_venda_tipo() {
        int e;
        do {
            e = this.menu.menu_venda_tipos();
            switch (e) {
                case 1:
                    this.escolha_venda_sapatilhas();
                    break;
                case 2:
                    this.escolha_venda_tshirt();
                    break;
                case 3:
                    this.escolha_venda_malas();
                    break;
                case 4:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 4);

    }

    // Adicionar aqui verificações que ele realmente está a colcar o int e o que
    // for, para não dar erro
    public void escolha_venda_sapatilhas() {
        int n_utilizadores = -1;
        int estado = -1;
        double desconto = 0;
        String marca = this.menu.pedir_marca();
        String descricao = this.menu.pedir_descricao();
        Boolean novo = this.menu.pedir_novo();
        if (novo == false) {
            n_utilizadores = this.menu.pedir_numero_utlizador();
            estado = this.menu.pedir_estado();
        }
        int tamanho = this.menu.pedir_tamanho_sapatilha();
        boolean atacadores = this.menu.atacadores();
        String cor = this.menu.pedir_cor();
        double preco = this.menu.pedir_preco();
        int data = this.menu.pedir_ano_colecao(this.vintage.getData());
        boolean premium = this.menu.pedir_premium();
        boolean existe;
        long id_transporadora;
        if (premium == true) {
            this.menu.transportadoras_premium();
            this.menu.imprimir(vintage.imprimeTransportadoraspremium());
            id_transporadora = this.menu.pedir_transportadora();
            existe = this.vintage.existe_transpotadoraPremium(id_transporadora); // Ver se existe a transportadora
            while (existe == false) {
                this.menu.imprimir(this.vintage.imprimeTransportadoraspremium());
                id_transporadora = this.menu.pedir_transportadora(); // Aqui tenho o mesmo problema de cima tenho que
                                                                     // adicionar uma forma de sair
                existe = this.vintage.existe_transpotadoraPremium(id_transporadora);
            }
        } else {
            this.menu.print_transportadoras();
            this.menu.imprimir(this.vintage.imprimeTransportadorasNormais());
            id_transporadora = this.menu.pedir_transportadora();
            existe = this.vintage.existe_transpotadoraNormal(id_transporadora); // Ver se existe a transportadora
            while (existe == false) {
                this.menu.imprimir(this.vintage.imprimeTransportadorasNormais());
                id_transporadora = this.menu.pedir_transportadora(); // Aqui tenho o mesmo problema de cima tenho que
                                                                     // adicionar uma forma de sair
                existe = this.vintage.existe_transpotadoraNormal(id_transporadora);
            }
        }

        if (premium == false) {
            if ((novo == false) || (tamanho > 45 && novo == true)) {
                desconto = this.menu.pedir_desconto();
            }
        }
        try {
            this.vintage.criaSapatilha(descricao, marca, preco, novo, estado, n_utilizadores, id_transporadora, tamanho,
                    atacadores, cor, data, premium, desconto);
        } catch (ArtigoExistenteException e) {
            this.menu.imprimir(e.getMessage());

        }
    }

    public void escolha_venda_tshirt() {
        int n_utilizadores = -1;
        int estado = -1;
        String marca = this.menu.pedir_marca();
        String descricao = this.menu.pedir_descricao();
        double preco = this.menu.pedir_preco();
        Boolean novo = this.menu.pedir_novo();
        Padrao padrao = this.menu.pedir_padrao();
        if (novo == false) {
            n_utilizadores = this.menu.pedir_numero_utlizador();
            estado = this.menu.pedir_estado();
        }
        Tamanho tamanho = this.menu.pedir_tamanho_tshirt();
        /*
         * boolean premium = this.menu.pedir_premium();
         * double desconto = this.menu.pedir_desconto();
         */
        this.menu.imprimir(this.vintage.imprimeTransportadoras()); // Aqui vai imprimr as transportadoras
        long id_transporadora = this.menu.pedir_transportadora();
        boolean existe = this.vintage.existeTranspotadora(id_transporadora); // Ver se existe a transportadora
        while (existe == false) {
            this.menu.imprimir(this.vintage.imprimeTransportadoras());
            id_transporadora = this.menu.pedir_transportadora(); // Aqui tenho o mesmo problema de cima tenho que
                                                                 // adicionar uma forma de sair
            existe = this.vintage.existeTranspotadora(id_transporadora);
        }
        try {
            this.vintage.criaTshirt(descricao, marca, preco, novo, estado, n_utilizadores, id_transporadora, tamanho,
                    padrao);
        } catch (ArtigoExistenteException d) {
            this.menu.imprimir(d.getMessage());
            this.escolha();
        }
    }

    public void escolha_venda_malas() {
        int n_utilizadores = -1;
        int estado = -1;
        float valorizacao = 1;
        String marca = this.menu.pedir_marca();
        String descricao = this.menu.pedir_descricao();
        double preco = this.menu.pedir_preco();
        Boolean novo = this.menu.pedir_novo();
        if (novo == false) {
            n_utilizadores = this.menu.pedir_numero_utlizador();
            estado = this.menu.pedir_estado();
        }
        int dimensao = this.menu.pedir_tamanho_mala();
        String material = this.menu.pedir_material();
        int ano = this.menu.pedir_ano_colecao(this.vintage.getData());
        boolean premium = this.menu.pedir_premium();
        if (premium == true) {
            valorizacao = this.menu.pedir_valorizacao();
            this.menu.transportadoras_premium();
            this.menu.imprimir(this.vintage.imprimeTransportadoraspremium());
        } else {
            this.menu.imprimir(this.vintage.imprimeTransportadorasNormais()); // Aqui vai imprimr as transportadoras
        }
        long id_transporadora = this.menu.pedir_transportadora();
        boolean existe = this.vintage.existeTranspotadora(id_transporadora); // Ver se existe a transportadora
        while (existe == false) {
            this.menu.imprimir(this.vintage.imprimeTransportadoras());
            id_transporadora = this.menu.pedir_transportadora(); // Aqui tenho o mesmo problema de cima tenho que
                                                                 // adicionar uma forma de sair
            existe = this.vintage.existeTranspotadora(id_transporadora);
        }
        try {
            this.vintage.criaMala(descricao, marca, preco, novo, estado, n_utilizadores, id_transporadora, dimensao,
                    material, ano, premium, valorizacao);
        } catch (ArtigoExistenteException e) {
            this.menu.imprimir(e.getMessage());
        }
    }

    public void adicionar_artigo_carrinho() {
        String artigo_adicionar = this.menu.pedir_artigo_adicionar();
        // if (this.vintage.existeArtigoId(artigo_adicionar) == false){
        // this.menu.artigo_n_encontrado();
        // }else{
        try {
            this.vintage.adicionarArtigoCarrinho(artigo_adicionar);
        } catch (ArtigoNaoEncontradoException e) {
            this.menu.imprimir(e.getMessage());
        } catch (ArtigoExistenteException e) {
            this.menu.imprimir(e.getMessage());
        }

        // this.menu.print_carrinho();
        // this.menu.imprimir(this.vintage.imprimeCarrinho());
        // }
    }

    public void menu_venda_retirar_artigo() {
        String artigo = this.menu.pedir_id_artigo();
        if (this.vintage.existeArtigoId(artigo) == false) {
            this.menu.artigo_n_encontrado();
        } else {
            try {
                this.vintage.removerArtigosCarrinho(artigo);
            } catch (ArtigoNaoEncontradoException e) {
                this.menu.imprimir(e.getMessage());
            }
        }

    }

    public void escolha_carrinho() {
        int e;
        do {
            e = this.menu.menu_encomedas();
            switch (e) {
                case 1:
                    // Ver Carrinho
                    this.menu.print_carrinho();
                    this.menu.imprimir(this.vintage.imprimeCarrinho());
                    break;

                case 2:
                    // Adicionar artigo ao carrinho
                    this.adicionar_artigo_carrinho();

                    break;
                case 3:
                    try {
                        this.vintage.removerTodosArtigosCarrinho();
                    } catch (ArtigoNaoEncontradoException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                        // this.escolha_carrinho();
                    }
                    break;

                case 4:
                    // Remover artigo da encomenda
                    String artigo = menu.pedir_artigo_remover();
                    // this.menu.pedir_artigo_remover();
                    try {
                        this.vintage.removerArtigosCarrinho(artigo);
                    } catch (ArtigoNaoEncontradoException d) {
                        this.menu.imprimir(d.getMessage());
                        // this.escolha_carrinho();
                        break;
                    }
                    break;

                case 5:
                    // Encomendas realizadas
                    this.menu.print_encomendas_realizadas();

                    this.menu.imprimir(this.vintage.encomendasRealizadas());
                    break;

                case 6:
                    // Encomendas devolver
                    long encomenda = this.menu.encomeda_id();
                    boolean devolver;
                    try {
                        devolver = this.vintage.encomendasDevolver(encomenda);
                        if (devolver == true) { // Função para remover e para ver se pode remover
                            this.menu.encomendas_retiradas();
                        }
                    } catch (ArtigoNaoEncontradoException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                        // this.escolha_carrinho();
                    } catch (EncomendaNaoEncontradaException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                        // this.escolha_carrinho();
                    } catch (EncomendaDevolucaoImpossivelException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                    }

                    break;
                case 7:
                    // Encomendas devolvidas
                    this.menu.encomedas_devolvidas();
                    this.menu.imprimir(this.vintage.encomendasDevolvidas());

                    break;
                case 8:
                    // Finalizar encomenda
                    double preco = 0;
                    try {
                        preco = this.vintage.finalizarEncomenda();
                    } catch (ArtigoNaoEncontradoException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                    } catch (CarrinhoVazioException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                    } catch (EncomendaExistenteException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                    } catch (EncomendaNaoEncontradaException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                    }

                    this.menu.finalizar();
                    DecimalFormat df = new DecimalFormat("0.00");
                    String precoFormatado = df.format(preco);
                    this.menu.preco_final();
                    this.menu.imprimir(precoFormatado + "€");
                    break;
                case 9:
                    // Sair
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 9);

    }

    public void escolha_query() {
        int e;
        int i;
        LocalDate datainical;
        LocalDate datafinal;

        do {
            e = this.menu.escolha_querys();
            switch (e) {
                case 1:
                    // Vendedor que mais facturou desde sempre
                    this.menu.vendedor_facturou();
                    this.menu.imprimir(this.vintage.vendedorFacturou());
                    break;
                case 2:
                    // Vendedor que mais num período de tempo
                    this.menu.print_vendedor_mais_facturou();
                    datainical = this.menu.pedir_data_inicio();
                    datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    while (datainical.isAfter(datafinal) == true) {
                        this.menu.imprimir("A data inicial não pode ser depois da data final. Insira novamente");
                        datainical = this.menu.pedir_data_inicio();
                        datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    }
                    this.menu.imprimir(this.vintage.vendedorFacturouTempo(datainical, datafinal));
                    break;
                case 3:
                    // Listar as encomendas emitidas por vendedor
                    long vendedor = this.menu.pedir_vendedor();
                    if (this.vintage.existeIdUtilizador(vendedor) == false) {
                        this.menu.vendedor_n_encontrado();
                        this.escolha_query();
                    } else {
                        this.menu.imprimir(this.vintage.encomendasEmitidasVendedor(vendedor));
                    }

                    break;

                case 4:
                    // Transportador com maior volume de facturação
                    this.menu.transportadora_mais_facturou();
                    try {
                        this.menu.imprimir(this.vintage.TransportadoraMaiorVolume());
                    } catch (TransportadoraNaoEncontradaException d) {
                        this.menu.imprimir(d.getMessage());
                        break;
                        // this.escolha_query();
                    }

                    break;

                case 5:
                    // Fornecer uma ordenação dos maiores compradores
                    i = this.menu.n_maiores();
                    datainical = this.menu.pedir_data_inicio();
                    datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    while (datainical.isAfter(datafinal) == true) {
                        this.menu.imprimir("A data inicial não pode ser depois da data final. Insira novamente");
                        datainical = this.menu.pedir_data_inicio();
                        datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    }
                    this.menu.imprimir(this.vintage.maiorComprador(i, datainical, datafinal));
                    break;

                case 6:
                    // Fornecer uma ordenação dos maiores vendedores
                    i = this.menu.n_maiores();
                    datainical = this.menu.pedir_data_inicio();
                    datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    while (datainical.isAfter(datafinal) == true) {
                        this.menu.imprimir("A data inicial não pode ser depois da data final. Insira novamente");
                        datainical = this.menu.pedir_data_inicio();
                        datafinal = this.menu.pedir_data_fim(this.vintage.getData());
                    }
                    this.menu.imprimir(this.vintage.maiorVendedor(i, datainical, datafinal));
                    break;

                case 7:
                    // Determinar quanto dinheiro ganhou o Vintage
                    this.menu.imprimir(this.vintage.VintageFaturou());
                    break;
                case 8:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 8);

    }

    public void escolha_transportadora() {
        int e;

        do {
            e = this.menu.menuTrasnportadoras();
            switch (e) {
                case 1:
                    // Criar Transportadora
                    this.menu.adicionar_trasnportadora();
                    String nome = this.menu.pedir_nome();
                    double valor_pequenas = this.menu.pedir_valor_pequeno();
                    double valor_medio = this.menu.pedir_valor_medio();
                    double valor_grande = this.menu.pedir_valor_grande();
                    double impostos = this.menu.pedir_impostos();
                    double margemlucro = this.menu.pedir_margem_lucro();
                    boolean premium = this.menu.pedir_premium();
                    int formula;
                    if (premium == true) {
                        this.menu.imprimir(this.vintage.imprimeFormulas());
                        formula = this.menu.pedir_formula(3);
                    } else {
                        this.menu.imprimir(this.vintage.imprimeFormulasNormais());
                        formula = this.menu.pedir_formula(2);
                    }
                    this.vintage.adicinonarTransportadora(nome, valor_pequenas, valor_medio, valor_grande, impostos,
                            margemlucro, premium, formula);
                    break;
                case 2:
                    // Mudar a formula
                    this.menu.imprimir(this.vintage.imprimeTransportadoras());
                    long l = this.menu.pedir_transportadora();
                    while (!this.vintage.existeTranspotadora(l)) {
                        this.menu.imprimir("Transportadora não encontrada. Por favor, insira um ID válido.");
                        l = this.menu.pedir_transportadora();
                    }
                    // Apresentar as formulas
                    int formula2;
                    if (this.vintage.existe_transpotadoraNormal(l) == true) {
                        this.menu.imprimir(this.vintage.imprimeFormulasNormais());
                        formula2 = this.menu.pedir_formula(2);
                    } else {
                        this.menu.imprimir(this.vintage.imprimeFormulas());
                        formula2 = this.menu.pedir_formula(3);
                    }
                    // int formula2 = this.menu.pedir_formula();
                    this.vintage.alteraFormula(l, formula2);
                    break;
                case 3:
                    this.menu.sair_print();
                    break;
                default:
                    break;
            }
        } while (e != 3);

    }

    public void parsing() {
        String local = this.menu.pedir_local_ficheiro();
        Parsing parsing = new Parsing(this.vintage, this.menu);
        this.vintage.setData(parsing.leitura_ficheiro(local, this.vintage.getData()));
    }
}