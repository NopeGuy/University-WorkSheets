package Queries;

import java.time.LocalDateTime;
import java.util.Map;
import Model.Atividades.*;
import Model.Utilizadores.*;

public class MaiorDistancia implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private int choice;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public MaiorDistancia(Map<Integer, Utilizador> utilizadores, int choice, LocalDateTime inicio,
            LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.choice = choice;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {
        double maxDist = 0;
        int id = choice;
        Utilizador u = utilizadores.get(id);
        if (inicio == null)
            inicio = LocalDateTime.MIN;
        if (fim == null)
            fim = LocalDateTime.MAX;
        if (u == null)
            return "Utilizador não existe";
        for (Atividade a : u.getAtividadesEfetuadas()) {
            if (a instanceof AtividadeDist) {
                AtividadeDist ada = (AtividadeDist) a;
                if (ada.getDatainicio().isAfter(inicio) && ada.getDatafim().isBefore(fim)) {
                    maxDist += ada.getDistancia();
                }
            }
            if (a instanceof AtividadeDistAlt) {
                AtividadeDistAlt ada = (AtividadeDistAlt) a;
                if (ada.getDatainicio().isAfter(inicio) && ada.getDatafim().isBefore(fim)) {
                    maxDist += ada.getDistancia();
                }
            }
        }
        return ("A distância total do utilizador foi de: " + maxDist + " metros.");
    }
}
