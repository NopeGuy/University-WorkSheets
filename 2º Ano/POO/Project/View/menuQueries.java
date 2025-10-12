package View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Controller.Controller;
import Errors.UtilizadorJaExisteException;
import Errors.UtilizadorNaoExisteException;

public class menuQueries {
    static void start(Scanner scanner, Controller _cont)
            throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        System.out.println("\nMenu Queries:\n");
        System.out.println("1 - Utilizador com mais calorias gastas de sempre");
        System.out.println("2 - Utilizador com mais calorias gastas num intervalo de tempo");
        System.out.println("3 - Utilizador com mais atividades");
        System.out.println("4 - Utilizador com mais atividades num intervalo de tempo");
        System.out.println("5 - Atividade mais realizada de sempre");
        System.out.println("6 - Atividade mais realizada num intervalo de tempo");
        System.out.println("7 - Km's percorridos por utilizador de sempre");
        System.out.println("8 - Km's percorridos por utilizador num intervalo de tempo");
        System.out.println("9 - Altura em altimetria realizada");
        System.out.println("10 - Altura em altimetria realizada num intervalo de tempo");
        System.out.println("11 - Plano de treino mais exigente");
        System.out.println("12 - Plano de treino mais exigente num intervalo de tempo\n");

        System.out.println("0 - Voltar\n");
        System.out.print("Escolha uma opção: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 0 || choice > 12) {
            System.out.println("Opção inválida!");
            start(scanner, _cont);
        }
        switch (choice) {
            case 1:
                System.out.println("Utilizador com mais calorias gastas sempre");
                System.out.println(_cont.executeQuery(choice, 0, null, null));
                break;
            case 2:
                System.out.println("Utilizador com mais calorias gastas num intervalo de tempo");
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(dataInicio, formatter);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim = scanner.nextLine();
                LocalDateTime dateTime2 = LocalDateTime.parse(dataFim, formatter);
                System.out.println(_cont.executeQuery(choice, 0, dateTime, dateTime2));
                break;
            case 3:
                System.out.println("Utilizador com mais atividades");
                System.out.println(_cont.executeQuery(choice, 0, null, null));
                break;
            case 4:
                System.out.println("Utilizador com mais atividades num intervalo de tempo");
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio3 = scanner.nextLine();
                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime6 = LocalDateTime.parse(dataInicio3, formatter3);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim3 = scanner.nextLine();
                LocalDateTime dateTime7 = LocalDateTime.parse(dataFim3, formatter3);
                System.out.println(_cont.executeQuery(choice, 0, dateTime6, dateTime7));
                break;
            case 5:
                System.out.println(_cont.executeQuery(choice, 0, null, null));
                break;
            case 6:
                System.out.println("Atividade mais realizada num intervalo de tempo");
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio1 = scanner.nextLine();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime1 = LocalDateTime.parse(dataInicio1, formatter1);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim1 = scanner.nextLine();
                LocalDateTime dateTime3 = LocalDateTime.parse(dataFim1, formatter1);
                System.out.println(_cont.executeQuery(choice, 0, dateTime1, dateTime3));
                break;
            case 7:
                System.out.println("Km's percorridos por utilizador");
                _cont.listarUtilizadores();
                System.out.print("Número do Utilizador: ");
                int nUtilizador = scanner.nextInt();
                scanner.nextLine();
                System.out.println(_cont.executeQuery(choice, nUtilizador, null, null));
                break;
            case 8:
                System.out.println("Km's percorridos por utilizador num intervalo de tempo");
                _cont.listarUtilizadores();
                System.out.print("Número do Utilizador: ");
                int nUtilizador2 = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio2 = scanner.nextLine();
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime4 = LocalDateTime.parse(dataInicio2, formatter2);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim2 = scanner.nextLine();
                LocalDateTime dateTime5 = LocalDateTime.parse(dataFim2, formatter2);
                System.out.println(_cont.executeQuery(choice, nUtilizador2, dateTime4, dateTime5));
                break;
            case 9:
                System.out.println("Altura em altimetria realizada");
                _cont.listarUtilizadores();
                System.out.print("Número do Utilizador: ");
                nUtilizador = scanner.nextInt();
                System.out.println(_cont.executeQuery(choice, nUtilizador, null, null));
                break;
            case 10:
                System.out.println("Altura em altimetria realizada num intervalo de tempo");
                _cont.listarUtilizadores();
                System.out.print("Número do Utilizador: ");
                nUtilizador = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio4 = scanner.nextLine();
                DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime8 = LocalDateTime.parse(dataInicio4, formatter4);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim4 = scanner.nextLine();
                LocalDateTime dateTime9 = LocalDateTime.parse(dataFim4, formatter4);
                System.out.println(_cont.executeQuery(choice, nUtilizador, dateTime8, dateTime9));
                break;
            case 11:
                System.out.println("Plano de treino mais exigente");
                System.out.println(_cont.executeQuery(choice, 0, null, null));
                break;
            case 12:
                System.out.println("Plano de treino mais exigente num intervalo de tempo");
                System.out.print("Data de Início: dd/MM/yyyy HH:mm \n");
                String dataInicio5 = scanner.nextLine();
                DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime10 = LocalDateTime.parse(dataInicio5, formatter5);
                System.out.print("Data de Fim: dd/MM/yyyy HH:mm \n");
                String dataFim5 = scanner.nextLine();
                LocalDateTime dateTime11 = LocalDateTime.parse(dataFim5, formatter5);
                System.out.println(_cont.executeQuery(choice, 0, dateTime10, dateTime11));
                break;
            case 0:
                View.mainMenu(scanner);
                break;
        }
        start(scanner, _cont);
    }
}
