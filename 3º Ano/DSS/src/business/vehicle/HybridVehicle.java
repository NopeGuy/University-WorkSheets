package business.vehicle;

import business.Station.Service;

import java.util.List;

public class HybridVehicle extends ElectricVehicle implements CombustionInterface {

    public HybridVehicle() {
    }

    public HybridVehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        super(id, vehicleFile, servicesNeeded);
    }

    public HybridVehicle(String vehicleFile, List<Service> servicesNeeded) {
        super(vehicleFile, servicesNeeded);
    }

}