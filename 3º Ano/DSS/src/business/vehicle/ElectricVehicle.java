package business.vehicle;

import business.Station.Service;

import java.util.List;

public class ElectricVehicle extends Vehicle {
    public ElectricVehicle() {
    }

    public ElectricVehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        super(id, vehicleFile, servicesNeeded);
    }

    public ElectricVehicle(String vehicleFile, List<Service> servicesNeeded) {
        super(vehicleFile, servicesNeeded);
    }
}