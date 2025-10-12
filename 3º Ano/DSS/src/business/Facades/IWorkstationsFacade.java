package business.Facades;

import business.Station.Workstation;

import java.util.List;
import java.util.Map;

public interface IWorkstationsFacade {
    Map<Integer, Workstation> getWorkstationMap();

    Workstation getWorkstation(Integer workstationID) throws IllegalArgumentException;

    void addWorkstation(Workstation workstation);

    List<Workstation> getWorkstations();

    void printWorkstations();

    Workstation selectWorkstation(int workstationChoice);

    void addWorkstation();

    Workstation findWorkstationByMechanic(int mechanicID);
}
