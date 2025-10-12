package business.vehicle;

import business.Station.Service;

import java.util.List;

public class GasolineVehicle extends CombustionVehicle implements CombustionInterface {
    public GasolineVehicle() {
    }


    public GasolineVehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        super(id, vehicleFile, servicesNeeded);
    }

    public GasolineVehicle(String vehicleFile, List<Service> servicesNeeded) {
        super(vehicleFile, servicesNeeded);
    }

}