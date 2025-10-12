package Queries;

import java.time.LocalDateTime;
import java.util.Map;
import Model.Atividades.*;
import Model.Utilizadores.*;

public class MaiorAltimetria implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private int choice;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public MaiorAltimetria(Map<Integer, Utilizador> utilizadores, int choice, LocalDateTime inicio,
            LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.choice = choice;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {
        double maxAltimetria = 0;
        int id = choice;
        Utilizador u = utilizadores.get(id);
        if (inicio == null)
            inicio = LocalDateTime.MIN;
        if (fim == null)
            fim = LocalDateTime.MAX;
        if (u == null)
            return "Utilizador não existe";
        for (Atividade a : u.getAtividadesEfetuadas()) {
            if (a instanceof AtividadeDistAlt) {
                AtividadeDistAlt ada = (AtividadeDistAlt) a;
                if (ada.getDatainicio().isAfter(inicio) && ada.getDatafim().isBefore(fim)) {
                    maxAltimetria += ada.getAltura();
                }
            }
        }
        return ("A altimetria total do utilizador foi de: " + maxAltimetria + " metros.");
    }
}
