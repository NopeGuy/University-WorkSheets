package TP2;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
    public Scanner scanner;

    public int iniciar_menu() {
        scanner = new Scanner(System.in);
        int escolha;

        System.out.print("Bem vindo a Vintage \nEscolha uma opção:\n");
        System.out.println("1 - Login");
        System.out.println("2 - Registar");
        System.out.println("3 - Querys");
        System.out.println("4 - Transportadora");
        System.out.println("5 - Avançar tempo");
        System.out.println("6 - Ver de um ficheiro");
        System.out.println("7 - Sair");
    
        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = iniciar_menu();
        }

        if(escolha < 1 || escolha > 7){
            System.out.println("Opção não disponível");
            scanner.nextLine();
            escolha = iniciar_menu();
        }

        return escolha;
    }
    public void tempo_vintage(String tempo){
        System.out.println("Tempo Vintage: " + tempo);
    }


      public void clear(){
      System.out.print("\033[H\033[2J");
      System.out.flush();
      }
     
    public String pedir_email() {
        scanner = new Scanner(System.in);
        System.out.print("Digite seu email: ");

        try {
            String email = scanner.nextLine();
            return email;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o email. ");
            return pedir_email();
        }
    }

    public String pedir_nome() {
        scanner = new Scanner(System.in);
        System.out.print("Digite seu nome: ");

        try {
            String nome = scanner.nextLine();
            return nome;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o nome. ");
            return pedir_nome();
        }
        // scanner.close();
    }

    public long pedir_nif() {
        scanner = new Scanner(System.in);
        System.out.print("Digite seu NIF: ");
        long nif;

        try {
            nif = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine();
            nif = pedir_nif();
        }
        return nif;
        // scanner.close();
    }

    public String pedir_morada() {
        scanner = new Scanner(System.in);
        String morada = "";

        try {
            System.out.print("Digite sua cidade: ");
            String cidade = scanner.nextLine();

            System.out.print("Digite sua rua: ");
            String rua = scanner.nextLine();

            morada = cidade + " " + rua;
        } catch (NoSuchElementException e) {
            System.out.println("Ocorreu um erro ao solicitar a morada. ");
            return pedir_morada();
        }

        // scanner.close();
        return morada;
    }

    public String pedir_password() {
        scanner = new Scanner(System.in);
        System.out.print("Digite seu password: ");

        try {
            String password = scanner.nextLine();
            return password;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler a password. ");
            return pedir_password();
        }

        // scanner.close();
    }

    public void email_nao_existe() {
        System.out.println("Email não existe!");
    }

    public void password_incorreta() {
        System.out.println("Password incorreta!");
    }

    public void login_sucesso() {
        System.out.println("Login com sucesso!");
    }

    public void login() {
        System.out.println("Login:");
    }

    public void registo() {
        System.out.println("Registo:");
    }

    public void registo_sucesso() {
        System.out.println("Registo com sucesso!");
    }

    public void email_ja_existe_print() {
        System.out.println("Email já existe!");
    }

    public void sair_print() {
        System.out.println("Sair...");
    }

    public int menu_escolha() {
        scanner = new Scanner(System.in);
        int escolha;
        System.out.println("1 - Comprar");
        System.out.println("2 - Pesquisar");
        System.out.println("3 - Vender");
        System.out.println("4 - Opções das encomendas");
        System.out.println("5 - Ver faturas");
        System.out.println("6 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_escolha();
        }

        if (escolha < 1 || escolha > 6) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_escolha();
        }
        // scanner.close();
        return escolha;
    }

    public int menu_faturas() {
        scanner = new Scanner(System.in);
        int escolha;
        System.out.println("1 - Faturas das compras");
        System.out.println("2 - Faturas das vendas");
        System.out.println("3 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_faturas();
        }

        if (escolha < 1 || escolha > 3) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_faturas();
        }

        // scanner.close();
        return escolha;
    }

    public void faturas_compras() {
        System.out.println("Faturas das compras:");
    }

    public void faturas_vendas() {
        System.out.println("Faturas das vendas:");
    }

    public void print_de_comprar() {
        System.out.println("Lista de artigos:");
    }

    public int menu_pesquisas() {
        // this.clear();
        scanner = new Scanner(System.in);
        int escolha;

        System.out.println("Escolha uma opção:");
        System.out.println("1 - Pesquisar por id");
        System.out.println("2 - Pesquisar por tipo");
        System.out.println("3 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_pesquisas();
        }

        if (escolha < 1 || escolha > 3) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_pesquisas();
        }

        // scanner.close();
        return escolha;
    }

    public void artigo_n_encontrado() {
        System.out.println("Artigo não encontrado!");
    }

    public String pedir_id_artigo() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o id do artigo: ");

        try {
            String id = scanner.nextLine();
            return id;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o ID do artigo. ");
            return pedir_id_artigo();
        }
        // scanner.close();
    }

    public int menu_pesquisas_tipo() {
        // this.clear();
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        int escolha;

        System.out.println("1 - Sapatilhas");
        System.out.println("2 - T-shirts");
        System.out.println("3 - Malas");
        System.out.println("4 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_pesquisas_tipo();
        }

        if (escolha < 1 || escolha > 4) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_pesquisas_tipo();
        }

        // scanner.close();
        return escolha;
    }

    public int menu_sapatilha_pesquisa() {
        // this.clear();
        scanner = new Scanner(System.in);
        int escolha;

        System.out.println("Escolha uma opção:");
        System.out.println("1 - Marca");
        System.out.println("2 - Novo");
        System.out.println("3 - Tamanho");
        System.out.println("4 - Cor");
        System.out.println("5 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_sapatilha_pesquisa();
        }

        if (escolha < 1 || escolha > 5) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_sapatilha_pesquisa();
        }

        // scanner.close();
        return escolha;
    }

    public int menu_tshirt_pesquisa() {
        // this.clear();
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        int escolha;

        System.out.println("1 - Marca");
        System.out.println("2 - Novo");
        System.out.println("3 - Tamanho");
        System.out.println("4 - Padrão");
        System.out.println("5 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_tshirt_pesquisa();
        }

        if (escolha < 1 || escolha > 5) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_tshirt_pesquisa();
        }

        // scanner.close();
        return escolha;
    }

    public int menu_malas_pesquisa() {
        // this.clear();
        scanner = new Scanner(System.in);
        int escolha;

        System.out.println("Escolha uma opção:");
        System.out.println("1 - Marca");
        System.out.println("2 - Novo");
        System.out.println("3 - Tamanho");
        System.out.println("4 - Ano de coleção");
        System.out.println("5 - Material");
        System.out.println("6 - Sair");

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_malas_pesquisa();
        }

        if (escolha < 1 || escolha > 6) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_malas_pesquisa();
        }

        // scanner.close();
        return escolha;
    }

    public String pedir_marca() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a marca: ");

        try {
            String marca = scanner.nextLine();
            return marca;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler a marca. ");
            return pedir_marca();
        }

        // scanner.close();
    }

    public int pedir_tamanho_sapatilha() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o tamanho: ");
        int tamanho;

        try {
            tamanho = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            tamanho = pedir_tamanho_sapatilha();
        }

        // scanner.close();
        return tamanho;

    }

    public Tamanho pedir_tamanho_tshirt() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o tamanho S/M/L/XL:");
        String tamanho = scanner.nextLine();

        if (tamanho.equals("S")) {
            return Tamanho.S;
        } else if (tamanho.equals("M")) {
            // scanner.close();
            return Tamanho.M;
        } else if (tamanho.equals("L")) {
            // scanner.close();
            return Tamanho.L;
        } else if (tamanho.equals("XL")) {
            // scanner.close();
            return Tamanho.XL;
        } else {
            System.out.println("Tamanho inválido. Digite S, M, L ou XL.");
            return pedir_tamanho_tshirt();
        }
        // scanner.close();
    }

    public int pedir_tamanho_mala() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o tamanho em cm: ");
        int tamanho;

        try {
            tamanho = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            tamanho = pedir_tamanho_mala();
        }
        // scanner.close();
        return tamanho;
    }

    public int pedir_numero_utlizador() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o número de utilizadores que o artigo teve: ");
        int numero;

        try {
            numero = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            numero = pedir_numero_utlizador();
        }

        // scanner.close();
        return numero;
    }

    public int pedir_estado() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o estado, (0/10): ");
        int estado;

        try {
            estado = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro entre 0 e 10.");
            scanner.nextLine(); // Limpa o buffer do scanner
            estado = pedir_estado();
        }

        if (estado < 0 || estado > 10) {
            System.out.println("Estado inválido!");
            scanner.nextLine();
            estado = iniciar_menu();
        }
        // scanner.close();
        return estado;
    }

    public boolean atacadores() {
        scanner = new Scanner(System.in);
        System.out.print("Tem atacadores, (true/false): ");
        boolean atacadores;

        try {
            atacadores = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite true ou false. ");
            scanner.nextLine(); // Limpa o buffer do scanner
            atacadores = atacadores();
        }
        // scanner.close();
        return atacadores;
    }

    public String pedir_cor() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a cor: ");

        try {
            String cor = scanner.nextLine();
            return cor;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler a cor. ");
            return pedir_cor();
        }
    }

    public boolean pedir_novo() {
        scanner = new Scanner(System.in);
        System.out.print("Novo, (true/false): ");
        boolean novo;

        try {
            novo = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite true ou false.");
            scanner.nextLine(); // Limpa o buffer do scanner
            novo = pedir_novo();
        }
        // scanner.close();
        return novo;
    }

    public void pesquisa_falhou() {
        System.out.println("Pesquisa falhou!");
    }

    public Padrao pedir_padrao() {
        scanner = new Scanner(System.in);
        System.out.print(" Padrão, (liso/riscas/palmeiras):");
        String padrao = scanner.nextLine();
        // scanner.close();
        if (padrao.equals("liso")) {
            return Padrao.liso;
        } else if (padrao.equals("riscas")) {
            return Padrao.riscas;
        } else if (padrao.equals("palmeiras")) {
            return Padrao.palmeiras;
        } else {
            System.out.println("Padrão inválido. Digite liso, riscas ou palmeiras.");
            return pedir_padrao();
        }
        // scanner.close();
    }

    public int pedir_ano() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o ano da coleção: ");
        int ano;

        try {
            ano = scanner.nextInt();
            if (ano > LocalDate.now().getYear()) {
                System.out.println("Ano inválido");
                scanner.nextLine();
                ano = pedir_ano();
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            ano = pedir_ano();
        }

        // scanner.close();
        return ano;
    }

    public String pedir_material() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o material: ");

        try {
            String material = scanner.nextLine();
            return material;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o material. ");
            return pedir_material();
        }
    }

    public int pedir_dimensao() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a dimensão: ");
        int dimensao;

        try {
            dimensao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            dimensao = pedir_dimensao();
        }
        // scanner.close();
        return dimensao;
    }

    public int pedir_ano_colecao(LocalDate data) {
        scanner = new Scanner(System.in);
        System.out.print("Digite o ano de coleção: ");
        int ano;

        try {
            ano = scanner.nextInt();
            if (ano > data.getYear()) {
                System.out.println("Ano inválido");
                scanner.nextLine();
                ano = pedir_ano();
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            ano = pedir_ano_colecao(data);
        }
        // scanner.close();
        return ano;
    }

    public int menu_venda() {
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Ver artigos à venda");
        System.out.println("2 - Colocar artigo à venda");
        System.out.println("3 - Tirar artigo à venda");
        System.out.println("4 - Sair");
        int escolha;

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_venda();
        }

        if (escolha < 1 || escolha > 4) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_venda();
        }

        // scanner.close();
        return escolha;
    }

    public void artigo_a_venda() {
        System.out.println("Artigo à venda!");
    }

    public void meusArtigos() {
        System.out.println("Artigos à venda:");
    }

    public void artigo_retirado() {
        System.out.println("Artigo retirado!");
    }

    public int menu_venda_tipos() {
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Sapatilhas");
        System.out.println("2 - T-shirts");
        System.out.println("3 - Malas");
        System.out.println("4 - Sair");
        int escolha;

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            // scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_venda_tipos();
        }

        if (escolha < 1 || escolha > 4) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_venda_tipos();
        }

        // scanner.close();
        return escolha;
    }

    public void print_tirar_produtos_venda() {
        System.out.println("Tirar produtos de venda:");
    }

    // Não pôs exception pq acho que vamos mudar
    public LocalDate pedir_data() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a data da coleção:yyyy-MM-dd:");
        LocalDate data = LocalDate.parse(scanner.nextLine());
        // scanner.close();
        return data;
    }

    public double pedir_preco() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o preço: ");
        double preco;

        try {
            preco = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            preco = pedir_preco();
        }
        // scanner.close();
        return preco;
    }

    public double pedir_desconto() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o desconto em percentagem: ");
        double desconto;

        try {
            desconto = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            desconto = pedir_desconto();
        }
        // scanner.close();
        return desconto;
    }

    // Transportadoras
    public int menuTrasnportadoras() {
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Criar transportadora");
        System.out.println("2 - Mudar a fórmula de cálculo");
        System.out.println("3 - Sair");
        int escolha;

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menuTrasnportadoras();
        }

        if (escolha < 1 || escolha > 3) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menuTrasnportadoras();
        }

        // scanner.close();
        return escolha;
    }

    public String pedirNomeTransportadora() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o nome da transportadora: ");

        try {
            String nome = scanner.nextLine();
            return nome;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler a transportadora.");
            return pedirNomeTransportadora();
        }
    }

    public void print_transportadoras() {
        System.out.println("Transportadoras:");
    }

    public long pedir_transportadora() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o id da transportadora: ");
        long numero;

        try {
            numero = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            numero = pedir_transportadora();
        }
        // scanner.close();
        return numero;
    }

    public String pedir_descricao() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a descrição: ");

        try {
            String descricao = scanner.nextLine();
            return descricao;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler a descrição. ");
            return pedir_descricao();
        }
    }

    public boolean pedir_premium() {
        scanner = new Scanner(System.in);
        System.out.print("É premium, (true/false): ");
        boolean premium;

        try {
            premium = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite true ou false.");
            scanner.nextLine(); // Limpa o buffer do scanner
            premium = pedir_premium();
        }
        // scanner.close();
        return premium;
    }

    public float pedir_valorizacao() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a valorização, em percentagem: ");
        float valorizacao;

        try {
            valorizacao = scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            valorizacao = pedir_valorizacao();
        }
        // scanner.close();
        return valorizacao;
    }

    public int menu_encomedas() {
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Ver carrinho");
        System.out.println("2 - Adicionar artigo ao carrinho");
        System.out.println("3 - Remover todos os artigos do carrinho");
        System.out.println("4 - Remover artigo do carrinho ");
        System.out.println("5 - Ver encomendas realizadas");
        System.out.println("6 - Devolver encomendas");
        System.out.println("7 - Ver encomendas devolvidas");
        System.out.println("8 - Finalizar carrinho");
        System.out.println("9 - Sair");
        int escolha;

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_encomedas();
        }

        if (escolha < 1 || escolha > 9) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = menu_encomedas();
        }

        // scanner.close();
        return escolha;
    }

    public void print_carrinho() {
        System.out.println("Carrinho:");
    }

    public String pedir_artigo_adicionar() {
        scanner = new Scanner(System.in);
        System.out.println("Adicionar artigo ao carrinho:");

        try {
            String escolha = scanner.nextLine();
            return escolha;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o artigo. ");
            return pedir_artigo_adicionar();
        }
    }

    public String pedir_artigo_remover() {
        scanner = new Scanner(System.in);
        System.out.println("Remover artigo do carrinho:");

        try {
            String escolha = scanner.nextLine();
            return escolha;
        } catch (NoSuchElementException e) {
            System.out.println("Não foi possível ler o artigo. ");
            return pedir_artigo_remover();
        }
    }

    public void imprimir(String s) {
        System.out.println(s);

    }

    public long encomeda_id() {
        scanner = new Scanner(System.in);
        System.out.println("Digite o id da encomenda:");
        long id;

        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            id = encomeda_id();
        }
        // scanner.close();
        return id;

    }

    public void print_encomendas_realizadas() {
        System.out.println("Encomendas realizadas:");
    }

    public void encomendas_retiradas() {
        System.out.println("Encomendas retirada!");
    }

    public void encomedas_devolvidas() {
        System.out.println("Encomendas devolvidas:");
    }

    public void finalizar() {
        System.out.println("Carrinho finalizado!");
    }

    public void adicionar_trasnportadora() {
        System.out.println("Adicionar transportadora:");
    }

    public double pedir_valor_pequeno() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o preço para as encomedas pequenas: ");
        double valor;

        try {
            valor = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            valor = pedir_valor_pequeno();
        }
        // scanner.close();
        return valor;
    }

    public double pedir_valor_medio() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o preço para as encomedas médias: ");
        double valor;

        try {
            valor = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            valor = pedir_valor_medio();
        }
        // scanner.close();
        return valor;
    }

    public void preco_final() {
        System.out.print("Preço final: ");
    }

    public double pedir_valor_grande() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o preço para as encomedas grandes: ");
        double valor;

        try {
            valor = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            valor = pedir_valor_grande();
        }
        // scanner.close();
        return valor;
    }

    public double pedir_impostos() {
        scanner = new Scanner(System.in);
        System.out.print("Digite o imposto, em percentagem: ");
        double valor;

        try {
            valor = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            valor = pedir_impostos();
        }
        // scanner.close();
        return (valor / (double) 100);
    }

    public double pedir_margem_lucro() {
        scanner = new Scanner(System.in);
        System.out.print("Digite a margem de lucro, em percentagem: ");
        double margem;

        try {
            margem = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer do scanner
            margem = pedir_margem_lucro();
        }
        // scanner.close();
        return (margem / (double) 100);
    }

    public int pedir_formula(int n) {
        scanner = new Scanner(System.in);
        System.out.print("Qual a fórmula que deseja usar: ");
        int formula;

        try {
            formula = scanner.nextInt();
            if (formula < 1 || formula > n) {
                System.out.println("Fórmula não disponível");
                scanner.nextLine(); // Limpa o buffer do scanner
                formula = pedir_formula(n);
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            formula = pedir_formula(n);
        }
        // scanner.close();
        return formula;
    }

    public boolean perguntar_sair() {
        scanner = new Scanner(System.in);
        System.out.print("Deseja sair? (true/false): ");
        boolean sair;

        try {
            sair = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite true ou false.");
            scanner.nextLine(); // Limpa o buffer do scanner
            sair = perguntar_sair();
        }
        // scanner.close();
        return sair;
    }

    // --------------- Menu Querys -----------
    public int escolha_querys() {
        scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Vendedor que mais faturou desde sempre");
        System.out.println("2 - Vendedor que mais faturou num período de tempo");
        System.out.println("3 - Listar as encomendas emitidas por vendedor");
        System.out.println("4 - Transportadora com maior volume de faturação");
        System.out.println("5 - Fornecer uma ordenação dos maiores compradores");
        System.out.println("6 - Fornecer uma ordenação dos maiores vendedores");
        System.out.println("7 - Determinar quanto dinheiro ganhou a Vintage");
        System.out.println("8 - Sair");
        int escolha;

        try {
            escolha = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out
                    .println("Entrada inválida. Por favor, digite um número inteiro correspondente à opção desejada.");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = escolha_querys();
        }

        if (escolha < 1 || escolha > 8) {
            System.out.println("Opção não disponível");
            scanner.nextLine(); // Limpa o buffer do scanner
            escolha = escolha_querys();
        }

        // scanner.close();
        return escolha;
    }

    public void print_vendedor_mais_facturou() {
        System.out.println("Vendedor que mais faturou desde sempre:");
    }

    public LocalDate pedir_data_inicio() {
        scanner = new Scanner(System.in);
        System.out.println("Digite a data de inicio:");

        int ano, mes, dia;
        try {
            System.out.print("Ano: ");
            ano = scanner.nextInt();
            System.out.print("Mês: ");
            mes = scanner.nextInt();
            System.out.print("Dia: ");
            dia = scanner.nextInt();
            LocalDate data = LocalDate.of(ano, mes, dia);
            return data;
        } catch (DateTimeException e) {
            System.out.println("Data inválida. Por favor, digite uma data válida.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return pedir_data_inicio();
        }
    }

    public LocalDate pedir_data_fim(LocalDate data_atual) {
        scanner = new Scanner(System.in);
        System.out.println("Digite a data de fim:");

        int ano, mes, dia;

        try {
            System.out.print("Ano: ");
            ano = scanner.nextInt();
            System.out.print("Mês: ");
            mes = scanner.nextInt();
            System.out.print("Dia: ");
            dia = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return pedir_data_fim(data_atual);
        }

        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            return data;
        } catch (DateTimeException e) {
            System.out.println("Data inválida. Por favor, digite uma data válida.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return pedir_data_fim(data_atual);
        }
    }

    public void transportadora_mais_facturou() {
        System.out.println("Transportadora que mais facturou:");
    }

    public int n_maiores() {
        scanner = new Scanner(System.in);
        System.out.println("Digite o Top N:");
        int n;

        try {
            n = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            n = n_maiores();
        }
        // scanner.close();
        return n;
    }

    public void vendedor_facturou() {
        System.out.println("Vendedor que mais faturou num período de tempo:");
    }

    public void transportadoras_premium() {
        System.out.println("Transportadoras Premium:");
    }

    public void tempo_avançado() {
        System.out.println("Tempo avançado!");
    }

    public LocalDate tempo_avançar(LocalDate data_atual) {
        scanner = new Scanner(System.in);
        System.out.println("Digite o tempo que deseja avançar:");

        int ano, mes, dia;

        try {
            System.out.print("Ano: ");
            ano = scanner.nextInt();
            System.out.print("Mês: ");
            mes = scanner.nextInt();
            System.out.print("Dia: ");
            dia = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return tempo_avançar(data_atual);
        }

        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            LocalDate hoje = data_atual;
            if (data.isBefore(hoje)) {
                System.out.println("A data inserida é anterior à data atual. Por favor, digite uma data futura.");
                scanner.nextLine(); // Limpa o buffer do scanner
                return tempo_avançar(data_atual);
            } else {
                return data;
            }
        } catch (DateTimeException e) {
            System.out.println("Data inválida. Por favor, digite uma data válida.");
            scanner.nextLine(); // Limpa o buffer do scanner
            return tempo_avançar(data_atual);
        }
    }

    public long pedir_vendedor() {
        scanner = new Scanner(System.in);
        System.out.println("Digite o id do vendedor:");
        long id;
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpa o buffer do scanner
            id = pedir_vendedor();
        }
        return id;
    }

    public void vendedor_n_encontrado() {
        System.out.println("Vendedor não encontrado!");
    }
    public String pedir_local_ficheiro(){
        scanner = new Scanner(System.in);    
        String local;    
        System.out.println("Digite o local do ficheiro:");
        try{
         local = scanner.nextLine();
        }
        catch(InputMismatchException e){
            System.out.println("Entrada inválida. Por favor, digite uma string.");
            scanner.nextLine(); // Limpa o buffer do scanner
            local = pedir_local_ficheiro();
        }
        return local;
    }

    /*
     * public boolean escolha_query(){
     * scanner = new Scanner(System.in); System.out.println("Queys:");
     * System.out.println("1 - Marca");
     * System.out.println("2 - Novo");
     * System.out.println("3 - Tamanho");
     * System.out.println("4 - Ano de coleção");
     * System.out.println("5 - Material");
     * System.out.println("6 - Sair");
     * 
     * }
     */

}

// public int iniciar_menu(){
// System.out.print("Bem vindo a Vintage \nEscolha uma opção:");

// scanner = new Scanner(System.in);// int escolha;
// System.out.println("1 - Login");
// System.out.println("2 - Registar");
// System.out.println("3 - Sair");

// escolha = scanner.nextInt();
// do{
// switch (escolha) {
// case 1:
// System.out.println("Login:");
// if(pedir_login() == true){
// this.exposição();

// }else{
// System.out.println("Login inválido!");
// this.iniciar_menu();
// }
// break;
// case 2:
// System.out.println("Registar:");
// if(fazer_registo() == true){

// }else{
// System.out.println("Registo inválido!");
// this.iniciar_menu();
// }
// break;
// case 3:
// System.out.println("Sair...");
// break;
// default:
// System.out.println("Opção inválida.");
// }
// }while(escolha != 3);

// scanner.close();

// }

// public boolean pedir_login(){
// scanner = new Scanner(System.in);// System.out.print("Digite seu email: ");
// String email = scanner.nextLine();
// System.out.print("Digite seu password: ");
// String password = scanner.nextLine();

// if(get_utlizador_emaail(email) == getutlizador_passoword(password)){
// this.get_utlizador_id(get_utlizador_emaail(email));
// return true;
// }
// return false;

// }
// public boolean fazer_registo(){
// scanner = new Scanner(System.in);// System.out.print("Registo na Vintage: ");

// System.out.print("Digite seu nome: ");
// String name = scanner.nextLine();

// System.out.print("Digite seu email: ");
// String email = scanner.nextLine();

// System.out.print("Digite seu cidade: ");
// String cidade = scanner.nextLine();

// System.out.print("Digite seu Rua: ");
// String rua = scanner.nextLine();

// String morada = cidade + " " + rua;

// System.out.print("Digite seu NIL: ");
// String nif = scanner.nextLine();

// System.out.print("Digite seu password: ");
// String password = scanner.nextLine();

// Utilizador utilizador = new Utilizador(email,name,morada,nif,password);
// scanner.close();

// retrun(utilizador.addutilizador());

// }

// public void exposiçãpo(){
// scanner = new Scanner(System.in);
// System.out.println("1 - Listar artigos");
// System.out.println("2 - Pesquisar artigo");
// System.out.println("2 - Vender artigo");
// System.out.println("4 - Sair artigo");
// int opecao;
// opecao = scanner.nextInt();

// switch (opecao) {
// case 1:
// this.listar_artigos();
// break;
// case 2:
// this.pesquisar_artigo();
// break;
// case 3:
// this.vender_artigo();
// break;
// case 4:
// System.out.println("Sair...");
// break;
// break;
// default:
// }

// scanner.close();

// }

// public void listar_artigos(){

// get_print_todos_artigos();

// }

// public void pesquisar_artigo(){

// scanner = new Scanner(System.in);
// System.out.println("1 - ID");
// System.out.println("2 - Modelo");
// System.out.println("3 - Tamannho");
// System.out.println("4 - Modelo e tamanho");
// System.out.println("5 - Sair da pesquisa dos artigos");
// int opecao
// opecao = scanner.nextInt();

// switch (opecao) {
// case 1:
// System.out.println("ID:");
// this.gestor_Artigos.pesquisar_id();
// break;
// case 2:
// this.gestor_Artigos.pesquisar_modelo();
// break;
// case 3:
// this.gestor_Artigos.pesquisar_tamanho();
// break;
// case 4:
// this.pesquisar_modelo_tamanho();
// break;
// case 5:
// System.out.println("Sair...");
// break;
// break;
// default:
// }

// scanner.close();

// }

// }
