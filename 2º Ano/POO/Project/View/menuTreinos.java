package View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Controller.Controller;
import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;
import Model.SystemDate;

public class menuTreinos {
    public static void start(Scanner scanner, Controller _cont)
            throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        try {
            _cont.save();
        } catch (Exception e) {
            System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
        }
        System.out.println("\nMenu Plano de Treino: \n");
        System.out.println("1 - Adicionar Plano de Treino por parametro");
        System.out.println("2 - Adicionar Plano de Treino por tipo de atividade");
        System.out.println("3 - Adicionar plano de treino por objetivo");
        System.out.println("4 - Listar Planos de Treino");
        System.out.println("5 - Executar Plano de Treino");
        System.out.println("6 - Recomeçar Plano de Treino");
        System.out.println("7 - Apagar Plano de Treino");
        System.out.println("8 - Ver planos realizados");
        System.out.println("0 - Voltar\n");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                adicionarPlanoTreino(scanner, _cont);
                break;
            case 2:
                adicionarPlanoTreinoPorTipo(scanner, _cont);
                break;
            case 3:
                System.out.println("To-do");
                break;
            case 4:
                listarPlanosTreino(scanner, _cont);
                break;
            case 5:
                executarPlanoTreino(scanner, _cont);
                break;
            case 6:
                resetPlanoTreino(scanner, _cont);
                start(scanner, _cont);
                break;
            case 7:
                removerPlanoTreino(scanner, _cont);
                break;
            case 8:
                verPlanosRealizados(scanner, _cont);
                break;
            case 0:
                View.mainMenu(scanner);
                break;
            default:
                System.out.println("\nOpção inválida!\n");
                break;
        }
        start(scanner, _cont);
    }

    private static void adicionarPlanoTreinoPorTipo(Scanner scanner, Controller _cont) {
        int atividadeMaxDia = -1;
        int recorrenciaSemanal = -1;
        System.out.print("Adicionar Plano de Treino\n");
        System.out.println(_cont.listarUtilizadores());
        if (_cont.utilizadoresVazios()) {
            return;
        }
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        while((atividadeMaxDia < 0 || atividadeMaxDia > 6) && (recorrenciaSemanal < 0 || recorrenciaSemanal > 7)){
        System.out.print("Número máximo de atividades para um dia: ");
        atividadeMaxDia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nRecorrência Semanal: ");
        recorrenciaSemanal = scanner.nextInt();
        scanner.nextLine();
        }
        System.out.print("\nConsumo Calórico Esperado: ");
        float calorias = scanner.nextFloat();
        System.out.println("Escolha o tipo de atividade: \n");
        System.out.println("1 - Atividades de Distância");
        System.out.println("2 - Atividades de Distância e Altimetria");
        System.out.println("3 - Atividades de Repetições");
        System.out.println("4 - Atividades de Repetições de Pesos");
        System.out.print("Escolha uma opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        _cont.registarPlanoDeTreinoPorTipo(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, tipo);
    }

    private static void verPlanosRealizados(Scanner scanner, Controller _cont) {
        System.out.print("Ver planos realizados\n");
        System.out.println(_cont.listarUtilizadores());
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        System.out.print(_cont.listarPlanoRealizado(nUtilizador));
    }

    private static void resetPlanoTreino(Scanner scanner, Controller _cont) {
        System.out.print("Recomeçar Plano de Treino\n");
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        if (_cont.planosTreinoNull(nUtilizador)) {
            System.out.println("Não existem planos de treino para este utilizador.");
            return;
        }
        _cont.resetPlanoAtividade(nUtilizador);
    }

    private static void listarPlanosTreino(Scanner scanner, Controller _cont) {
        System.out.print("Listar Planos de Treino\n");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        if (_cont.planosTreinoNull(nUtilizador)) {
            System.out.println("Não existem planos de treino para este utilizador.");
            return;
        }
        System.out.print(_cont.listarPlanoTreino(nUtilizador));
    }

    private static void adicionarPlanoTreino(Scanner scanner, Controller _cont) {
        int atividadeMaxDia = -1;
        int recorrenciaSemanal = -1;
        System.out.print("Adicionar Plano de Treino\n");
        System.out.println(_cont.listarUtilizadores());
        if(_cont.utilizadoresVazios()){
            return;
        }
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        while((atividadeMaxDia < 0 || atividadeMaxDia > 6) && (recorrenciaSemanal < 0 || recorrenciaSemanal > 7)){
        System.out.print("\nNúmero máximo de atividades para um dia: ");
        atividadeMaxDia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nRecorrência Semanal: ");
        recorrenciaSemanal = scanner.nextInt();
        scanner.nextLine();
        }
        System.out.print("\nConsumo Calórico Esperado: ");
        float calorias = scanner.nextFloat();

        HashMap<Integer, List<Integer>> escolhas = new HashMap<>();
        int escolha;
        int i = 0;
        int j = 0;
        for (i = 0; i < recorrenciaSemanal; i++) {
            List<Integer> escolhaList = new ArrayList<>();
            System.out.println("Dia da Semana: " + i);
            for (j = 0; j < atividadeMaxDia; j++) {
                System.out.println("Atividade " + j);
                System.out.println("Escolha o tipo de atividade: ");
                System.out.println("\nAtividades de Distância:");
                System.out.println("1 - Remo");
                System.out.println("2 - Corrida Na Pista de Atletismo");
                System.out.println("3 - Patinagem");
                System.out.println("4 - Natação");
                System.out.println("\nAtividades de Distância e Altimetria:");
                System.out.println("5 - Corrida na Estrada");
                System.out.println("6 - Trail no Monte");
                System.out.println("7 - Bicicleta de Estrada");
                System.out.println("8 - Bicicleta de Montanha");
                System.out.println("\nAtividades de Repetições:");
                System.out.println("9 - Abdominais");
                System.out.println("10 - Alongamentos");
                System.out.println("11 - Flexões");
                System.out.println("12- Prancha");
                System.out.println("\nAtividades de Repetições de Pesos:");
                System.out.println("13 - Levantamento De Pesos");
                System.out.println("14 - Extensão De Pernas");
                System.out.println("15 - Supino");
                System.out.println("16 - Deadlift");
                System.out.print("Escolha uma opção: (0 para sair) ");
                escolha = scanner.nextInt();
                scanner.nextLine();
                if(escolha > 16 || escolha < 0){
                    System.out.println("Opção inválida!");
                    j--;
                    continue;
                }
                else if (escolha == 0)
                    return;
                escolhaList.add(escolha);
            }
            escolhas.put(i, escolhaList);
        }
        _cont.registarPlanoDeTreino(nUtilizador, atividadeMaxDia, recorrenciaSemanal, calorias, escolhas);
    }

    private static void removerPlanoTreino(Scanner scanner, Controller _cont) {
        System.out.print("Remover Plano de Treino\n");
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        if (_cont.planosTreinoNull(nUtilizador)) {
            System.out.println("Não existem planos de treino para este utilizador.");
            return;
        }
        _cont.removerPlanoTreino(nUtilizador);
    }

    private static void executarPlanoTreino(Scanner scanner, Controller _cont) throws UtilizadorNaoExisteException {
        System.out.print("Executar Plano de Treino\n");
        System.out.println(_cont.listarUtilizadores());
        System.out.print("Número do utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        if(_cont.planosTreinoNull(nUtilizador)) {
            System.out.println("Não existem planos de treino para este utilizador.");
            return;
        }
        System.out.print(_cont.listarPlanoTreino(nUtilizador));
        System.out.println("Escolha o dia a realizar(0 para sair):");
        int diaDaAtividade = scanner.nextInt();
        scanner.nextLine();
        if (_cont.verificarDia(nUtilizador, diaDaAtividade-1)) {
            System.out.println("Dia já realizado");
            return;
        }
        if (diaDaAtividade == 0)
            return;
        while (true) {
            System.out.print("Introduza o Número da Atividade(0 para sair): ");
            int nAtividade = scanner.nextInt();
            scanner.nextLine();
            if (nAtividade == 0)
                break;
            LocalDateTime dateInicio = SystemDate.getDate();
            System.out.print("[DEBUG] Quantos minutos passaram para a conclusão da atividade?: ");
            int minutos = scanner.nextInt();
            _cont.passarTempo(minutos);
            scanner.nextLine();
            LocalDateTime dateFim = SystemDate.getDate();
            int atv = _cont.perguntaDaAtividadeTreino(nUtilizador, diaDaAtividade-1, nAtividade-1);
            int n = 0;
            int n2 = 0;
            switch (atv) {
                case 0:
                    System.out.print("Distância(em metros): ");
                    n = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 1:
                    System.out.print("Distância(em metros): ");
                    n = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Altura(em metros): ");
                    n2 = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.print("Repetições: ");
                    n = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("Repetições: ");
                    n = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Peso(em kg): ");
                    n2 = scanner.nextInt();
                    scanner.nextLine();
                    break;
                default:
                    break;
            }
            System.out.println(_cont.executarAtividadePlanoTreino(nUtilizador, diaDaAtividade-1, nAtividade-1, dateInicio, dateFim, n, n2));
        }
    }
}
