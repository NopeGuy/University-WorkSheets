package ui;

import business.Exceptions.MechanicAlreadyExistsException;
import business.Exceptions.MechanicOutOfHoursException;
import business.Exceptions.WorkstationEmptyException;
import business.Station.Shift;
import business.controller.Controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    Scanner scanner;
    private Controller _cont = null;

    public View(Controller c) {
        _cont = c;
        scanner = new Scanner(System.in);
    }

    public void adminMenu()
            throws MechanicAlreadyExistsException, MechanicOutOfHoursException,
            WorkstationEmptyException {
        while (true) {
            System.out.flush();
            System.out.print("Welcome to E.S. business.Service business.Station Admin Menu!\n");
            System.out.println("1. Register a new Workstation");
            System.out.println("2. Define Workstation Services");
            System.out.println("3. Record Mechanics");
            System.out.println("4. Define Mechanic Workstation");
            System.out.println("5. Change Mechanic Workstation");
            System.out.println("6. Show Workstation Details");
            System.out.println("7. DB menu");
            System.out.println("0. Exit to Main Menu\n");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    _cont.addWorkstation();
                    break;
                case 2:
                    int workstation = this.selectWorkstation();
                    System.out.println("Select the type of service the workstation should support:");
                    System.out.println("0. Universal business.Service");
                    System.out.println("1. Specific business.Service");
                    System.out.print("Choose an option: ");
                    choice = scanner.nextInt();
                    if (choice == 0)
                        _cont.defineWorkstationServices(choice, workstation);
                    if (choice == 1) {
                        System.out.println("1. Combustion Services");
                        System.out.println("2. Diesel Services");
                        System.out.println("3. Electric Services");
                        System.out.println("4. Gasoline Services");
                        System.out.print("Choose a specific service: ");
                        choice = scanner.nextInt();
                        _cont.defineWorkstationServices(choice, workstation);
                    }
                    break;
                case 3:
                    System.out.print("Enter Mechanic Name: ");
                    scanner.nextLine();
                    String mechanicName = scanner.nextLine();
                    System.out.println("Select the types of service the mechanic can do:");
                    System.out.println("0. Universal business.Service");
                    System.out.println("1. Combustion Services");
                    System.out.println("2. Diesel Services");
                    System.out.println("3. Electric Services");
                    System.out.println("4. Gasoline Services");
                    System.out.print("Choose service type: ");
                    int serviceType = scanner.nextInt();
                    boolean valid = false;
                    LocalTime startTime = null;
                    LocalTime endTime = null;
                    while (!valid) {
                        System.out.print("Enter Shift Start Time (HH:mm): ");
                        String startTimeStr = scanner.next();
                        startTime = LocalTime.parse(startTimeStr);

                        System.out.print("Enter Shift End Time (HH:mm): ");
                        String endTimeStr = scanner.next();
                        endTime = LocalTime.parse(endTimeStr);
                        valid = _cont.verifyTime(startTime, endTime);
                    }
                    choice = this.selectWorkstation();
                    _cont.registerMechanic(mechanicName, serviceType, startTime, endTime, choice);
                    break;
                case 4:
                    int mechanicID = this.selectMechanic();
                    choice = this.selectWorkstation();
                    int prev = _cont.whereMechanicIs(mechanicID);
                    if (prev != -1){
                        System.out.println("Mechanic cannot be assigned.");
                        break;
                    }
                    _cont.defineWorkstationMechanic(choice, mechanicID);
                    break;
                case 5:
                    mechanicID = this.selectMechanic();
                    choice = this.selectWorkstation();
                    prev = _cont.whereMechanicIs(mechanicID);
                    if (prev == -1){
                        System.out.println("Mechanic cannot be assigned.");
                        break;
                    }
                    _cont.clearWorkstation(prev);
                    _cont.defineWorkstationMechanic(choice, mechanicID);
                    break;
                case 6:
                    int workstationID = this.selectWorkstation();
                    this.printWorkstation(workstationID);
                    break;
                case 7:
                    _cont.load();
                    break;
                case 0:
                    this.legitMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public int selectWorkstation() {
        System.out.println("Select a Workstation:");
        _cont.printWorkstations();
        System.out.print("Choose a workstation: ");
        int workstationChoice = scanner.nextInt();
        _cont.selectWorkstation(workstationChoice);
        return workstationChoice;
    }

    public int selectMechanic() {
        System.out.println("Select a Mechanic:");
        _cont.printMechanics();
        System.out.print("Choose a Mechanic: ");
        int mechanicChoice = scanner.nextInt();
        _cont.selectMechanic(mechanicChoice);
        return mechanicChoice;
    }

    public void legitMenu()
            throws MechanicAlreadyExistsException, MechanicOutOfHoursException,
            WorkstationEmptyException {
        while (true) {
            System.out.print("\nWelcome to E.S business station menu!\n\n");
            System.out.print("1. Login as Admin\n");
            System.out.print("2. Login as Mechanic\n");
            System.out.print("0. Exit Menu\n");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Exiting the E.S. business.Service business.Station menu. Goodbye!");
                    System.exit(0);
                    break;
                case 1:
                    adminMenu();
                    break;
                case 2:
                    mechanicMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void mechanicMenu() throws WorkstationEmptyException {
        int mechanicID = selectMechanic();
        int workstationID;

        System.out.println("Welcome Mechanic " + mechanicID);
        System.out.println("Mechanic Menu:");
        System.out.println("1. Register Client");
        System.out.println("2. Mark Service");
        System.out.println("3. Consult Service and Car Information of Workstation");
        System.out.println("4. Modify Service Status");
        System.out.println("0. Exit");
        System.out.print("Choose the desired option: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                this.registerUser();
                break;
            case 2:
                workstationID = selectWorkstation();
                int clientID = this.getClientChoice();
                int vehicleChoice = this.getVehicleChoice(clientID);
                Shift shift = this.getShift();
                this.markService(clientID, vehicleChoice, workstationID, shift);
                break;
            case 3:
                workstationID = selectWorkstation();
                _cont.consultService(workstationID);
                break;
            case 4:
                workstationID = selectWorkstation();
                _cont.modifyServiceStatus(workstationID);
                System.out.println("Was another service required?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                if (choice == 1) {
                    clientID = this.getClientChoice();
                    vehicleChoice = this.getVehicleChoice(clientID);
                    choice = this.getChoice(workstationID);
                    _cont.addServiceToCar(workstationID, clientID, vehicleChoice, choice);
                }
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");

        }
    }

    public void printWorkstation(int choice) {
        _cont.printWorkstation(choice);
    }

    public void printVehicles(int clientID) {
        _cont.printVehicles(clientID);
    }

    public int getChoice(int workstationID) {
        int serviceChoice = 0;
        System.out.println("Select a service:");
        _cont.printServices(workstationID);
        System.out.print("Enter service choice: ");
        serviceChoice = scanner.nextInt();
        return serviceChoice;
    }

    private int getClientChoice() {
        System.out.println("Select a client:");
        _cont.printClients();
        System.out.print("Enter client choice: ");
        return scanner.nextInt();
    }

    private int getVehicleChoice(int clientID) {
        System.out.println("Select a vehicle for the client: ");
        this.printVehicles(clientID);
        System.out.print("Enter vehicle choice: ");
        return scanner.nextInt();
    }

    private Shift getShift() {
        boolean valid = false;
        LocalTime startTime = null;
        LocalTime endTime = null;
        while (!valid) {
            System.out.print("Enter service Start Time (HH:mm): ");
            String startTimeStr = scanner.next();
            startTime = LocalTime.parse(startTimeStr);

            System.out.print("Enter service End Time (HH:mm): ");
            String endTimeStr = scanner.next();
            endTime = LocalTime.parse(endTimeStr);
            valid = _cont.verifyTime(startTime, endTime);
        }
        return new Shift(startTime, endTime);
    }

    private void registerUser() {
        System.out.print("Enter client's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter client's VATNumber: ");
        String VATNumber = scanner.nextLine();
        System.out.print("Enter client's address: ");
        String address = scanner.nextLine();
        System.out.print("Enter client's telephone: ");
        String telephone = scanner.nextLine();
        System.out.print("Enter client's email: ");
        String email = scanner.nextLine();
        System.out.print("Enter the number of vehicles to add: ");
        int numVehicles = scanner.nextInt();
        scanner.nextLine();

        List<String> vehicleFiles = new ArrayList<>();
        List<Integer> vehicleTypes = new ArrayList<>();

        for (int i = 0; i < numVehicles; i++) {
            System.out.print("Enter vehicle file for vehicle #" + (i + 1) + ": ");
            String vehicleFile = scanner.nextLine();
            vehicleFiles.add(vehicleFile);

            System.out.println("Enter vehicle type for vehicle #" + (i + 1) + ": ");
            System.out.println("1. Diesel Vehicle");
            System.out.println("2. Electric Vehicle");
            System.out.println("3. Gasoline Vehicle");
            System.out.println("4. Hybrid Vehicle");
            System.out.print("Enter service choice: ");
            int vehicleType = scanner.nextInt();
            scanner.nextLine();
            vehicleTypes.add(vehicleType);
        }

        _cont.registerUser(email, name, VATNumber, address, telephone, vehicleFiles, vehicleTypes);
        System.out.println("Client and vehicles successfully registered!");
    }

    public void markService(int clientID, int vehicleChoice, int workstationID, Shift shift) {

        System.out.println("1. Check-up\n2. Mark service\n");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        int serviceChoice;
        boolean valid = false;
        while (!valid)
            valid = _cont.isInvalidShiftHours(shift.getStartTime(), shift.getEndTime());
        switch (choice) {
            case 1:
                serviceChoice = this.getChoice(workstationID);
                _cont.performCheckUp(workstationID, clientID, vehicleChoice, serviceChoice);
                break;
            case 2:
                serviceChoice = this.getChoice(workstationID);
                _cont.markServiceForWorkstation(workstationID, clientID, vehicleChoice, serviceChoice);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
