package business.Station.Services;

import business.Station.Service;
import business.Station.enums.TypesOfCombustionServices;

public class CombustionService extends Service {
    private final TypesOfCombustionServices combustionServices;

    public CombustionService(TypesOfCombustionServices combustionServices) {
        this.combustionServices = combustionServices;
    }

    public TypesOfCombustionServices getCombustionServices() {
        return combustionServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombustionService that)) return false;
        return getCombustionServices() == that.getCombustionServices();
    }

    @Override
    public String toString() {
        return "CombustionService{" +
                "combustionServices=" + combustionServices +
                '}';
    }

}