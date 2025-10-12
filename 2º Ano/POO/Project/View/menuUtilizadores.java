package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import Controller.Controller;
import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;

public class menuUtilizadores {
        public static void start(Scanner scanner, Controller _cont) throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        try {
            _cont.save();
        } catch (Exception e) {
            System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
        }
        System.out.println("\nMenu Utilizadores: \n");
        System.out.println("1 - Registar Utilizador");
        System.out.println("2 - Listar Utilizadores");
        System.out.println("3 - Atualizar Utilizador");
        System.out.println("4 - Apagar Utilizador");
        System.out.println("0 - Voltar\n");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                adicionarUtilizador(scanner, _cont);
                start(scanner, _cont);
                break;
            case 2:
                System.out.println(_cont.listarUtilizadores());
                start(scanner, _cont);
                break;
            case 3:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                atualizarUtilizador(scanner, _cont);
                break;
            case 4:
                System.out.println(_cont.listarUtilizadores());
                if (_cont.utilizadoresVazios()) {
                    start(scanner, _cont);
                }
                removerUtilizador(scanner, _cont);
                break;
            case 0:
                View.mainMenu(scanner);
                break;
            default:
                System.out.println("\nOpção inválida!\n");
                start(scanner, _cont);
                break;
        }
        start(scanner, _cont);
    }    

    public static void adicionarUtilizador(Scanner scanner, Controller _cont) throws UtilizadorJaExisteException {
        System.out.print("Adicionar Utilizador\n");
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("\nMorada: ");
        String morada = scanner.nextLine();
        System.out.print("\nEmail: ");
        String email = scanner.nextLine();
        int freqCard = 0;
        while (true) {
            try {
                System.out.print("\nFrequência Cardíaca Média: ");
                freqCard = scanner.nextInt();
                if (freqCard < 50 || freqCard > 250) {
                    System.out.println("A frequência cardíaca deve estar entre 50 e 250. Tente novamente.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número inteiro para a frequência cardíaca.");
                scanner.next();
            }
        }
        scanner.nextLine();

        String tipoUtilizador = "-1";
        while (!tipoUtilizador.equals("0") && !tipoUtilizador.equals("1") && !tipoUtilizador.equals("2")) {
            try {
                System.out.println("Tipo de Utilizador:");
                System.out.println("0 - Utilizador Ocasional");
                System.out.println("1 - Utilizador Amador");
                System.out.println("2 - Utilizador Profissional");
                System.out.print("Escolha uma opção: ");
                tipoUtilizador = scanner.nextLine();
                System.out.println(tipoUtilizador);
                if (!tipoUtilizador.equals("0") && !tipoUtilizador.equals("1") && !tipoUtilizador.equals("2")) {
                    System.out.println("Opção inválida! Por favor, escolha uma opção entre 0 e 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número inteiro para o tipo de utilizador.");
                scanner.next();
            }
        }
        try {
            int tipo = Integer.parseInt(tipoUtilizador);
            _cont.registarUtilizador(nome, morada, email, freqCard, tipo);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void removerUtilizador(Scanner scanner, Controller _cont) throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        System.out.println("Remover Utilizador");
        System.out.print("Número do Utilizador a remover: ");

        int nUtilizador = scanner.nextInt();
        _cont.removerUtilizador(nUtilizador);
        start(scanner, _cont);
    }

    public static void atualizarUtilizador(Scanner scanner, Controller _cont) throws UtilizadorNaoExisteException, UtilizadorJaExisteException {
        System.out.println("Atualizar Utilizador");
        System.out.print("Número do Utilizador a atualizar: ");
        int nUtilizador = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.println("Indique que dados pretende atualizar:");
            System.out.println("1 - Nome");
            System.out.println("2 - Email");
            System.out.println("3 - Morada");
            System.out.println("4 - Frequência Cardíaca Média");
            System.out.println("5 - Tipo de Utilizador");
            System.out.println("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Novo Nome: ");
                    String nome = scanner.nextLine();
                    _cont.atualizarUtilizador(nUtilizador, opcao, nome);
                    break;
                case 2:
                    System.out.print("Novo Email: ");
                    String email = scanner.nextLine();
                    _cont.atualizarUtilizador(nUtilizador, opcao, email);
                    break;
                case 3:
                    System.out.print("Nova Morada: ");
                    String morada = scanner.nextLine();
                    _cont.atualizarUtilizador(nUtilizador, opcao, morada);
                    break;
                case 4:
                    String freqCard = "";
                    while (true) {
                        try {
                            System.out.print("Nova Frequência Cardíaca Média: ");
                            freqCard = scanner.nextLine();
                            int freqCardInt = Integer.parseInt(freqCard);
                            if (freqCardInt < 50 || freqCardInt > 250) {
                                System.out.println("A frequência cardíaca deve estar entre 50 e 250. Tente novamente.");
                                continue;
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Por favor, insira um número inteiro para a frequência cardíaca.");
                            scanner.next();
                        }
                    }
                    _cont.atualizarUtilizador(nUtilizador, opcao, freqCard);
                    break;
                case 5:
                    int tipoUtilizador = -1;
                    while (tipoUtilizador < 0 || tipoUtilizador > 2) {
                        try {
                            System.out.println("Tipo de Utilizador:");
                            System.out.println("0 - Utilizador Ocasional");
                            System.out.println("1 - Utilizador Amador");
                            System.out.println("2 - Utilizador Profissional");
                            System.out.print("Escolha uma opção: ");
                            tipoUtilizador = scanner.nextInt();
                            if (tipoUtilizador < 0 || tipoUtilizador > 2) {
                                System.out.println("Opção inválida! Por favor, escolha uma opção entre 0 e 2.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Por favor, insira um número inteiro para o tipo de utilizador.");
                            scanner.next();
                        }
                    }
                    _cont.atualizarUtilizador(nUtilizador, opcao, String.valueOf(tipoUtilizador));
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        start(scanner, _cont);
    }
}
