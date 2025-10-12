package Queries;

import java.time.LocalDateTime;
import java.util.Map;

import Model.PlanoTreino;
import Model.Utilizadores.*;

public class PlanoMaisExigente implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public PlanoMaisExigente(Map<Integer, Utilizador> utilizadores, LocalDateTime inicio,
            LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {
        float calorias = 0.0f;
        int id = 0;
        PlanoTreino plano = null;
        if (inicio == null)
            inicio = LocalDateTime.MIN;
        if (fim == null)
            fim = LocalDateTime.MAX;
        for (Utilizador u : utilizadores.values()) {
            plano = u.getPlanoTreino();
            if (plano != null) {
                if (calorias < plano.getConsumoCalorico()) {
                    calorias = plano.getConsumoCalorico();
                    id = u.getNUtilizador();
                }
            }
        }
        return ("O plano mais exigente é o plano do utilizador " + id + " com " + calorias + " calorias.");
    }
}
