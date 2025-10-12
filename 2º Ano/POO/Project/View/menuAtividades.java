package View;

import java.time.LocalDateTime;
import java.util.Scanner;

import Controller.Controller;
import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;
import Model.SystemDate;

public class menuAtividades {
    public static void start(Scanner scanner, Controller _cont)
            throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        try {
            _cont.save();
        } catch (Exception e) {
            System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
        }
        System.out.println("\nMenu Atividades:\n");
        System.out.println("1 - Adicionar Atividade a Utilizador");
        System.out.println("2 - Executar atividade de Utilizador");
        System.out.println("3 - Remover Atividade Ativa de Utilizador");
        System.out.println("4 - Listar Atividades de Utilizador");
        System.out.println("5 - Listar Atividades Terminadas de Utilizador");
        System.out.println("0 - Voltar\n");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                adicionarAtividade(scanner, _cont);
                break;
            case 2:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                executarAtividade(scanner, _cont);
                break;
            case 3:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                terminarAtividade(scanner, _cont);
                break;
            case 4:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                listarAtividadesAtivas(scanner, _cont);
                break;
            case 5:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                listarAtividadesTerminadas(scanner, _cont);
                break;
            case 0:
                View.mainMenu(scanner);
                break;
        }
        start(scanner, _cont);
    }

    private static void listarAtividadesAtivas(Scanner scanner, Controller _cont) {
        System.out.println("Listar Atividades Ativas de Utilizador");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        System.out.println(_cont.listarAtividadesAtivas(nUtilizador));
    }

    private static void listarAtividadesTerminadas(Scanner scanner, Controller _cont) {
        System.out.println("Listar Atividades Terminadas de Utilizador");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        System.out.println(_cont.listarAtividadesTerminadas(nUtilizador));
    }

    private static void terminarAtividade(Scanner scanner, Controller _cont) {
        System.out.println("Terminar Atividade de Utilizador");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        System.out.println(_cont.listarAtividadesAtivas(nUtilizador));
        System.out.print("Número da Atividade (0 Para sair): ");
        int nAtividade = scanner.nextInt();
        scanner.nextLine();
        if (nAtividade != 0) {
            _cont.eliminarAtividade(nUtilizador, nAtividade);
        }
    }

    private static void executarAtividade(Scanner scanner, Controller _cont) throws UtilizadorNaoExisteException {
        System.out.println("Executar Atividade de Utilizador");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        System.out.print(_cont.listarAtividadesAtivas(nUtilizador));
        System.out.print("Número da Atividade (0 Para sair): ");
        int nAtividade2 = scanner.nextInt();
        scanner.nextLine();
        if (nAtividade2 == 0)
            return;
        LocalDateTime dateInicio = SystemDate.getDate();
        System.out.print("[DEBUG] Quantos minutos passaram?: ");
        int minutos = scanner.nextInt();
        _cont.passarTempo(minutos);
        scanner.nextLine();
        LocalDateTime dateFim = SystemDate.getDate();
        int atv = _cont.perguntaDaAtividade(nUtilizador, nAtividade2);
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
        if (nAtividade2 != 0) {
            System.out.println(_cont.executarAtividade(nUtilizador, nAtividade2, dateInicio, dateFim, n, n2));
        }
    }

    private static void adicionarAtividade(Scanner scanner, Controller _cont) throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        System.out.println("Escolha que utilizador deseja adicionar a atividade: ");
        System.out.print("Número do Utilizador: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Escolha a atividade: ");
        System.out.println("1 - Atividade Distância");
        System.out.println("2 - Atividade Distância e Altimetria");
        System.out.println("3 - Atividade de Repetições");
        System.out.println("4 - Atividade de Repetições com Pesos");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        switch (escolha) {
            case 1:
                System.out.println("1 - Remo");
                System.out.println("2 - Corrida Na Pista de Atletismo");
                System.out.println("3 - Patinagem");
                System.out.println("4 - Natação");
                System.out.println("0 - Voltar\n");
                break;
            case 2:
                System.out.println("1 - Corrida na Estrada");
                System.out.println("2 - Trail no Monte");
                System.out.println("3 - Bicicleta de Estrada");
                System.out.println("4 - Bicicleta de Montanha");
                System.out.println("0 - Voltar\n");
                break;
            case 3:
                System.out.println("1 - Abdominais");
                System.out.println("2 - Alongamentos");
                System.out.println("3 - Flexões");
                System.out.println("4- Prancha");
                System.out.println("0 - Voltar\n");
                break;
            case 4:
                System.out.println("1 - Levantamento De Pesos");
                System.out.println("2 - Extensão De Pernas");
                System.out.println("3 - Supino");
                System.out.println("4 - Deadlift");
                System.out.println("0 - Voltar\n");
                break;
        }
        int escolha2 = scanner.nextInt();
        scanner.nextLine();

        if (escolha2 == 0) {
            start(scanner, _cont);
        }
        _cont.registarAtividade(nUtilizador, escolha, escolha2);
    }
}
