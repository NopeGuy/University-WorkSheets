package business.vehicle;

import business.Station.Service;

import java.util.List;

public class DieselVehicle extends CombustionVehicle implements CombustionInterface {

    public DieselVehicle() {
    }

    public DieselVehicle(String vehicleFile, List<Service> servicesNeeded) {
        super(vehicleFile, servicesNeeded);
    }

    public DieselVehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        super(id, vehicleFile, servicesNeeded);
    }

}