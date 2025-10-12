package TP1;

import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

public class Controlador implements Serializable{
    private final Input in;
    private final ControladorArtigo ca;
    private final ControladorUtilizador cu;
    private final ControladorTransportadoras ct;
    private final ControladorEncomenda ce;

    public Controlador(){
        in = new Input();
        ca = new ControladorArtigo();
        cu = new ControladorUtilizador();
        ct = new ControladorTransportadoras();
        ce = new ControladorEncomenda();
    }


    
    private void interpretadorEstatisticas (Vintage v, Apresentacao a){
        int comando;
        boolean b = true;

        while(b){
            a.printMenuEstatisticas(); 
            comando = in.lerInt(a,"Escolha uma das opções:",0,5);

            switch(comando){
                case 1:
                    a.printMessage("--------------------------");
                    int opcao = in.lerInt(a, "1 | Desde sempre\n2 | Intervalo de Tempo", 1, 2);
                    a.printMessage("--------------------------");
                    if (opcao == 1) {
                        String vendedor = v.calculaMaiorFaturacaoSempre();
                        double valor = v.getMaiorFaturacaoSempre(vendedor);
                        a.printMessage("O Vendedor que mais faturou desde sempre foi: " + vendedor + " num total de: " + valor);
                    }
                    if (opcao == 2){
                        LocalDate before = in.lerData(a, "Data início (dd-mm-aaaa): ");
                        LocalDate after = in.lerData(a, "Data fim (dd-mm-aaaa): ");
                        String vendedor = v.calculaMaiorFauracaoIntevalo(before, after);
                        double valor = v.getMaiorFaturacaoIntervalo(before, after, vendedor);
                        a.printMessage("O Vendedor que mais faturou entre" + before + " e " + after + " foi: " + vendedor + " num total de: " + valor);
                    }
                    break;
                case 2:
                    String transportadora = v.calculaMaiorVolFaturacao();
                    double valor = v.getMaiorVolFaturacao(transportadora);
                    a.printMessage("A transportadora com maior volume de faturação é: " + transportadora + " num total de: " + valor);
                    break;
                case 3:
                    String email = in.lerString(a, "Email do vendedor: ");
                    List<Artigo> vendas = v.listarVendas(email);
                    if (vendas != null){
                        a.printArtigos(vendas, v.getDataPrograma());
                    }
                    else {
                        a.printMessage("Email não existe!");
                    }
                    break;
                case 4:
                    a.printMessage("--------------------------");
                    int opt = in.lerInt(a, "1 | Listar Vendedores\n2 | Listar Compradores", 0, 2);
                    int dias = in.lerInt(a,"Indique os dias que quer recuar: ", 0, 1000);
                    a.printMessage(".-------------------------");
                    LocalDate dataInicial = v.getDataPrograma().minusDays(dias);
                    if (opt == 1) {
                        a.printTop10(v.top10Vendedores(dataInicial));
                    }
                    if (opt == 2){
                        a.printTop10(v.top10Compradores(dataInicial));
                    }
                    break;
                case 5:
                    double total = v.totalDinheiroVintage();
                    a.printMessage("Dinheiro ganho pela Vintage: " + total);
                    break;
                case 0:
                    b = false;
                    break;
                default:
                    a.printMessage("Erro Comando Inválido");
                    break;
            }
        }
    }
    





    private void interpretadorConsultas (Vintage v, Apresentacao a){
        int comando;
        boolean b = true;

        while(b){
            a.printMenuConsultas();
            comando = in.lerInt(a,"Escolha uma das opções:",0,5);

            switch(comando){
                 case 1: // Ver Produtos que está a Vender       
                    a.printArtigos(v.getUtilizadores().get(v.getSessaoAtual()).getPorVender(), v.getDataPrograma());
                    break;
                case 2: // Ver os Produtos que já Comprou
                    a.printArtigos(v.getUtilizadores().get(v.getSessaoAtual()).getCompras(), v.getDataPrograma());
                    break;
                case 3: // Ver os Produtos que já Vendeu
                    a.printArtigos(v.getUtilizadores().get(v.getSessaoAtual()).getVendas(), v.getDataPrograma());
                    break;
                case 4: // Ver transportadoras disponíveis
                    a.printTransportadoras(v.getTransportadoras());
                    break;
                case 5:
                    a.printEncomendas(v.getEncomendasSessaoAtual());
                case 0:
                    b = false;
                    break;
                default:
                    a.printMessage("Erro Comando Inválido!");
            }
        }

    }










    public void interpretador (Vintage v, Apresentacao a) throws ClassNotFoundException, IOException{
        int comando;
        boolean b = true;

        Estados e = new Estados();
        Scanner s = new Scanner(System.in);
        a.welcome();
        s.nextLine();

        while(b){
            if(v.getSessaoAtual() == null){
                
                a.printMenuInicial();
                comando = in.lerInt(a,"Escolhe uma das opcões: ",0,6);
                
                switch(comando){
                    case 1: // Login / Registar
                        a.printMenuLogin();
                        String sessaoAtual = cu.interpretador(v, a);
                        v.SetSessaoAtual(sessaoAtual);
                        break;
                    case 2: // Gravar para um Ficheiro
                        String ficheiroG = in.lerString(a, "Nome do ficheiro que pretende guardar: ");
                        e.dadosGuardar(ficheiroG + ".dat",v);
                        a.printMessage("Dados Guardados com Sucesso em " + ficheiroG + ".dat");
                        break;
                    case 3: // Carregar um Ficheiro
                        String ficheiroA = in.lerString(a, "Nome do ficheiro que prentede carregar: ");
                        v = e.dadoscarregar(ficheiroA + ".dat");
                        a.printMessage("Dados Carregados com Sucesso em " + ficheiroA + ".dat");
                        break;
                    case 4: // Criar Transportadora
                        a.printMessage("----------------------------");
                        a.printMessage("1 | Criar Transportadora\n2 | Editar Transportadora\n0 | Voltar Atrás");
                        a.printMessage("----------------------------");
                        int option = in.lerInt(a, "Escolha uma das opções: ", 0, 2);
                        if (option == 1){ct.interpretador(v,a);break;}
                        if (option == 2){ct.alterarTransportadora(v,a);break;}
                        break;
                    case 5: // Avançar no Tempo
                        Input in = new Input();
                        a.printMessage("Data do Programa: " + v.getDataPrograma());
                        int dias= in.lerInt(a, "Selecione quantos dias pretende avançar: ",0,1000);
                        v.setDataPrograma(v.getDataPrograma().plusDays(dias));
                        v.avancarTempo();
                        a.printMessage("Data do Programa Atualizada: " + v.getDataPrograma());
                        break;
                    case 6: // Estatísticas
                        this.interpretadorEstatisticas(v, a);
                        break;
                    case 0: // Sair do programa
                        b = false;
                        break;
                    default:
                        a.printMessage("Erro! Comando Inválido.");
                        break;
                }
            }else{
                a.printMainMenuLogOut();
                comando = in.lerInt(a,"Escolhe uma das opções: ",0,5);
                switch(comando){
                    case 1: // Dar logout
                        v.SetSessaoAtual(null);
                        a.printMessage("Logout feito com Sucesso!");
                        break;
                    case 2: // Consultar os Produtos
                        interpretadorConsultas(v,a);
                        break;
                    case 3: // Colocar Produto para Vender
                        ca.interpretador(a,v);
                        break;
                    case 4: // Fazer encomenda
                        ce.interpretador(a,v);
                        break;
                    case 5: // Devolver encomenda
                        a.printMessage("--------------------------");
                        a.printMessage("1 | Devolver Encomenda\n2 | Adiar Prazo Limite\n0 | Voltar Atrás");
                        a.printMessage("--------------------------");
                        int opt = in.lerInt(a,"Escolha uma das opções", 0, 2);
                        if (opt == 1){ce.interpretadorDevolver(a,v);break;}
                        if (opt == 2){ce.interpretadorAdiar(a, v);break;}
                        break;
                    case 0: // Sair do programa
                        b = false;
                        break;
                    default:
                        a.printMessage("Erro! Comando Inválido.");
                        break;


                }
            }
        }

        a.printSair();
        s.close();
    }

    
}
