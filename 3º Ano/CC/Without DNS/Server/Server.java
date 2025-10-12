import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {

    private static final int PORT = 9090;
    private static final int BACKLOG = 50;
    private static final String DEFAULT_ADDRESS = "0.0.0.0"; // Default to bind to all network interfaces
    private static volatile boolean isRunning = true;
    private static Map<String, List<String>> clientFilesMap = new HashMap<>();
    private static Map<String, List<String>> clientBlockFilesMap = new HashMap<>();
    private static Map<String, Integer> FileBlockNumber = new HashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG, InetAddress.getByName(getLocalIPAddress()))) {

            String IP = serverSocket.getInetAddress().getHostAddress();
            System.out.println("Server listening on " + IP + " port " + PORT);

            // Start a thread to handle the menu
            Thread menuThread = new Thread(Server::startMenu);
            menuThread.start();

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                // Print Client DNS 
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                System.out.println("Client connected: " + clientIP);

                // Start a new thread to handle the client
                executorService.execute(new ClientHandler(clientSocket, clientFilesMap, clientBlockFilesMap, FileBlockNumber));
            }

            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1. Exit\n");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Exiting the server.");
                        isRunning = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static String getLocalIPAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    if (address instanceof Inet4Address) {
                        return address.getHostAddress();
                    }
                }
            }
        }

        return DEFAULT_ADDRESS; // Default to binding to all network interfaces
    }
}
