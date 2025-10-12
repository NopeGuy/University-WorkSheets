package business.Station.Services;

import business.Station.Service;
import business.Station.enums.TypesOfElectricServices;

public class ElectricService extends Service {

    private final TypesOfElectricServices electricServices;

    public ElectricService(TypesOfElectricServices electricServices) {
        this.electricServices = electricServices;
    }

    public TypesOfElectricServices getElectricServices() {
        return electricServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricService that)) return false;
        return getElectricServices() == that.getElectricServices();
    }

    @Override
    public String toString() {
        return "ElectricService{" +
                "electricServices=" + electricServices +
                '}';
    }
}