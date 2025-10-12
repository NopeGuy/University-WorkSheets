package View;

import java.util.Scanner;
import Controller.Controller;
import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;

public class View {

    private static Controller _cont = null;
    static Scanner scanner = null;

    public View(Controller c) {
        _cont = c;
        scanner = new Scanner(System.in);
    }

    public void start() throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        mainMenu(scanner);
        scanner.close();
    }

    public static void mainMenu(Scanner scanner) throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        try {
            _cont.save();
        } catch (Exception e) {
            System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
        }
        // serializable não guarda o counter, por isso temos de o guardar à parte
        _cont.getAndSetCounter();
        System.out.println("\n\n---------------------------");
        System.out.println("| Bem-vindo ao GymAtHome! |");
        System.out.println("---------------------------\n\n");
        _cont.printData();

        System.out.println("1  - Menu Utilizadores");
        System.out.println("2  - Menu de Atividades");
        System.out.println("3  - Menu de Plano de Treino");
        System.out.println("4  - Queries");
        System.out.println("5  - Gravar estado da aplicação");
        System.out.println("99 - Passar tempo");
        System.out.println("0  - Sair\n");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                menuUtilizadores.start(scanner, _cont);
                break;
            case 2:
                menuAtividades.start(scanner, _cont);
                break;
            case 3:
                menuTreinos.start(scanner, _cont);
                break;
            case 4:
                menuQueries.start(scanner, _cont);
                break;
            case 5:
                try {
                    _cont.save();
                } catch (Exception e) {
                    System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
                }
                System.out.println("Estado da aplicação guardado com sucesso!");
                mainMenu(scanner);
                break;
            case 99:
                System.out.print("Quantos minutos deseja passar? ");
                int minutos = scanner.nextInt();
                _cont.passarTempo(minutos);
                scanner.nextLine();
                mainMenu(scanner);
                break;
            case 0:
                try {
                    _cont.save();
                } catch (Exception e) {
                    System.out.println("Erro ao guardar o estado da aplicação: " + e.getMessage());
                }
                System.out.println("Adeus!");
                System.exit(0);
                break;
            default:
                System.out.println("\nOpção inválida!\n");
                break;
        }
    }
}