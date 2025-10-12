import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import Client.Mediator;

import CPackage.*;

public class Client {

    private static final String SERVER_ADDRESS = "10.4.4.1"; // Server IP address
    private static final int SERVER_PORT = 9090; // Server port

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Semaphore requestSemaphore = new Semaphore(0);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream()) {

            System.out.println("Connected to server.");

            // Get the client IP
            String clientIp = socket.getLocalAddress().getHostAddress();
            clientIp = GenericMethods.transformToFullIP(clientIp);
            System.out.println("Client IP: " + clientIp);

            // Create a thread for the Mediator functionality
            Thread mediatorThread = new Thread(new Mediator(inputStream, requestSemaphore));
            mediatorThread.start(); // Start the thread

            // Start a loop to allow the user to choose options
            while (true) {                
                FileMethods.fragmentAndSendInfo(outputStream);
                System.out.println("______________________________________________________\n");
                System.out.println(
                    "\n__________________"+
                       "       Menu:      "+
                       "__________________\n"
                    );
                System.out.println("1. Ask for a file location");
                System.out.println("2. Download a file");
                System.out.println("3. Exit");
                System.out.println("______________________________________________________\n");

                String message;
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.println("Enter the file name:");
                    String file = scanner.nextLine();
                    message = "4" + ";" + file + "$";
                    byte[] userRequestBytes = message.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(userRequestBytes);
                    outputStream.flush();
                } else if (choice == 2) {
                    // Add code for file download option
                    System.out.println("Enter the file name:");
                    String file = scanner.nextLine();
                    message = "3" + ";" + file + "$";
                    byte[] userRequestBytes = message.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(userRequestBytes);
                    outputStream.flush();
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
                requestSemaphore.acquire();
                // Clear the server info so new one can be sent
                message = "5" + ";" + "$";
                byte[] userRequestBytes = message.getBytes(StandardCharsets.UTF_8);
                outputStream.write(userRequestBytes);

            }
            System.out.println("Closing connection.");
        } catch (IOException e) {
            System.err.println("Error: Server is not reachable.");
        } finally {
            scanner.close();
        }
    }
}
