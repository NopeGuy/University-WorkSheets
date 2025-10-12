package business.Facades;

import business.Station.User;
import business.vehicle.Vehicle;

import java.util.List;
import java.util.Map;

public interface IUsersFacade {
    User getUser(int id);

    void addUser(User oneUser);

    Map<Integer, User> getUserMap();

    int getVehiclesSize(int userID);

    void printVehicles(int userID);

    void printClients();

    Vehicle selectVehicle(int vehicleChoice, int userID);

    Vehicle createVehicle(int vehicleType, String vehicleFile);

    List<Vehicle> addVehiclesToUser(List<String> vehicleFiles, List<Integer> vehicleTypes);

    void registerUser(String name, String email, String VATNumber, String address,
                      String telephone, List<String> vehicleFiles, List<Integer> vehicleTypes);
}
