package business.Station.Services;

import business.Station.Service;
import business.Station.enums.TypesOfGasolineServices;

public class GasolineService extends Service {

    private final TypesOfGasolineServices gasolineServices;

    public GasolineService(TypesOfGasolineServices gasolineServices) {
        this.gasolineServices = gasolineServices;
    }

    public TypesOfGasolineServices getGasolineServices() {
        return gasolineServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GasolineService that)) return false;
        if (!super.equals(o)) return false;
        return getGasolineServices() == that.getGasolineServices();
    }

    @Override
    public String toString() {
        return "GasolineService{" +
                "gasolineServices=" + gasolineServices +
                '}';
    }
}