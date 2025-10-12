package business.vehicle;

import business.Station.Service;

import java.util.List;

public abstract class CombustionVehicle extends Vehicle {
    public CombustionVehicle() {
    }

    public CombustionVehicle(String vehicleFile, List<Service> servicesNeeded) {
        super(vehicleFile, servicesNeeded);
    }

    public CombustionVehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        super(id, vehicleFile, servicesNeeded);
    }
}
