package business.Facades;

import business.Exceptions.MechanicAlreadyExistsException;
import business.Station.Mechanic;

import java.util.Map;

public interface IMechanicsFacade {
    Mechanic getMechanic(int mechanicID) throws NullPointerException;

    void addMechanic(Mechanic mechanic) throws MechanicAlreadyExistsException;

    Map<Integer, Mechanic> mapCopy();

    Map<Integer, Mechanic> getMechanicsMap();

    void printMechanics();

    Mechanic selectMechanic(int mechanicChoice);
}
