package business.model;

import business.Exceptions.MechanicAlreadyExistsException;
import business.Exceptions.MechanicOutOfHoursException;
import business.Exceptions.WorkstationEmptyException;
import business.Facades.MechanicsFacade;
import business.Facades.ServicesFacade;
import business.Facades.UsersFacade;
import business.Facades.WorkstationsFacade;
import business.Station.*;
import business.Station.enums.GenericService;
import business.vehicle.Vehicle;
import data.DBManager;

import java.time.LocalTime;
import java.util.List;

public class Model {
    private static final LocalTime startTime = LocalTime.of(9, 0, 0, 00);
    private static final LocalTime endTime = LocalTime.of(19, 0, 0, 00);
    private final MechanicsFacade mechanicsFacade;
    private final ServicesFacade servicesFacade;
    private final WorkstationsFacade workstationsFacade;
    private final UsersFacade usersFacade;

    public Model() {
        this.mechanicsFacade = new MechanicsFacade();
        this.servicesFacade = new ServicesFacade();
        this.workstationsFacade = new WorkstationsFacade();
        this.usersFacade = new UsersFacade();
    }

    public WorkstationsFacade getWorkstationsFacade() {
        return workstationsFacade;
    }

    public void registerMechanic(String name, int serviceType, LocalTime starttime, LocalTime endtime,
                                 int workstationID)
            throws MechanicOutOfHoursException, MechanicAlreadyExistsException {
        try {
            if (verifyTime(starttime, endtime)) {

                Shift shift = new Shift(starttime, endtime);
                Service service = new Service(this.servicesFacade.getGenericService(serviceType));
                Mechanic mechanic = new Mechanic(this.mechanicsFacade.getMechanicsMap().size() + 1, name, service,
                        shift);
                this.mechanicsFacade.addMechanic(mechanic);
                defineWorkstationMechanics(workstationID, mechanic.getId());
            }
        } catch (Exception e) {
            throw new MechanicOutOfHoursException();
        }
    }

    public void defineWorkstationServices(int choice, int workstationID) {
        Workstation workstationToUpdate = this.workstationsFacade.getWorkstation(workstationID);
        Service selectedService = this.servicesFacade.getGenericService(choice);
        workstationToUpdate.setService(selectedService);
        workstationToUpdate.setAvailable(true);
        this.workstationsFacade.getWorkstationMap().put(workstationID, workstationToUpdate);
    }

    public void defineWorkstationMechanics(int workstationID, int mechanicID) {
        Mechanic mechanic = this.mechanicsFacade.getMechanic(mechanicID);
        Service mechanicService = mechanic.getGenericSkill();
        int serviceType = this.servicesFacade.getGenericService(mechanicService);
        if (serviceIsCompatible(serviceType, workstationID)) {
            Workstation selectedWorkstation = this.workstationsFacade.getWorkstationMap().get(workstationID);
            selectedWorkstation.setMechanic(mechanicID);
            this.workstationsFacade.getWorkstationMap().put(workstationID, selectedWorkstation);
        } else {
            System.out.println("No suitable workstations available.");
        }
    }

    public void clearWorkstation(int workstationID) {
        Workstation workstation = this.workstationsFacade.getWorkstation(workstationID);
        workstation.setMechanic(0);
        workstation.setAvailable(true);
        this.workstationsFacade.getWorkstationMap().put(workstationID, workstation);
    }

    private boolean serviceIsCompatible(int serviceType, int workstationID) {
        Workstation w = this.workstationsFacade.getWorkstationMap().get(workstationID);
        Service s = this.servicesFacade.getGenericService(serviceType);
        if (s.getGenericService().equals(w.getService().getGenericService())) {
            return true;
        } else {
            return s.getGenericService().equals(GenericService.CombustionService)
                    && w.getService().getGenericService().equals(GenericService.DieselService) ||
                    s.getGenericService().equals(GenericService.CombustionService)
                            && w.getService().getGenericService().equals(GenericService.GasolineService);
        }
    }

    public void printWorkstation(int choice) {
        Workstation ws = this.workstationsFacade.getWorkstation(choice);

        if (ws != null) {
            System.out.println("Workstation Details for Workstation ID: " + ws.getID());
            System.out.println("Service: " + ws.getService());
            System.out.println("Mechanic ID: " + ws.getMechanic());
            System.out.println("Availability: " + (ws.isAvailable() ? "Available" : "Not Available"));
            Mechanic mechanic = this.mechanicsFacade.mapCopy().get(ws.getMechanic());

            if (mechanic != null) {
                System.out.println("Mechanic Details:");
                System.out.println(mechanic);
            } else {
                System.out.println("Mechanic not found for ID: " + ws.getMechanic());
            }
        } else {
            System.out.println("Workstation not found for ID: " + choice);
        }
    }

    public boolean isInvalidShiftHours(LocalTime start, LocalTime end) {
        return startTime.isBefore(start) || endTime.isAfter(end);
    }

    public void printServices(int workstationID) {
        Workstation workstation = this.workstationsFacade.getWorkstation(workstationID);
        String services = this.servicesFacade.getAllServices(workstation.getService()).toString();

        this.servicesFacade.printServicesFromWorkstation(services);
    }

    public void markServiceForWorkstation(int workstationID, int clientID, int vehicleChoice, int serviceChoice) {
        Workstation workstation = this.getWorkstationsFacade().findWorkstationByMechanic(workstationID);

        List<Service> allServices = this.servicesFacade.getAllServices(workstation.getService());
        Service service = allServices.get(serviceChoice);
        Vehicle vehicle = this.usersFacade.getUser(clientID).getVehicles().get(vehicleChoice - 1);

        if (checkServiceCompatibility(service, vehicle)) {
            String currentServiceName = this.servicesFacade.extractServiceName(allServices.get(serviceChoice));
            workstation.setCurrentService(currentServiceName);
            workstation.setAvailable(false);
            workstation.setCurrentClientID(clientID);
            workstation.setCurrentVehicleID(vehicleChoice);

            this.workstationsFacade.getWorkstationMap().put(workstation.getID(), workstation);
            System.out.println("Service has been set for workstation.");
        } else {
            System.out.println("\nInvalid service choice.");
        }
    }

    public boolean checkServiceCompatibility(Service service2, Vehicle vehicle) {
        GenericService gs = service2.getGenericService();
        int vehicletype = vehicle.getVehicleType();
        switch (gs) {
            case CombustionService:
                if (vehicletype == 1 || vehicletype == 3)
                    return true;
                break;
            case DieselService:
                if (vehicletype == 1 || vehicletype == 4)
                    return true;
                break;
            case ElectricService:
                if (vehicletype == 2 || vehicletype == 4)
                    return true;
                break;
            case GasolineService:
                if (vehicletype == 3 || vehicletype == 4)
                    return true;
                break;
            case UniversalService:
                return true;
            default:
                return false;
        }
        return false;
    }

    public void consultService(int workstationID) throws WorkstationEmptyException {
        try {
            if (!this.workstationsFacade.getWorkstation(workstationID).isAvailable()) {
                Workstation workstation = this.workstationsFacade.getWorkstation(workstationID);
                User user = this.usersFacade.getUser(workstation.getCurrentClientID());
                Vehicle vehicle = user.getVehicles().get(workstation.getCurrentVehicleID() - 1);
                System.out.println("Current service: " + workstation.getCurrentService());
                System.out.println(vehicle.toStringFromWish());
            } else {
                System.out.println("\nWorkstation is empty.");
            }
        } catch (Exception e) {
            throw new WorkstationEmptyException();
        }
    }

    public void modifyServiceStatus(int workstationID) {
        Workstation workstation = workstationsFacade.getWorkstationMap().get(workstationID);

        if (workstation.isAvailable()) {
            System.out.println("Workstation is available. No service to modify.");
        } else {
            int clientID = workstation.getCurrentClientID();
            int vehicleID = workstation.getCurrentVehicleID();

            User user = this.usersFacade.getUser(clientID);
            Vehicle vehicle = user.getVehicles().get(vehicleID - 1);

            String currentService = workstation.getCurrentService();
            Service service = ServicesFacade.valueOf(currentService);
            vehicle.getServicesNeeded().remove(service);
            this.usersFacade.getUserMap().put(clientID, user);
            System.out.println("Service completed.");
            workstation.setAvailable(true);
            workstation.setCurrentService("None");
            workstation.setCurrentClientID(0);
            workstation.setCurrentVehicleID(0);
            this.workstationsFacade.getWorkstationMap().put(workstationID, workstation);
        }
    }

    public void performCheckUp(int workstationID, int userID, int carID, int serviceChoice) {
        Workstation workstation = this.workstationsFacade.getWorkstation(workstationID);
        User user = this.usersFacade.getUser(userID);
        Vehicle vehicle = user.getVehicles().get(carID - 1);
        List<Service> listOfServices = this.servicesFacade.getAllServices(workstation.getService());

        Service service = listOfServices.get(serviceChoice);

        if (!vehicle.getServicesNeeded().contains(service) && checkServiceCompatibility(service, vehicle)) {
            vehicle.getServicesNeeded().add(service);
            this.usersFacade.getUserMap().put(userID, user);
            System.out.println("Service " + service + " is needed.");
        } else {
            System.out.println("Service " + service + " is already in the list or is incompatible.");
        }
    }

    public void addServiceToCar(int workstationID, int userID, int carID, int serviceChoice) {
        Workstation workstation = this.workstationsFacade.getWorkstation(workstationID);
        User user = this.usersFacade.getUser(userID);
        Vehicle vehicle = user.getVehicles().get(carID - 1);
        List<Service> listOfServices = this.servicesFacade.getAllServices(workstation.getService());
        Service service = listOfServices.get(serviceChoice);

        if (!vehicle.getServicesNeeded().contains(service) && checkServiceCompatibility(service, vehicle)) {
            vehicle.getServicesNeeded().add(service);
            this.usersFacade.getUserMap().put(userID, user);
            System.out.println("Service " + service + " was added.");
        } else {
            System.out.println("Service " + service + " is already in the list.");
        }
    }

    public boolean verifyTime(LocalTime start, LocalTime end) {
        if (start.isBefore(startTime) || end.isAfter(endTime) || start.isAfter(end)) {
            System.out.println("Invalid shift hours. Please try again.");
            return false;
        } else
            return true;
    }

    public int whereMechanicIs(int mechanicID) {
        if (this.workstationsFacade.findWorkstationByMechanic(mechanicID) == null) {
            return -1;
        }
        return this.workstationsFacade.findWorkstationByMechanic(mechanicID).getID();
    }

    public void addWorkstation() {
        this.workstationsFacade.addWorkstation();
    }

    public void selectWorkstation(int choice) {
        this.workstationsFacade.selectWorkstation(choice);
    }

    public void printWorkstations() {
        this.workstationsFacade.printWorkstations();
    }

    public void printMechanics() {
        this.mechanicsFacade.printMechanics();
    }

    public void selectMechanic(int mechanicChoice) {
        this.mechanicsFacade.selectMechanic(mechanicChoice);
    }

    public void printClients() {
        this.usersFacade.printClients();
    }

    public void printVehicles(int userID) {
        this.usersFacade.printVehicles(userID);
    }

    public void load() {
        DBManager dbm = new DBManager();
        dbm.start();
    }

    public void registerUser(String name, String email, String vATNumber, String address, String telephone,
                             List<String> vehicleFile, List<Integer> vehicleType) {
        this.usersFacade.registerUser(name, email, vATNumber, address, telephone, vehicleFile, vehicleType);
    }

}