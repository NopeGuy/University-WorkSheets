package business.vehicle;

import business.Station.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Vehicle {

    private static int nextId = 1;
    private final int id;
    private final List<Service> servicesNeeded;
    private String vehicleFile;

    public Vehicle() {
        this.vehicleFile = "No Vehicle File";
        this.servicesNeeded = new ArrayList<Service>();
        this.id = nextId++;
    }

    public Vehicle(String vehicleFile, List<Service> servicesNeeded) {
        this.vehicleFile = vehicleFile;
        this.id = nextId++;
        this.servicesNeeded = servicesNeeded;
    }

    public Vehicle(int id, String vehicleFile, List<Service> servicesNeeded) {
        this.vehicleFile = vehicleFile;
        this.id = id;
        this.servicesNeeded = servicesNeeded;
    }

    public String getVehicleFile() {
        return vehicleFile;
    }

    public int getId() {
        return id;
    }

    public List<Service> getServicesNeeded() {
        return servicesNeeded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vehicle vehicle))
            return false;
        return getId() == vehicle.getId() && Objects.equals(getVehicleFile(), vehicle.getVehicleFile())
                && Objects.equals(getServicesNeeded(), vehicle.getServicesNeeded());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleFile='" + vehicleFile + '\'' +
                ", id=" + id +
                ", servicesNeeded=" + servicesNeeded +
                ", vehicleType=" + this.getVehicleTypeString(this.getVehicleType()) +
                '}';
    }

    public String toStringFromWish() {
        return "Vehicle Name = " + vehicleFile + '\'' +
                ", id = " + id +
                ", Services Needed = " + servicesNeeded +
                ", Vehicle Type = " + this.getVehicleTypeString(this.getVehicleType()) +
                '}';
    }

    public int getVehicleType() {
        if (this.getClass() == DieselVehicle.class) {
            return 1;
        } else if (this.getClass() == ElectricVehicle.class) {
            return 2;
        } else if (this.getClass() == GasolineVehicle.class) {
            return 3;
        } else if (this.getClass() == HybridVehicle.class) {
            return 4;
        } else {
            return 0;
        }
    }

    public String getVehicleTypeString(int choice) {
        switch (choice) {
            case 1:
                return "DieselVehicle";
            case 2:
                return "ElectricVehicle";
            case 3:
                return "GasolineVehicle";
            case 4:
                return "HybridVehicle";
            default:
                break;
        }
        return "bruh";
    }
}
