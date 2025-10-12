package Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import Model.Model;
import Model.SystemDate;
import Queries.*;
import Errors.*;

public class Controller {
    private Model m;

    public Controller(Model m) {
        this.m = m;
    }

    public void getAndSetCounter() {
        this.m.getAndSetCounter();
    }

    // Time Methods
    public LocalDateTime obterData() {
        return this.m.getCurrentDate();
    }

    public void passarTempo(int minutos) {
        this.m.passarTempo(minutos);
    }

    public void printData() {
        this.m.printDate();
    }

    // Save and Load
    public void load() throws Exception, ClassNotFoundException {
        this.m = Model.load("data.ser");
        SystemDate.load("date.ser");
    }

    public void save() throws Exception {
        this.m.save("data.ser");
        SystemDate.save("date.ser");
    }

    // Utilizadores

    public String listarUtilizadores() {
        return this.m.listarUtilizadores();
    }

    public boolean utilizadoresVazios() {
        return this.m.utilizadoresVazios();
    }

    public void registarUtilizador(String nome, String morada, String email, int freqCard, int escolha)
            throws UtilizadorJaExisteException {
        switch (escolha) {
            case 0:
                this.m.registarUtilizadorOcasional(nome, morada, email, freqCard);
                break;
            case 1:
                this.m.registarUtilizadorAmador(nome, morada, email, freqCard);
                break;
            case 2:
                this.m.registarUtilizadorProfissional(nome, morada, email, freqCard);
                break;
            default:
                break;
        }
    }

    public void removerUtilizador(int nUtilizador) {
        this.m.removerUtilizador(nUtilizador);
    }

    public void atualizarUtilizador(int nUtilizador, int opcao, String value) {

        switch (opcao) {
            case 1:
                this.m.atualizarUtilizadorNome(nUtilizador, value);
                break;
            case 2:
                this.m.atualizarUtilizadorMorada(nUtilizador, value);
                break;
            case 3:
                this.m.atualizarUtilizadorEmail(nUtilizador, value);
                break;
            case 4:
                this.m.atualizarUtilizadorFreqCard(nUtilizador, value);
                break;
            case 5:
                this.m.atualizarUtilizadorUser(nUtilizador, value);
                break;
            default:
                break;
        }
    }

    // Atividades

    public String listarAtividadesAtivas(int nUtilizador) {
        return this.m.listarAtividadesAtivasUtilizador(nUtilizador);
    }

    public String listarAtividadesTerminadas(int nUtilizador) {
        return this.m.listarAtividadesTerminadas(nUtilizador);
    }

    public void eliminarAtividade(int nUtilizador, int nAtividade) {
        this.m.eliminarAtividade(nUtilizador, nAtividade);
    }

    public int perguntaDaAtividade(int nUtilizador, int Atividade) {
        return this.m.perguntaDaAtividade(nUtilizador, Atividade);
    }

    public String executarAtividade(int nUtilizador, int nAtividade, LocalDateTime dateInicio, LocalDateTime dateFim, int n, int n2) throws UtilizadorNaoExisteException {
        return this.m.executarAtividade(nUtilizador, nAtividade, dateInicio, dateFim, n, n2);
    }

    public void registarAtividade(int nUtilizador, int escolha, int escolha2) {
        if (escolha < 1 || escolha > 4) {
            System.out.println("Escolha inválida");
            return;
        }
        switch (escolha) {
            case 1:
                switch (escolha2) {
                    case 1:
                        this.m.registarAtividadeRemo(nUtilizador);
                        break;
                    case 2:
                        this.m.registarAtividadeCorridaNaPistaDeAtletismo(nUtilizador);
                        break;
                    case 3:
                        this.m.registarAtividadePatinagem(nUtilizador);
                        break;
                    case 4:
                        this.m.registarAtividadeNatacao(nUtilizador);
                        break;
                    default:
                        System.out.println("Escolha inválida");
                }
                break;
            case 2:
                switch (escolha2) {
                    case 1:
                        this.m.registarAtividadeCorridaNaEstrada(nUtilizador);
                        break;
                    case 2:
                        this.m.registarAtividadeTrailNoMonte(nUtilizador);
                        break;
                    case 3:
                        this.m.registarAtividadeBicicletaDeEstrada(nUtilizador);
                        break;
                    case 4:
                        this.m.registarAtividadeBicicletaDeMontanha(nUtilizador);
                        break;
                    default:
                        System.out.println("Escolha inválida");
                        break;
                }
                break;
            case 3:
                switch (escolha2) {
                    case 1:
                        this.m.registarAtividadeAbdominais(nUtilizador);
                        break;
                    case 2:
                        this.m.registarAtividadeAlongamentos(nUtilizador);
                        break;
                    case 3:
                        this.m.registarAtividadeFlexoes(nUtilizador);
                        break;
                    case 4:
                        this.m.registarAtividadePrancha(nUtilizador);
                        break;
                    default:
                        System.out.println("Escolha inválida");
                        break;
                }
                break;
            case 4:
                switch (escolha2) {
                    case 1:
                        this.m.registarAtividadeLevantamentoDePesos(nUtilizador);
                        break;
                    case 2:
                        this.m.registarAtividadeExtensaoDePernas(nUtilizador);
                        break;
                    case 3:
                        this.m.registarAtividadeSupino(nUtilizador);
                        break;
                    case 4:
                        this.m.registarAtividadeDeadLift(nUtilizador);
                        break;
                    default:
                        System.out.println("Escolha inválida");
                        break;
                }
                break;
            default:
                System.out.println("Escolha inválida");
                break;
        }
    }

    // Planos de Treino

    public String listarPlanoTreino(int nUtilizador) {
        return this.m.listarPlanoTreino(nUtilizador);
    }

    public void registarPlanoDeTreino(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, HashMap<Integer, List<Integer>> escolhas) {
        this.m.registarPlanoDeTreino(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, escolhas);
    }

    public boolean planosTreinoNull(int nUtilizador) {
        return this.m.planosTreinoNull(nUtilizador);
    }

    public void removerPlanoTreino(int nUtilizador) {
        this.m.removerPlanoTreino(nUtilizador);
    }

    public int perguntaDaAtividadeTreino(int nUtilizador, int diaDaAtividade, int nAtividade) {
        return this.m.perguntaDaAtividadeTreino(nUtilizador, diaDaAtividade, nAtividade);
    }

    public String executarAtividadePlanoTreino(int nUtilizador, int diaDaAtividade, int nAtividade, LocalDateTime dateInicio,
            LocalDateTime dateFim, int n, int n2) {
        return this.m.executarAtividadePlanoTreino(nUtilizador, diaDaAtividade, nAtividade, dateInicio, dateFim, n, n2);
    }

    public boolean verificarDia(int nUtilizador, int diaDaAtividade) {
        return this.m.verificarDia(nUtilizador, diaDaAtividade);
    }

    public void resetPlanoAtividade(int nUtilizador) {
        this.m.resetPlanoAtividade(nUtilizador);
    }

    public String listarPlanoRealizado(int nUtilizador) {
        return this.m.listarPlanoRealizado(nUtilizador);
    }

    public void registarPlanoDeTreinoPorTipo(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, int tipo) {
        this.m.registarPlanoDeTreinoPorTipo(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, tipo);
    }

    // Queries

    public String executeQuery(int escolha, int input, LocalDateTime dateInicio, LocalDateTime dateFim) {
        String resultado = "";
        Querier querier;
        switch (escolha) {
            case 1:
                querier = new MaisCaloriasSempre(m.getUserMap(), null, null);
                resultado = (String) querier.execute();
                break;
            case 2:
                querier = new MaisCaloriasSempre(m.getUserMap(), dateInicio, dateFim);
                resultado = (String) querier.execute();
                break;
            case 3:
                querier = new MaiorNumeroAtividades(m.getUserMap(), null, null);
                resultado = (String) querier.execute();
                break;
            case 4:
                querier = new MaiorNumeroAtividades(m.getUserMap(), dateInicio, dateFim);
                resultado = (String) querier.execute();
                break;
            case 5:
                querier = new AtividadeMaisRealizada(m.getUserMap(), null, null);
                resultado = (String) querier.execute();
                break;
            case 6:
                querier = new AtividadeMaisRealizada(m.getUserMap(), dateInicio, dateFim);
                resultado = (String) querier.execute();
                break;
            case 7:
                querier = new MaiorDistancia(m.getUserMap(), input, null, null);
                resultado = String.valueOf(querier.execute());
                break;
            case 8:
                querier = new MaiorDistancia(m.getUserMap(), input, dateInicio, dateFim);
                resultado = String.valueOf(querier.execute());
                break;
            case 9:
                querier = new MaiorAltimetria(m.getUserMap(), input, null, null);
                resultado = (String) querier.execute();
                break;
            case 10:
                querier = new MaiorAltimetria(m.getUserMap(), input, dateInicio, dateFim);
                resultado = (String) querier.execute();
                break;
            case 11:
                querier = new PlanoMaisExigente(m.getUserMap(), null, null);
                resultado = (String) querier.execute();
                break;
            case 12:
                querier = new PlanoMaisExigente(m.getUserMap(), dateInicio, dateFim);
                resultado = (String) querier.execute();
                break;
        }
        return resultado;
    }

}
