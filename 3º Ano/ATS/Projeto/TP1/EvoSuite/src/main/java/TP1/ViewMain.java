package TP1;

import java.io.Serializable;

public class ViewMain implements Serializable {
    private final Output out;

    public ViewMain() {
        out = new Output();
    }

    public void welcome() {
        System.out.println("    __      _______ _   _ _______       _____ ______      ");
        System.out.println("    \\ \\    / /_   _| \\ | |__   __|/\\   / ____|  ____| ");
        System.out.println("     \\ \\  / /  | | |  \\| |  | |  /  \\ | |  __| |__    ");
        System.out.println("      \\ \\/ /   | | | . ` |  | | / /\\ \\| | |_ |  __|   ");
        System.out.println("       \\  /   _| |_| |\\  |  | |/ ____ \\ |__| | |____   ");
        System.out.println("        \\/   |_____|_| \\_|  |_/_/    \\_\\_____|______| ");
        System.out.println("                                                          ");
        System.out.println("                                                          ");
        System.out.println("               Bem vindo à aplicação Vintage!             "); 
        System.out.println("                                                          ");
        System.out.println("          Pressione qualquer tecla para continuar         ");
        
    }

    public void printMenuInicial() {
        out.printMenus((new String[]{"Login/Registar", "Gravar para um Ficheiro", "Carregar de um Ficheiro","Transportadoras","Avançar no Tempo","Estatísticas"}),"      MENU PRINCIPAL",0);
    }

    public void printMainMenuLogOut() {
        out.printMenus((new String[]{"Logout", "Consultas", "Vender Artigo","Comprar Artigo","Encomendas"}),"   MENU PRINCIPAL  ",0);
    }

    public void printMenuArtigo(){
        out.printMenus((new String[]{"Vender T-Shirt", "Vender Mala", "Vender Sapatilha"}),"  VENDER ARTIGOS  ",1);

    }

    

    public void printMenuConsultas(){
        out.printMenus((new String[]{"Produtos a Vender", "Produtos Comprados", "Produtos Vendidos","Transportadoras Disponíveis","Encomendas Realizadas"}),"      CONSULTAS",1);
    }

    public void printMenuEstatisticas(){
        out.printMenus((new String[]{"Vendedor que mais facturou num período ou desde sempre", 
                                    "Transportador com maior volume de facturação", 
                                    "Listar as vendas emitidas por um vendedor",
                                    "Top 10 maiores compradores/vendedores do sistema num período",
                                    "Quanto dinheiro ganhou o Vintage"
                                    }),"                          ESTATÍSTICAS",1);
    }



    public void printMenuEncomenda(){
        out.printMenus((new String[] {"Adicionar Produto ao Carrinho", "Remover Produto do Carrinho", "Terminar Encomenda"}), "         NOVA ENCOMENDA", 1);
    }


    public void printErroComandoInvalido(){
        System.out.println("Comando Inválido");
    }

    public void printFicheiroCarregado(String file){
        System.out.println("Ficheiro " + file + " carregado");
    }
    
    public void printFicheiroGuardado(String file){
        System.out.println("Ficheiro " + file + " guardado");
    }

    public void printSair() {
        System.out.println("A Sair do Programa");
    }
}