package business.Station.Services;

import business.Station.Service;
import business.Station.enums.TypesOfDieselServices;

public class DieselService extends Service {

    private final TypesOfDieselServices dieselServices;

    public DieselService(TypesOfDieselServices dieselServices) {
        this.dieselServices = dieselServices;
    }

    public TypesOfDieselServices getDieselServices() {
        return dieselServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DieselService that)) return false;
        if (!super.equals(o)) return false;
        return getDieselServices() == that.getDieselServices();
    }

    @Override
    public String toString() {
        return "DieselService{" +
                "dieselServices=" + dieselServices +
                '}';
    }
}