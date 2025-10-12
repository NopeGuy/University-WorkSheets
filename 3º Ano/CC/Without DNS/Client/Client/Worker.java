package Client;
import CPackage.*;

import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Worker implements Runnable {
    private byte[] data;
    private static long tripTime;
    byte[] hash;
    String ip, filePath;
    static Boolean success = true;
    static Boolean connection = false;

    public Worker(byte[] buffer) {
        this.data = buffer;
    }

    @Override
    public void run() {
        String request = new String(data, 0, 1, StandardCharsets.UTF_8);

        switch (request) {
            case "1":
                success = UDPMethods.parseFileReceiveRequest(data);
                setSuccess(success);
                break;
            case "2":
                UDPMethods.parseFileSendRequest(data);
                break;
            case "3":
                try {
                    UDPMethods.RTTRequest(data);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                break;
            case "4":
                tripTime = UDPMethods.RTTResponse(data);
                setTripTime(tripTime);
                setConnection(true);
                break;
            default:
                break;
        }
    }

    // Method to set the tripTime field
    public void setTripTime(long tripTime) {
        Worker.tripTime = tripTime;
    }

    // Method to get the tripTime value
    public static long getTripTime() {
        return tripTime;
    }

    // Method to set the success field
    public static void setSuccess(Boolean success) {
        Worker.success = success;
    }

    // Method to get the success value
    public static Boolean getSuccess() {
        return success;
    }

    // Method to set the connection field
    public static void setConnection(Boolean connection) {
        Worker.connection = connection;
    }

    // Method to get the connection value
    public static Boolean getConnection() {
        return connection;
    }
}
