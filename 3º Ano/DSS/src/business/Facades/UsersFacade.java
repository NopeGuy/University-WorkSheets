package business.Facades;

import business.Station.Customer;
import business.Station.User;
import business.vehicle.*;
import data.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersFacade implements IUsersFacade {
    Map<Integer, User> userMap;

    public UsersFacade() {
        this.userMap = UserDAO.getInstance();
    }

    public User getUser(int id) {
        return this.userMap.get(id);
    }

    public void addUser(User oneUser) throws NullPointerException {
        if (oneUser == null) {
            new NullPointerException();
        }
        ((UserDAO) this.userMap).putnew(this.getUserMap().size() + 1, oneUser);
    }

    public Map<Integer, User> getUserMap() {
        return this.userMap;
    }

    public int getVehiclesSize(int userID) {
        return this.getUser(userID).getVehicles().size();
    }

    public void printVehicles(int userID) {
        System.out.println(this.getUserMap().get(userID).getVehicles().toString());
        for (int i = 1; i <= this.getVehiclesSize(userID); i++) {
            System.out.println("Vehicle ID: " + (i));
        }
    }

    public void printClients() {
        for (int i = 0; i < this.getUserMap().size(); i++) {
            System.out.println("Client ID: " + (i + 1));
        }
    }

    public Vehicle selectVehicle(int vehicleChoice, int userID) {
        if (vehicleChoice > -1 && vehicleChoice <= this.getUser(userID).getVehicles().size()) {
            return this.getUser(userID).getVehicle(vehicleChoice);
        } else {
            System.out.println("Invalid workstation choice. Returning the first workstation.");
        }
        return null;
    }

    public Vehicle createVehicle(int vehicleType, String vehicleFile) {
        switch (vehicleType) {
            case 1:
                return new DieselVehicle(vehicleFile, new ArrayList<>());
            case 2:
                return new ElectricVehicle(vehicleFile, new ArrayList<>());
            case 3:
                return new GasolineVehicle(vehicleFile, new ArrayList<>());
            case 4:
                return new HybridVehicle(vehicleFile, new ArrayList<>());
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
    }

    public List<Vehicle> addVehiclesToUser(List<String> vehicleFiles, List<Integer> vehicleTypes) {
        List<Vehicle> lv = new ArrayList<>();
        for (int i = 0; i < vehicleFiles.size(); i++) {
            String vehicleFile = vehicleFiles.get(i);
            int vehicleType = vehicleTypes.get(i);
            Vehicle vehicle = this.createVehicle(vehicleType, vehicleFile);
            lv.add(vehicle);
        }

        return lv;
    }


    public void registerUser(String name, String email, String VATNumber, String address,
                             String telephone, List<String> vehicleFiles, List<Integer> vehicleTypes)
            throws NullPointerException {
        List<Vehicle> vehicles = this.addVehiclesToUser(vehicleFiles, vehicleTypes);
        User u = new Customer(name, email, VATNumber, address, telephone, vehicles);
        this.addUser(u);
    }


}
