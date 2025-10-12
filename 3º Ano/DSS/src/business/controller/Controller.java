package business.controller;

import business.Exceptions.MechanicAlreadyExistsException;
import business.Exceptions.MechanicOutOfHoursException;
import business.Exceptions.WorkstationEmptyException;
import business.model.Model;

import java.time.LocalTime;
import java.util.List;


public class Controller {
    private final Model m;

    public Controller(Model m) {
        this.m = m;
    }

    public void registerUser(String name, String email, String VATNumber, String address, String telephone, List<String> vehicleFile, List<Integer> vehicleType) {
        this.m.registerUser(name, email, VATNumber, address, telephone, vehicleFile, vehicleType);
    }

    public void addWorkstation() {
        m.addWorkstation();
    }

    public void registerMechanic(String mechanicName, int serviceType, LocalTime startTime, LocalTime endTime,
                                 int workstationID) throws MechanicAlreadyExistsException, MechanicOutOfHoursException {
        m.registerMechanic(mechanicName, serviceType, startTime, endTime, workstationID);
    }

    public void defineWorkstationServices(int choice, int workstationID) {
        m.defineWorkstationServices(choice, workstationID);
    }

    public void defineWorkstationMechanic(int workstationID, int mechanicID) {
        m.defineWorkstationMechanics(workstationID , mechanicID);
    }

    public void selectWorkstation(int choice) {
        m.selectWorkstation(choice);
    }

    public void printWorkstations() {
        m.printWorkstations();
    }

    public void printWorkstation(int choice) {
        m.printWorkstation(choice);
    }

    public void printMechanics() {
        m.printMechanics();
    }

    public void selectMechanic(int mechanicChoice) {
        m.selectMechanic(mechanicChoice);
    }

    public void consultService(int workstationID) throws WorkstationEmptyException {
        m.consultService(workstationID);
    }

    public void modifyServiceStatus(int workstationID) {
        m.modifyServiceStatus(workstationID);
    }

    public void load() {
        m.load();
    }

    public boolean verifyTime(LocalTime start, LocalTime end) {
        return m.verifyTime(start, end);
    }

    public void printClients() {
        m.printClients();
    }

    public void printVehicles(int choice) {
        m.printVehicles(choice);
    }

    public void markServiceForWorkstation(int workstationID, int clientID, int vehicleChoice, int serviceChoice) {
        m.markServiceForWorkstation(workstationID, clientID, vehicleChoice, serviceChoice);
    }

    public void performCheckUp(int workstationID, int clientID, int vehicleChoice, int serviceChoice) {
        m.performCheckUp(workstationID, clientID, vehicleChoice, serviceChoice);
    }

    public void printServices(int workstationID) {
        m.printServices(workstationID);
    }

    public boolean isInvalidShiftHours(LocalTime start, LocalTime end) {
        return m.isInvalidShiftHours(start, end);
    }

    public void addServiceToCar(int workstationID, int clientID, int vehicleChoice, int serviceChoice) {
        m.addServiceToCar(workstationID, clientID, vehicleChoice, serviceChoice);
    }

    public int whereMechanicIs(int mechanicID) {
        return m.whereMechanicIs(mechanicID);
    }

    public void clearWorkstation(int workstationID) {
        m.clearWorkstation(workstationID);
    }
}
