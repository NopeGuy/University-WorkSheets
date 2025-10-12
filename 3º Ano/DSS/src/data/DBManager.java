package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBManager {

    private static final String URL = DAOconfig.URL;
    private static final String USERNAME = DAOconfig.USERNAME;
    private static final String PASSWORD = DAOconfig.PASSWORD;

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        displayMenu();
        int option = getUserOption();

        switch (option) {
            case 1:
                loadDB();
                break;
            case 2:
                clearDB();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }

    private void displayMenu() {
        System.out.println("Select one of the following options: ");
        System.out.println("1. Load data");
        System.out.println("2. Clear data");
        System.out.println("3. Exit");
    }

    private int getUserOption() {
        System.out.print("Enter your option: ");
        return scanner.nextInt();
    }

    public void loadDB() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stm = conn.createStatement()) {
            String insertMechanics = "INSERT INTO mechanics (Name, Skills, Shifts, Id) VALUES " +
                    "('John Combustion', 'Service{genericService=CombustionService}', '09:23-16:25', 1), " +
                    "('Jane Diesel', 'Service{genericService=DieselService}', '10:12-17:25', 2), " +
                    "('Bob Electric', 'Service{genericService=ElectricService}', '11:23-19:00', 3), " +
                    "('Charlie Universal', 'Service{genericService=UniversalService}', '12:00-19:00', 4)";

            stm.executeUpdate(insertMechanics);

            // INSERT INTO users
            String insertUsers = "INSERT INTO users (Id, Name, Email, VATNumber, Address, Telephone) VALUES "
                    +
                    "(1, 'Alice Johnson', 'alice@example.com', 'VAT123', '123 Main St', '555-1234'), " +
                    "(2, 'Charlie Brown', 'charlie@example.com', 'VAT456', '456 Oak St', '555-5678'), " +
                    "(3, 'Eva Green', 'eva@example.com', 'VAT789', '789 Pine St', '555-9876')";

            stm.executeUpdate(insertUsers);

            // INSERT INTO vehicles
            String insertVehicles = "INSERT INTO vehicles (VehicleId, UserId, VehicleFile, Services, TypeOfVehicle) VALUES "
                    +
                    "(1, 1, 'CarFile123', '[]', 1), " +
                    "(2, 2, 'MotorcycleFile456', '[]', 2), " +
                    "(3, 3, 'VanFile789', '[]', 3)";

            stm.executeUpdate(insertVehicles);

            // INSERT INTO workstations
            String insertWorkstations = "INSERT INTO workstations (Id, IsAvailable, Service, MechanicID, CurrentService, CurrentClient, CurrentVehicle) VALUES "
                    +
                    "('1', '1', 'Service{genericService=CombustionService}', '1', 'None', '0', '0'), " +
                    "('2', '1', 'Service{genericService=DieselService}', '2', 'None', '0', '0'), " +
                    "('3', '1', 'Service{genericService=ElectricService}', '3', 'None', '0', '0'), " +
                    "('4', '1', 'Service{genericService=UniversalService}', '4', 'None', '0', '0')";

            stm.executeUpdate(insertWorkstations);

            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public void clearDB() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stm = conn.createStatement()) {

            String deleteMechanics = "DELETE FROM mechanics";
            stm.executeUpdate(deleteMechanics);

            String deleteVehicles = "DELETE FROM vehicles";
            stm.executeUpdate(deleteVehicles);

            String deleteUsers = "DELETE FROM users";
            stm.executeUpdate(deleteUsers);

            String deleteWorkstations = "DELETE FROM workstations";
            stm.executeUpdate(deleteWorkstations);

            System.out.println("Data cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Error clearing data: " + e.getMessage());
        }
    }

}
