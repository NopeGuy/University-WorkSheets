package Queries;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import Model.Atividades.*;
import Model.Utilizadores.*;

public class AtividadeMaisRealizada implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public AtividadeMaisRealizada(Map<Integer, Utilizador> utilizadores, LocalDateTime inicio, LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {
        // get all calories from atividades and planos
        HashMap<String, Integer> atividades = new HashMap<>();
        if(inicio == null) inicio = LocalDateTime.MIN;
        if(fim == null) fim = LocalDateTime.MAX;
        for (Utilizador u : utilizadores.values()) {
            for (Atividade a : u.getAtividadesEfetuadas()) {
                if(a.getDatainicio().isBefore(inicio) || a.getDatafim().isAfter(fim)) continue;
                String name = a.getClass().getSimpleName();
                if(atividades.containsKey(name)) {
                    atividades.put(name, atividades.get(name) + 1);
                } else {
                    atividades.put(name, 1);
                }
            }
        }
        int max = 0;
        String maxName = "";
        for (Map.Entry<String, Integer> entry : atividades.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
                maxName = entry.getKey();
            }
        }
        if(max == 0) return ("Nenhuma atividade foi realizada.");
        return ("A atividade mais realizada foi: " + maxName + " com " + max + " vezes.");
    }
}
