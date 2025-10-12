package Queries;

import java.time.LocalDateTime;
import java.util.Map;

import Model.Atividades.*;
import Model.Utilizadores.*;

public class MaiorNumeroAtividades implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public MaiorNumeroAtividades(Map<Integer, Utilizador> utilizadores, LocalDateTime inicio,
            LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {
        int maxAtividades = 0;
        int id = 0;
        if (inicio == null)
            inicio = LocalDateTime.MIN;
        if (fim == null)
            fim = LocalDateTime.MAX;
        for (Utilizador u : utilizadores.values()) {
            int count = 0;
            for (Atividade a : u.getAtividadesEfetuadas()) {
                if (a.getDatainicio().isAfter(inicio) && a.getDatafim().isBefore(fim)) {
                    count++;
                }
            }
            if (count > maxAtividades) {
                maxAtividades = count;
                id = u.getNUtilizador();
            }
        }
        if(maxAtividades == 0) return "Não existem atividades.";
        return ("O utilizador com mais atividades foi o utilizador " + id + " com " + maxAtividades + " atividades.");
    }
}
