package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;
import Model.Utilizadores.*;
import Model.Atividades.*;

public class Model implements Serializable {

    private UtilizadorManager utilizadorManager;

    public Model() {
        this.utilizadorManager = new UtilizadorManager();
        SystemDate.setDate(LocalDateTime.now());
    }

    public void getAndSetCounter() {
        int counter = 1;
        for (Utilizador u : utilizadorManager.getUserMapCopy().values()) {
            if (u.getNUtilizador() >= counter) {
                counter = u.getNUtilizador() + 1;
            }
        }
        Utilizador.setnUtilizadorCounter(counter);
    }

    public LocalDateTime getCurrentDate() {
        return SystemDate.getDate();
    }

    public void setDate(LocalDateTime date) {
        SystemDate.setDate(date);
    }

    public LocalDateTime getDate() {
        return SystemDate.getDate();
    }

    public void printDate() {
        LocalDateTime date = SystemDate.getDate();
        System.out.println("Data: " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear()
                + "\nHora: " + date.getHour() + ":" + date.getMinute() + "\n");
    }

    public void passarTempo(int minutos) {
        LocalDateTime newDate = SystemDate.getDate().plusMinutes(minutos);
        SystemDate.setDate(newDate);
    }

    // Load method
    public static Model load(String fileName) throws IOException, ClassNotFoundException {
        String filePath = "data/" + fileName;
        try (FileInputStream fs = new FileInputStream(filePath);
                ObjectInputStream os = new ObjectInputStream(fs)) {
            return (Model) os.readObject();
        }
    }

    // Save method
    public void save(String fileName) throws IOException {
        String filePath = "data/" + fileName;
        try (FileOutputStream fs = new FileOutputStream(filePath);
                ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(this);
        }
    }

    // for querier

    public Map<Integer, Utilizador> getUserMap() {
        return this.utilizadorManager.getUserMapCopy();
    }

    // Utilizadores

    private boolean reverCredenciais(String email) {
        Utilizador u = this.utilizadorManager.encontraUserEmail(email);
        return u == null;
    }

    public void registarUtilizadorOcasional(String nome, String morada, String email, int freqCard)
            throws UtilizadorJaExisteException {
        if (reverCredenciais(email) != true) {
            throw new UtilizadorJaExisteException();
        }

        UtilizadorOcasional u0 = new UtilizadorOcasional(nome, morada, email, freqCard);
        this.utilizadorManager.addUtilizador(u0);
    }

    public void registarUtilizadorAmador(String nome, String morada, String email, int freqCard)
            throws UtilizadorJaExisteException {
        if (reverCredenciais(email) != true) {
            throw new UtilizadorJaExisteException();
        }

        UtilizadorAmador u1 = new UtilizadorAmador(nome, morada, email, freqCard);
        this.utilizadorManager.addUtilizador(u1);
    }

    public void registarUtilizadorProfissional(String nome, String morada, String email, int freqCard)
            throws UtilizadorJaExisteException {
        if (reverCredenciais(email) != true) {
            throw new UtilizadorJaExisteException();
        }

        UtilizadorProfissional u2 = new UtilizadorProfissional(nome, morada, email, freqCard);
        this.utilizadorManager.addUtilizador(u2);
    }

    public String listarUtilizadores() {
        try {
            if (utilizadoresVazios()) {
                throw new NullPointerException("Não existem utilizadores registados.");
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        String resposta = utilizadorManager.listarUtilizadores();
        return resposta;
    }

    public void removerUtilizador(int nUtilizador) {
        try {
            if (utilizadoresVazios()) {
                throw new NullPointerException("Não existem utilizadores registados.");
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        utilizadorManager.removerUtilizador(nUtilizador);
    }

    public boolean utilizadoresVazios() {
        return utilizadorManager.utilizadoresVazios();
    }

    public void atualizarUtilizadorNome(int nUtilizador, String value) {
        this.utilizadorManager.atualizarNome(nUtilizador, value);
    }

    public void atualizarUtilizadorMorada(int nUtilizador, String value) {
        this.utilizadorManager.atualizarMorada(nUtilizador, value);
    }

    public void atualizarUtilizadorEmail(int nUtilizador, String value) {
        this.utilizadorManager.atualizarEmail(nUtilizador, value);
    }

    public void atualizarUtilizadorFreqCard(int nUtilizador, String value) {
        this.utilizadorManager.atualizarFreqCard(nUtilizador, Integer.parseInt(value));
    }

    public void atualizarUtilizadorUser(int nUtilizador, String value) {
        this.utilizadorManager.atualizarUser(nUtilizador, Integer.parseInt(value));
    }

    // Atividades

    public String listarAtividadesAtivasUtilizador(int nUtilizador) {
        return this.utilizadorManager.listarAtividadesAtivas(nUtilizador);
    }

    public String listarAtividadesTerminadas(int nUtilizador) {
        return this.utilizadorManager.listarAtividadesTerminadas(nUtilizador);
    }

    public void eliminarAtividade(int nUtilizador, int nAtividade) {
        this.utilizadorManager.eliminarAtividade(nUtilizador, nAtividade);
    }

    public int perguntaDaAtividade(int nUtilizador, int nAtividade) {
        return this.utilizadorManager.perguntaDaAtividade(nUtilizador, nAtividade);
    }

    public String executarAtividade(int nUtilizador, int nAtividade, LocalDateTime dateInicio, LocalDateTime dateFim,
            int n, int n2) throws UtilizadorNaoExisteException {
        return this.utilizadorManager.executarAtividade(nUtilizador, nAtividade, dateInicio, dateFim, n, n2);
    }

    public void registarAtividadeRemo(int nUtilizador) {
        Remo ativRemo = new Remo();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativRemo);
    }

    public void registarAtividadeCorridaNaPistaDeAtletismo(int nUtilizador) {
        CorridaAtletismo ativCorridaNaPistaDeAtletismo = new CorridaAtletismo();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativCorridaNaPistaDeAtletismo);
    }

    public void registarAtividadePatinagem(int nUtilizador) {
        Patinagem ativPatinagem = new Patinagem();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativPatinagem);
    }

    public void registarAtividadeNatacao(int nUtilizador) {
        Natacao ativNatacao = new Natacao();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativNatacao);
    }

    public void registarAtividadeCorridaNaEstrada(int nUtilizador) {
        CorridaEstrada ativCorridaNaEstrada = new CorridaEstrada();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativCorridaNaEstrada);
    }

    public void registarAtividadeTrailNoMonte(int nUtilizador) {
        TrailNoMonte ativTrailNoMonte = new TrailNoMonte();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativTrailNoMonte);
    }

    public void registarAtividadeBicicletaDeEstrada(int nUtilizador) {
        BicicletaDeEstrada ativBicicletaDeEstrada = new BicicletaDeEstrada();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativBicicletaDeEstrada);
    }

    public void registarAtividadeBicicletaDeMontanha(int nUtilizador) {
        BicicletaDeMontanha ativBicicletaDeMontanha = new BicicletaDeMontanha();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativBicicletaDeMontanha);
    }

    public void registarAtividadeAbdominais(int nUtilizador) {
        Abdominais ativAbdominais = new Abdominais();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativAbdominais);
    }

    public void registarAtividadeAlongamentos(int nUtilizador) {
        Alongamentos ativAlongamentos = new Alongamentos();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativAlongamentos);
    }

    public void registarAtividadeFlexoes(int nUtilizador) {
        Flexoes ativFlexoes = new Flexoes();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativFlexoes);
    }

    public void registarAtividadePrancha(int nUtilizador) {
        Prancha ativPrancha = new Prancha();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativPrancha);
    }

    public void registarAtividadeLevantamentoDePesos(int nUtilizador) {
        LevantamentoDePesos ativLevantamentoDePesos = new LevantamentoDePesos();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativLevantamentoDePesos);
    }

    public void registarAtividadeExtensaoDePernas(int nUtilizador) {
        ExtensaoDePernas ativExtensaoDePernas = new ExtensaoDePernas();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativExtensaoDePernas);
    }

    public void registarAtividadeSupino(int nUtilizador) {
        Supino ativSupino = new Supino();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativSupino);
    }

    public void registarAtividadeDeadLift(int nUtilizador) {
        Deadlift ativDeadlift = new Deadlift();
        this.utilizadorManager.adicionarAtividade(nUtilizador, ativDeadlift);
    }

    // Planos de Treino

    public String listarPlanoTreino(int nUtilizador) {
        return this.utilizadorManager.listarPlanoTreino(nUtilizador);
    }

    public void registarPlanoDeTreino(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, HashMap<Integer, List<Integer>> escolhas) {
        this.utilizadorManager.registarPlanoDeTreino(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, escolhas);
    }

    public boolean planosTreinoNull(int nUtilizador) {
        return this.utilizadorManager.planosTreinoNull(nUtilizador);
    }

    public void removerPlanoTreino(int nUtilizador) {
        this.utilizadorManager.removerPlanoTreino(nUtilizador);
    }

    public int perguntaDaAtividadeTreino(int nUtilizador, int diaDaAtividade, int nAtividade) {
        return this.utilizadorManager.perguntaDaAtividadeTreino(nUtilizador, diaDaAtividade, nAtividade);
    }

    public String executarAtividadePlanoTreino(int nUtilizador, int diaDaAtividade, int nAtividade, LocalDateTime dateInicio,
            LocalDateTime dateFim, int n, int n2) {
        return this.utilizadorManager.executarAtividadePlanoTreino(nUtilizador, diaDaAtividade, nAtividade, dateInicio, dateFim, n, n2);
    }

    public boolean verificarDia(int nUtilizador, int diaDaAtividade) {
        return this.utilizadorManager.verificarDia(nUtilizador, diaDaAtividade);
    }

    public void resetPlanoAtividade(int nUtilizador) {
        this.utilizadorManager.resetPlanoAtividade(nUtilizador);
    }

    public String listarPlanoRealizado(int nUtilizador) {
        return this.utilizadorManager.listarPlanoRealizado(nUtilizador);
    }

    public void registarPlanoDeTreinoPorTipo(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, int tipo) {
        this.utilizadorManager.registarPlanoDeTreinoPorTipo(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, tipo);
    }

}
