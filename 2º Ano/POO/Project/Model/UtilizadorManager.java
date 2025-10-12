package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Errors.UtilizadorNaoExisteException;
import Model.Atividades.*;
import Model.Utilizadores.*;

public class UtilizadorManager implements Serializable {
    private Map<Integer, Utilizador> userMap = new HashMap<>();

    public Map<Integer, Utilizador> getUserMapCopy() {
        Map<Integer, Utilizador> copy = new HashMap<>();
        for (Integer user_id : userMap.keySet()) {
            copy.put(user_id, userMap.get(user_id).clone());
        }
        return copy;
    }

    public Utilizador encontraUserEmail(String email) {
        for (Integer user_id : userMap.keySet()) {
            Utilizador temp = this.userMap.get(user_id);
            if (temp.getEmail().equals(email))
                return temp;
        }
        return null;
    }

    public void addUtilizador(Utilizador utilizador) {
        if (utilizador == null) {
            throw new NullPointerException("Utilizador is null");
        }
        this.userMap.put(utilizador.getNUtilizador(), utilizador.clone());
    }

    public String listarUtilizadores() {
        StringBuilder sb = new StringBuilder();
        for (Integer user_id : userMap.keySet()) {
            sb.append(userMap.get(user_id).clone().toString() + "\n\n");
        }
        return sb.toString();
    }

    public String pesquisarUtilizador(int nUtilizador) {
        try {
            if (userMap.containsKey(nUtilizador)) {
                return userMap.get(nUtilizador).toString();
            } else {
                throw new UtilizadorNaoExisteException();
            }
        } catch (UtilizadorNaoExisteException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public void removerUtilizador(int nUtilizador) {
        try {
            if (userMap.containsKey(nUtilizador)) {
                userMap.remove(nUtilizador);
            } else {
                throw new UtilizadorNaoExisteException();
            }
        } catch (UtilizadorNaoExisteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public boolean utilizadoresVazios() {
        return userMap.isEmpty();
    }

    public void atualizarNome(int nUtilizador, String nome) {
        Utilizador temp = this.userMap.get(nUtilizador);
        temp.setNome(nome);
    }

    public void atualizarEmail(int nUtilizador, String email) {
        Utilizador temp = this.userMap.get(nUtilizador);
        temp.setEmail(email);
    }

    public void atualizarMorada(int nUtilizador, String morada) {
        Utilizador temp = this.userMap.get(nUtilizador);
        temp.setMorada(morada);
    }

    public void atualizarFreqCard(int nUtilizador, int freqCardiaca) {
        Utilizador temp = this.userMap.get(nUtilizador);
        temp.setFreqCard(freqCardiaca);
    }

    public void atualizarUser(int nUtilizador, int val) {
        switch (val) {
            case 0:
                Utilizador u = this.userMap.get(nUtilizador);
                UtilizadorOcasional uo = new UtilizadorOcasional(u.getNUtilizador(), u.getNome(), u.getMorada(),
                        u.getEmail(), u.getFreqCard());
                this.userMap.put(nUtilizador, uo.clone());
                break;
            case 1:
                Utilizador u1 = this.userMap.get(nUtilizador);
                UtilizadorAmador ua = new UtilizadorAmador(u1.getNUtilizador(), u1.getNome(), u1.getMorada(),
                        u1.getEmail(), u1.getFreqCard());
                this.userMap.put(nUtilizador, ua.clone());
                break;
            case 2:
                Utilizador u2 = this.userMap.get(nUtilizador);
                UtilizadorProfissional up = new UtilizadorProfissional(u2.getNUtilizador(), u2.getNome(),
                        u2.getMorada(), u2.getEmail(), u2.getFreqCard());
                this.userMap.put(nUtilizador, up.clone());
                break;
        }
    }

    // Para atividades

    public String listarAtividadesAtivas(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        String res = "";
        if (temp != null) {
            List<Atividade> atividades = temp.getAtividades();
            for (Atividade atividade : atividades) {
                res += "\nAtividade número " + (atividades.indexOf(atividade) + 1) + ": " + atividade.toString() + "\n";
            }
            return res;
        } else {
            return "Não existem atividades ativas.";
        }
    }

    public String listarAtividadesTerminadas(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        String res = "";
        if (temp != null) {
            List<Atividade> atividadesEfet = temp.getAtividadesEfetuadas();
            for (Atividade atividade : atividadesEfet) {
                res += "\nAtividade número " + (atividadesEfet.indexOf(atividade) + 1) + ": " + atividade.toString();
            }
            return res;
        } else {
            return "Não existem atividades terminadas.";
        }
    }

    public void eliminarAtividade(int nUtilizador, int index) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            // remove from atividades
            List<Atividade> atividades = temp.getAtividades();
            atividades.remove(index - 1);
            temp.setAtividades(atividades);
        }
    }

    public String executarAtividade(int nUtilizador, int index, LocalDateTime datainicio, LocalDateTime datafim,
            int n, int n2) {
        StringBuilder sb = new StringBuilder();
        Utilizador temp = this.userMap.get(nUtilizador).clone();
        try {
            if (temp != null) {
                List<Atividade> atividades = temp.getAtividades();
                List<Atividade> atividadesEfet = temp.getAtividadesEfetuadas();
                Atividade toAdd = null;
                if (index > atividades.size())
                    throw new NullPointerException("Atividade não existe.");
                else {
                    toAdd = atividades.get(index - 1).clone();
                    toAdd.setDataInicio(datainicio);
                    toAdd.setDataFim(datafim);
                    toAdd.calculaTempoDispendido();
                    if (toAdd instanceof AtividadeDist) {
                        ((AtividadeDist) toAdd).setDistancia(n);
                    } else if (toAdd instanceof AtividadeDistAlt) {
                        ((AtividadeDistAlt) toAdd).setDistancia(n);
                        ((AtividadeDistAlt) toAdd).setAltura(n2);
                    } else if (toAdd instanceof AtividadeRep) {
                        ((AtividadeRep) toAdd).setRepeticoes(n);
                    } else if (toAdd instanceof AtividadeRepPesos) {
                        ((AtividadeRepPesos) toAdd).setRepeticoes(n);
                        ((AtividadeRepPesos) toAdd).setPeso(n2);
                    }
                }
                for (int i = 0; i < atividadesEfet.size(); i++) {
                    if (atividadesEfet.get(i) instanceof Hard) {
                        // check if there's any atividade efetuada the day before or after
                        if (atividadesEfet.get(i).getDatainicio().isAfter(datainicio.minusDays(1))) {
                            return "Não pode efetuar a atividade " + toAdd.getClass().getSimpleName() + " porque outra atividade Hard foi efetuada há um dia ou menos.\n";
                        }
                    }
                }
                // add to atividadesEfetuadas
                atividadesEfet.add(toAdd);
                temp.setAtividadesEfetuadas(atividadesEfet);
                sb.append("Atividade efetuada com sucesso. Foram gastas ").append(toAdd.calculaCaloriasGastas(temp)).append(" calorias.\n");
            } else {
                throw new UtilizadorNaoExisteException();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public void adicionarAtividade(int nUtilizador, Atividade atividade) {
        Utilizador temp = this.userMap.get(nUtilizador);
        try {
            if (temp != null) {
                List<Atividade> atividades = temp.getAtividades();
                atividades.add(atividade);
                temp.setAtividades(atividades);
                atividades = temp.getAtividades();
            } else {
                throw new UtilizadorNaoExisteException();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public int perguntaDaAtividade(int nUtilizador, int nAtividade) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            List<Atividade> atividades = temp.getAtividades();
            if (nAtividade > atividades.size())
                return -1;
            else {
                Atividade atividade = atividades.get(nAtividade - 1);
                if (atividade instanceof AtividadeDist) {
                    return 0;
                } else if (atividade instanceof AtividadeDistAlt) {
                    return 1;
                } else if (atividade instanceof AtividadeRep) {
                    return 2;
                } else if (atividade instanceof AtividadeRepPesos) {
                    return 3;
                }
            }
        }
        return -1;
    }

    // para treinos

    public String listarPlanoTreino(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            return temp.getPlanoTreino().toString();
        } else {
            return "Não existe plano de treino para este utilizador.";
        }
    }

    public void registarPlanoDeTreino(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, HashMap<Integer, List<Integer>> escolhas) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            PlanoTreino plano = new PlanoTreino();
            plano.setAtividadeMaxDia(atividadeMaxDia);
            plano.setRecorrenciaSemanal(recorrenciaSemanal);
            plano.setConsumoCalorico(calorias);
            HashMap<Integer, List<Atividade>> mapa = new HashMap<>(); 
            List<Atividade> atividades;
            for (int i = 0; i < escolhas.keySet().size(); i++) {
                Atividade atividade = null;
                atividades = new ArrayList<>();
                for(int j = 0; j < escolhas.get(i).size(); j++) {
                    switch (escolhas.get(i).get(j)) {
                        case 1:
                            atividade = new Remo();
                            break;
                        case 2:
                            atividade = new CorridaAtletismo();
                            break;
                        case 3:
                            atividade = new Patinagem();
                            break;
                        case 4:
                            atividade = new Natacao();
                            break;
                        case 5:
                            atividade = new CorridaEstrada();
                            break;
                        case 6:
                            atividade = new TrailNoMonte();
                            break;
                        case 7:
                            atividade = new BicicletaDeEstrada();
                            break;
                        case 8:
                            atividade = new BicicletaDeMontanha();
                            break;
                        case 9:
                            atividade = new Abdominais();
                            break;
                        case 10:
                            atividade = new Alongamentos();
                            break;
                        case 11:
                            atividade = new Flexoes();
                            break;
                        case 12:
                            atividade = new Prancha();
                            break;
                        case 13:
                            atividade = new LevantamentoDePesos();
                            break;
                        case 14:
                            atividade = new ExtensaoDePernas();
                            break;
                        case 15:
                            atividade = new Supino();
                            break;
                        case 16:
                            atividade = new Deadlift();
                            break;
                    }
                    if (atividade == null) {
                        continue;
                    }
                    if (atividades.contains(atividade)) {
                        System.out.println("A atividade " + atividade.getClass().getSimpleName() + " já foi adicionada ao dia " + i + ". A atividade não foi adicionada.");
                        continue;
                    }
                    if(atividade instanceof Hard && atividades.stream().anyMatch(a -> a instanceof Hard)) {
                        System.out.println("Já existe uma atividade Hard no dia " + i + ". A atividade " + atividade.getClass().getSimpleName() + " não foi adicionada.");
                        continue;
                    }
                    atividades.add(atividade);
                }
                mapa.put(i, atividades);
            }
            plano.setAtividades(mapa);
            temp.setPlanoTreino(plano);
        }
    }

    public boolean planosTreinoNull(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            return temp.getPlanoTreino() == null;
        }
        return true;
    }

    public void removerPlanoTreino(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            temp.setPlanoTreino(null);
        }
    }

    public int perguntaDaAtividadeTreino(int nUtilizador, int diaDaAtividade, int nAtividade) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            PlanoTreino plano = temp.getPlanoTreino();
            if (plano != null) {
                List<Atividade> atividades = plano.getAtividades().get(diaDaAtividade);
                if (nAtividade > atividades.size())
                    return -1;
                else {
                    Atividade atividade = atividades.get(nAtividade);
                    if (atividade instanceof AtividadeDist) {
                        return 0;
                    } else if (atividade instanceof AtividadeDistAlt) {
                        return 1;
                    } else if (atividade instanceof AtividadeRep) {
                        return 2;
                    } else if (atividade instanceof AtividadeRepPesos) {
                        return 3;
                    }
                }
            }
        }
        return -1;
    }

    public String executarAtividadePlanoTreino(int nUtilizador, int diaDaAtividade, int nAtividade,
            LocalDateTime dateInicio, LocalDateTime dateFim, int n, int n2) {
        Utilizador temp = this.userMap.get(nUtilizador);
        String toReturn = "";
        List<Atividade> atividadesEfet = temp.getAtividadesEfetuadas();
        if (temp != null) {
            PlanoTreino plano = temp.getPlanoTreino();
            if (plano != null) {
                List<Atividade> atividades = plano.getAtividades().get(diaDaAtividade);
                Atividade toAdd = atividades.get(nAtividade).clone();
                // verify if there's no atividade efetuada that day or the one before thats hard
                for(int i = 0; i < atividadesEfet.size(); i++) {
                    if (atividadesEfet.get(i) instanceof Hard) {
                        // check if there's any atividade efetuada the day before or after
                        if (atividadesEfet.get(i).getDatainicio().isAfter(dateInicio.minusDays(1))) {
                            toReturn += "Não pode efetuar a atividade " + toAdd.getClass().getSimpleName() + " porque outra atividade Hard foi efetuada há um dia ou menos.\n";
                            return toReturn;
                        }
                    }
                }
                toAdd.setDataInicio(dateInicio);
                toAdd.setDataFim(dateFim);
                toAdd.calculaTempoDispendido();
                toAdd.setEfetuado(1);
                List<Integer> diasRealizados = plano.getDiasRealizados();
                if (!diasRealizados.contains(diaDaAtividade)) {
                    diasRealizados.add(diaDaAtividade+1);
                    plano.setDiasRealizados(diasRealizados);

                }
                if (toAdd instanceof AtividadeDist) {
                    ((AtividadeDist) toAdd).setDistancia(n);
                } else if (toAdd instanceof AtividadeDistAlt) {
                    ((AtividadeDistAlt) toAdd).setDistancia(n);
                    ((AtividadeDistAlt) toAdd).setAltura(n2);
                } else if (toAdd instanceof AtividadeRep) {
                    ((AtividadeRep) toAdd).setRepeticoes(n);
                } else if (toAdd instanceof AtividadeRepPesos) {
                    ((AtividadeRepPesos) toAdd).setRepeticoes(n);
                    ((AtividadeRepPesos) toAdd).setPeso(n2);
                }
                // update atividade inside map
                atividades.set(nAtividade, toAdd);
                // update plano
                HashMap<Integer, List<Atividade>> planoMap = plano.getAtividades();
                planoMap.put(diaDaAtividade, atividades);
                plano.setAtividades(planoMap);
                atividadesEfet.add(toAdd);
                temp.setAtividadesEfetuadas(atividadesEfet);
                List<PlanoTreino> planosEfet = temp.getPlanosEfetuados();
                planosEfet.add(plano);
                temp.setPlanosEfetuados(planosEfet);
                System.out.println(plano.toString());
                toReturn += "Atividade efetuada com sucesso. Foram gastas " + atividades.get(nAtividade).calculaCaloriasGastas(temp) + " calorias.\n";
            }
        }
        return toReturn;
    }

    public Boolean verificarDia(int nUtilizador, int diaDaAtividade) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            PlanoTreino plano = temp.getPlanoTreino();
            if (plano != null) {
                if(plano.getDiasRealizados().size() == plano.getRecorrenciaSemanal()) {
                    plano.setDiasRealizados(new ArrayList<>());
                    return false;
                }
                List<Integer> diasRealizados = plano.getDiasRealizados();
                return diasRealizados.contains(diaDaAtividade);
            }
        }
        return false;
    }

    public void resetPlanoAtividade(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            PlanoTreino plano = temp.getPlanoTreino();
            if (plano != null) {
                plano.setDiasRealizados(new ArrayList<>());
            }
            for (int i = 0; i < plano.getAtividades().size(); i++) {
                List<Atividade> atividades = plano.getAtividades().get(i);
                for (int j = 0; j < atividades.size(); j++) {
                    Atividade atividade = atividades.get(j);
                    atividade.setEfetuado(0);
                    atividades.set(j, atividade);
                }
                plano.getAtividades().put(i, atividades);
            }
        }
    }

    public String listarPlanoRealizado(int nUtilizador) {
        Utilizador temp = this.userMap.get(nUtilizador);
        if (temp != null) {
            List<PlanoTreino> planosEfet = temp.getPlanosEfetuados();
            StringBuilder sb = new StringBuilder();
            for (PlanoTreino plano : planosEfet) {
                sb.append(plano.toString());
            }
            return sb.toString();
        } else {
            return "Não existem planos de treino efetuados.";
        }
    }

    public void registarPlanoDeTreinoPorTipo(int nUtilizador, int atividadeMaxDia, int recorrenciaSemanal,
            float calorias, int tipo) {
        Utilizador temp = this.userMap.get(nUtilizador);
        int rand = 0;
        int prevrand = 0;
        if (temp != null) {
            PlanoTreino plano = new PlanoTreino();
            plano.setAtividadeMaxDia(atividadeMaxDia);
            plano.setRecorrenciaSemanal(recorrenciaSemanal);
            plano.setConsumoCalorico(calorias);
            HashMap<Integer, List<Atividade>> mapa = new HashMap<>();
            List<Atividade> atividades;
            for (int i = 0; i < recorrenciaSemanal; i++) {
                atividades = new ArrayList<>();
                for (int j = 0; j < atividadeMaxDia; j++) {
                    Atividade atividade = null;
                    while(prevrand == rand) rand = (int) (Math.random() * 4) + 1;
                    switch (tipo) {
                        case 1:
                            switch(rand){
                                case 1:
                                    atividade = (Atividade) new Remo();
                                    break;
                                case 2:
                                    atividade = (Atividade) new CorridaAtletismo();
                                    break;
                                case 3:
                                    atividade = (Atividade) new Patinagem();
                                    break;
                                case 4:
                                    atividade = (Atividade) new Natacao();
                                    break;
                            }
                            break;
                        case 2:
                            switch(rand){
                                case 1:
                                    atividade = (Atividade) new CorridaEstrada();
                                    break;
                                case 2:
                                    atividade = (Atividade) new TrailNoMonte();
                                    break;
                                case 3:
                                    atividade = (Atividade) new BicicletaDeEstrada();
                                    break;
                                case 4:
                                    atividade = (Atividade) new BicicletaDeMontanha();
                                    break;
                            }
                            break;
                        case 3:
                            switch(rand){
                                case 1:
                                    atividade = (Atividade) new Abdominais();
                                    break;
                                case 2:
                                    atividade = (Atividade) new Alongamentos();
                                    break;
                                case 3:
                                    atividade = (Atividade) new Flexoes();
                                    break;
                                case 4:
                                    atividade = (Atividade) new Prancha();
                                    break;
                            }
                            break;
                        case 4:
                            switch(rand){
                                case 1:
                                    atividade = (Atividade) new LevantamentoDePesos();
                                    break;
                                case 2:
                                    atividade = (Atividade) new ExtensaoDePernas();
                                    break;
                                case 3:
                                    atividade = (Atividade) new Supino();
                                    break;
                                case 4:
                                    atividade = (Atividade) new Deadlift();
                                    break;
                            }
                            break;
                    }
                    prevrand = rand;
                    atividades.add(atividade);
                }
                mapa.put(i, atividades);
            }
            plano.setAtividades(mapa);
            temp.setPlanoTreino(plano);
        }
    }
   
}
