package business.Facades;

import business.Exceptions.MechanicAlreadyExistsException;
import business.Station.Mechanic;
import data.MechanicDAO;

import java.util.HashMap;
import java.util.Map;

public class MechanicsFacade implements IMechanicsFacade {
    Map<Integer, Mechanic> mechanicMap;

    public MechanicsFacade() {
        this.mechanicMap = MechanicDAO.getInstance();
    }

    public Mechanic getMechanic(int mechanicID) throws NullPointerException {
        if (this.mechanicMap.containsKey(mechanicID) || mechanicID == 0) {
            return this.mechanicMap.get(mechanicID);
        } else {
            throw new NullPointerException();
        }
    }

    public void addMechanic(Mechanic mechanic) throws MechanicAlreadyExistsException {
        if (this.mechanicMap.containsKey(mechanic.getId()))
            throw new MechanicAlreadyExistsException();
        this.mechanicMap.put(this.mechanicMap.size() + 1, mechanic.clone());
    }

    public Map<Integer, Mechanic> mapCopy() {
        Map<Integer, Mechanic> copy = new HashMap<>();
        for (Map.Entry<Integer, Mechanic> entry : mechanicMap.entrySet()) {
            Integer id = entry.getKey();
            Mechanic Mechanic = entry.getValue();
            copy.put(id, Mechanic.clone());
        }
        return copy;
    }

    public Map<Integer, Mechanic> getMechanicsMap() {
        return this.mechanicMap;
    }


    public void printMechanics() {
        for (int i = 0; i < this.getMechanicsMap().size(); i++) {
            System.out.println("Mechanic ID: " + (i + 1));
            Mechanic mechanic = this.getMechanic(i + 1);
            mechanic.toString();
        }
    }

    public Mechanic selectMechanic(int mechanicChoice) {
        if (mechanicChoice > -1 && mechanicChoice <= this.getMechanicsMap().size()) {
            return this.getMechanic(mechanicChoice);
        } else {
            System.out.println("Invalid mecahnic choice. Returning the first mechanic.");
            return this.getMechanic(1);
        }
    }


}
