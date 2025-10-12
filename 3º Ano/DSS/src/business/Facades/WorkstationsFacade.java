package business.Facades;

import business.Station.Workstation;
import data.WorkstationDAO;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkstationsFacade implements IWorkstationsFacade {
    private final Map<Integer, Workstation> workstationMap;

    public WorkstationsFacade() {
        this.workstationMap = WorkstationDAO.getInstance();
    }

    public Map<Integer, Workstation> getWorkstationMap() {
        return workstationMap;
    }

    public Workstation getWorkstation(Integer workstationID) throws IllegalArgumentException {
        return this.workstationMap.get(workstationID);
    }

    public void addWorkstation(Workstation workstation) {
        workstationMap.put(this.workstationMap.size() + 1, new Workstation());
    }

    public List<Workstation> getWorkstations() {
        List<Workstation> workstations = new LinkedList<Workstation>();
        for (Integer key : this.workstationMap.keySet()) {
            Workstation value = this.workstationMap.get(key);
            workstations.add(value);
        }
        return workstations;
    }

    public void printWorkstations() {
        for (int i = 0; i < this.getWorkstationMap().size(); i++)
            System.out.println("Workstation ID: " + (i + 1));
    }

    public Workstation selectWorkstation(int workstationChoice) {
        if (workstationChoice > -1 && workstationChoice <= this.getWorkstationMap().size()) {
            return this.getWorkstation(workstationChoice);
        } else {
            System.out.println("Invalid workstation choice. Returning the first workstation.");
            return this.getWorkstation(1);
        }
    }

    public void addWorkstation() {
        this.addWorkstation(new Workstation());
    }

    public Workstation findWorkstationByMechanic(int mechanicID) {
        for (Workstation w : this.getWorkstationMap().values()) {
            if (w.getMechanic() == mechanicID) {
                return w;
            }
        }
        return null;
    }
}
