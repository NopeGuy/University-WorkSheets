package TP2;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

//import javax.print.DocFlavor.STRING;

// Começemos pequeno, apenas a fazer isto:
// data, utlizador, Compra , artigo-id 
// data, utiliazdor, Venda, ......
// data, utiliazdor, Venda, Sapatilha, cor , anoColecao, premium, desconto
// data, utiliazdor, Venda, Mala, ......

public class Parsing {
    private Vintage vintage;
    private Menu menu;

    public Parsing(Vintage vintage, Menu menu) {
        this.vintage = vintage;
        this.menu = menu;
    }
    public Menu getMenu() {
        return menu;
    }
    public Vintage getVintage() {
        return vintage;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public void setVintage(Vintage vintage) {
        this.vintage = vintage;
    }

    public LocalDate leitura_ficheiro(String fileName, LocalDate data)  {
        
        File file = null;
        file = new File(fileName);
        Scanner scanner = null;
        LocalDate data_atual = data;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                this.menu.imprimir("Erro a ler o ficheiro");
            }
            try{
            while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if(linha.equals("")){
                continue;
            }
            String[] linha_saprada = linha.split(",");
            data_atual = adicionar_comando(linha_saprada, data);
            }

            scanner.close();
            }catch(ArtigoExistenteException e ){
            this.menu.imprimir(e.getMessage());
            }
           
        return data_atual;
    }

    public LocalDate adicionar_comando(String[] linha_saprada, LocalDate data_atual) throws ArtigoExistenteException {
        String[] data_string = linha_saprada[0].split(":");
        int dia = Integer.parseInt(data_string[0]);
        int mes = Integer.parseInt(data_string[1]);
        int ano = Integer.parseInt(data_string[2]);
        LocalDate data = LocalDate.of(ano, mes, dia);
        long id_utilizador = Long.parseLong(linha_saprada[1]);
        String id_artigo = linha_saprada[3];
        if (data_atual.isBefore(data)) {
            data_atual = data;
        }
        // Os printf são apenas para testar se está a ler e reconhecer os comandos
        if (this.vintage.existeIdUtilizador(id_utilizador) == true) {
                if (linha_saprada[2].equals("Comprar")) {
                    if (this.vintage.existeArtigoId(id_artigo) == true) {
                    if (this.vintage.comprarArtigo(id_utilizador, id_artigo, data) == true) {
                       this.menu.imprimir("Compra efetuada com sucesso do artigo " + id_artigo + " para o utilizador "+ id_utilizador);
                    } else {
                        this.menu.imprimir("Compra não efetuada do artigo " + id_artigo + " para o utilizador "+ id_utilizador);
                    }
                }
                }  if (linha_saprada[2].equals("Vender")) {
                    if (linha_saprada[3].equals("Sapatilha")) {
                        this.venderSapatilha(id_utilizador, data, linha_saprada);
                    } else if (linha_saprada[3].equals("Mala")) {
                        this.venderMala(id_utilizador, data, linha_saprada);
                    } else if (linha_saprada[3].equals("T-shirt")) {
                        this.venderTshit(id_utilizador, data, linha_saprada);
                    }

                }
        }
        return data_atual;
    }

    // novo -> data, utiliazdor, Venda, Sapatilha, premium, descricao, marca,
    // preco_base, uso, estado_uso, n_utilizadores, transportadora, tamanho,
    // atacadores, cor, anoColecao
    // premium -> data, utiliazdor, Venda, Sapatilha, premium, descricao, marca,
    // preco_base, uso, estado_uso, n_utilizadores, transportadora, tamanho,
    // atacadores, cor, anoColecao, desconto
    public void venderSapatilha(long id_utilizador, LocalDate data, String[] c) throws ArtigoExistenteException {
        try{
        boolean premium = Boolean.parseBoolean(c[4]);
        String descricao = c[5];
        String marca = c[6];
        double preco_base = Double.parseDouble(c[7]);
        boolean uso = Boolean.parseBoolean(c[8]);
        int estado_uso = Integer.parseInt(c[9]);
        int n_utilizadores = Integer.parseInt(c[10]);
        long transportadora = Long.parseLong(c[11]);
        int tamanho = Integer.parseInt(c[12]);
        boolean atacadores = Boolean.parseBoolean(c[13]);
        String cor = c[14];
        int anoColecao = Integer.parseInt(c[15]);
        
            if (premium == false) {
               if (this.vintage.existe_transpotadoraNormal(transportadora) == true) {
                    if (this.vintage.ColocarvenderSapatilha(id_utilizador, data, descricao, marca, preco_base, uso,
                            estado_uso, n_utilizadores, transportadora, id_utilizador, tamanho, atacadores, cor,
                            anoColecao, premium, 0) == true) {
                            this.menu.imprimir("Venda efetuada com sucesso da sapatilha " + marca + " do utlizador" + id_utilizador);
                    } else {
                            this.menu.imprimir("Venda não efetuada da sapatilha " + marca + " do utlizador" + id_utilizador);
                    }
                }
            } else  if (premium == true) {
                    if (this.vintage.existe_transpotadoraPremium(anoColecao) == true) {
                        double desconto = Double.parseDouble(c[16]);
                        if (this.vintage.ColocarvenderSapatilha(id_utilizador, data, descricao, marca, preco_base, uso,
                                estado_uso, n_utilizadores, transportadora, id_utilizador, tamanho, atacadores, cor,
                                anoColecao, premium, desconto) == true) {
                            this.menu.imprimir("Venda efetuada com sucesso da sapatilha " + marca + " do utlizador " + id_utilizador);
                        } else {
                            this.menu.imprimir("Venda não efetuada da sapatilha " + marca + " do utlizador " + id_utilizador);
                            }
                    }

                }
            }
                catch(Exception e ){
                    this.menu.imprimir("Erro a ler a sapatilha");
                    }
    }

    // novo -> data, utiliazdor, Venda, T-shit, descricao marca preco_base uso
    // estado_uso n_utilizadores transportadora vendedor tamanho padrao
    public void venderTshit(long id_utilizador, LocalDate data, String[] c) {

        try{
        String descricao = c[4];
        String marca = c[5];
        double preco_base = Double.parseDouble(c[6]);
        boolean uso = Boolean.parseBoolean(c[7]);
        int estado_uso = Integer.parseInt(c[8]);
        int n_utilizadores = Integer.parseInt(c[9]);
        long transportadora = Long.parseLong(c[10]);
        Tamanho tamanho = Tamanho.S;
        if (c[11].equals("S")) {
            tamanho = Tamanho.S;
        } else if (c[11].equals("M")) {
            // scanner.close();
            tamanho = Tamanho.M;
        } else if (c[11].equals("L")) {
            // scanner.close();
            tamanho = Tamanho.L;
        } else if (c[11].equals("XL")) {
            // scanner.close();
            tamanho = Tamanho.XL;
        }

        Padrao padrao = Padrao.liso;
        if (c[12].equals("liso")) {
            padrao = Padrao.liso;
        } else if (c[12].equals("riscas")) {
            padrao = Padrao.riscas;

        } else if (c[12].equals("palmeiras")) {
            padrao = Padrao.palmeiras;
        }
        if (this.vintage.existe_transpotadoraNormal(transportadora)) {
            if (this.vintage.ColocarVenderTshit(id_utilizador, data, descricao, marca, preco_base, uso, estado_uso,
                    n_utilizadores, transportadora, tamanho, padrao) == true) {
                this.menu.imprimir("Venda efetuada com sucesso da t-shirt " + marca + " do utlizador " + id_utilizador);
            } else {
                this.menu.imprimir("Venda não efetuada da t-shirt " + marca + " do utlizador " + id_utilizador);
            }
        }
    }
    catch(Exception e ){
        this.menu.imprimir("Erro a ler a t-shirt");
        }

    }

    // String descricao, String marca, double preco, boolean uso, int estado, int
    // n_utilizadores, long transportadora, int dimensao, String material, int ano,
    // Boolean premium, float valorizacao
    public void venderMala(long id_utilizador, LocalDate data, String[] c) {
        try{
        String descricao = c[4];
        String marca = c[5];
        double preco_base = Double.parseDouble(c[6]);
        boolean uso = Boolean.parseBoolean(c[7]);
        int estado_uso = Integer.parseInt(c[8]);
        int n_utilizadores = Integer.parseInt(c[9]);
        long transportadora = Long.parseLong(c[10]);
        int dimensao = Integer.parseInt(c[11]);
        String material = c[12];
        int ano = Integer.parseInt(c[13]);
        boolean premium = Boolean.parseBoolean(c[14]);

        if (premium == true) {
            float valorizacao = Float.parseFloat(c[15]);
            if (this.vintage.existe_transpotadoraPremium(transportadora)) {
                if (this.vintage.ColocarVenderMala(id_utilizador, data, descricao, marca, preco_base, uso, estado_uso,
                        n_utilizadores, transportadora, dimensao, material, ano, premium, valorizacao) == true) {

                    this.menu.imprimir("Venda efetuada com sucesso da mala " + marca + " do utlizador " + id_utilizador);
                } else {
                    this.menu.imprimir("Venda não efetuada da mala " + marca + " do utlizador " + id_utilizador);
                }
            }
        } else if (premium == false) {
            if (this.vintage.existe_transpotadoraNormal(transportadora)) {
                if (this.vintage.ColocarVenderMala(id_utilizador, data, descricao, marca, preco_base, uso, estado_uso,
                        n_utilizadores, transportadora, dimensao, material, ano, premium, 0) == true) {
                    this.menu.imprimir("Venda efetuada com sucesso da mala " + marca + " do utlizador " + id_utilizador);
                } else {
                    this.menu.imprimir("Venda não efetuada da mala " + marca + " do utlizador " + id_utilizador);
                }
            }
        }
    }
    catch(Exception e ){
        this.menu.imprimir("Erro a ler a mala");
        }


    }
}
