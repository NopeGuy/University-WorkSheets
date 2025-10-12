package business.Station.Services;

import business.Station.Service;
import business.Station.enums.TypesOfUniversalServices;

public class UniversalService extends Service {

    private final TypesOfUniversalServices universalServices;

    public UniversalService(TypesOfUniversalServices universalServices) {
        this.universalServices = universalServices;
    }

    public TypesOfUniversalServices getUniversalServices() {
        return universalServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniversalService that)) return false;
        return getUniversalServices() == that.getUniversalServices();
    }

    @Override
    public String toString() {
        return "UniversalService{" +
                "universalServices=" + universalServices +
                '}';
    }

}