package Queries;

import java.time.LocalDateTime;
import java.util.Map;

import Model.Atividades.*;
import Model.Utilizadores.*;

public class MaisCaloriasSempre implements Querier {

    private Map<Integer, Utilizador> utilizadores;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public MaisCaloriasSempre(Map<Integer, Utilizador> utilizadores, LocalDateTime inicio, LocalDateTime fim) {
        this.utilizadores = utilizadores;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Object execute() {

        int maxCalorias = 0;
        int count = 0;
        int id = -1;
        if (inicio == null)
            inicio = LocalDateTime.MIN;
        if (fim == null)
            fim = LocalDateTime.MAX;
        for (Utilizador u : utilizadores.values()) {
            maxCalorias = 0;
            for (Atividade a : u.getAtividadesEfetuadas()) {
                if (a.getDatainicio().isAfter(inicio) && a.getDatafim().isBefore(fim)) {
                    maxCalorias += a.calculaCaloriasGastas(u);
                }
            }
            if (maxCalorias > count) {
                count = maxCalorias;
                id = u.getNUtilizador();
            }
        }
        if (id == -1)
            return "Não existem atividades.";
        return ("O utilizador com mais calorias gastas foi o utilizador " + id + " com " + count + " calorias.");
    }

}
