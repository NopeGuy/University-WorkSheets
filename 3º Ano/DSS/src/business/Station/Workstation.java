package business.Station;

import business.Station.enums.GenericService;

import java.util.Objects;

public class Workstation {
    private final int id;
    private Service service;
    private int mechanicID;
    private boolean isAvailable;
    private String currentService;
    private int currentClientID;
    private int currentVehicleID;

    public Workstation() {
        this.id = -1;
        this.service = new Service(GenericService.NoService);
        this.mechanicID = 0;
        this.isAvailable = true;
        this.currentService = "None";
        this.currentClientID = 0;
        this.currentVehicleID = 0;
    }

    public Workstation(Service service, int mechanicID, int id, boolean isAvailable, String currentService,
                       int currentClientID, int currentVehicleID) {
        this.service = service;
        this.mechanicID = mechanicID;
        this.id = id;
        this.isAvailable = isAvailable;
        this.currentService = currentService;
        this.currentClientID = currentClientID;
        this.currentVehicleID = currentVehicleID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public int getMechanic() {
        return this.mechanicID;
    }

    public void setMechanic(int mechanicID) {
        this.mechanicID = mechanicID;
    }

    public int getID() {
        return id;
    }

    public String getCurrentService() {
        return currentService;
    }

    public void setCurrentService(String aService) {
        this.currentService = aService;
    }

    public int getCurrentClientID() {
        return currentClientID;
    }

    public void setCurrentClientID(int currentClientID) {
        this.currentClientID = currentClientID;
    }

    public int getCurrentVehicleID() {
        return currentVehicleID;
    }

    public void setCurrentVehicleID(int currentVehicleID) {
        this.currentVehicleID = currentVehicleID;
    }

    @Override
    public String toString() {
        return "Workstation{" +
                "service=" + service +
                ", mechanic=" + mechanicID +
                ", id=" + id +
                ", isAvailable=" + isAvailable +
                ", currentService='" + currentService + '\'' +
                ", currentClientID=" + currentClientID +
                ", currentVehicleID=" + currentVehicleID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Workstation that))
            return false;
        return getId() == that.getId() && isAvailable() == that.isAvailable()
                && Objects.equals(getService(), that.getService()) && Objects.equals(getMechanic(), that.getMechanic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getService(), getMechanic(), getId(), isAvailable());
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}
