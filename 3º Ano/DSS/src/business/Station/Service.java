package business.Station;

import business.Station.Services.*;
import business.Station.enums.*;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private GenericService genericService;

    public Service(GenericService genericService) {
        this.genericService = genericService;
    }

    public Service() {
        this.genericService = null;
    }

    public Service(Service s) {
        this.genericService = s.getGenericService();
    }

    public GenericService getGenericService() {
        if (this instanceof CombustionService) {
            return GenericService.CombustionService;
        }
        if (this instanceof DieselService) {
            return GenericService.DieselService;
        }
        if (this instanceof ElectricService) {
            return GenericService.ElectricService;
        }
        if (this instanceof GasolineService) {
            return GenericService.GasolineService;
        }
        if (this instanceof UniversalService) {
            return GenericService.UniversalService;
        }
        return genericService;
    }

    public void setGenericService(GenericService genericService) {
        this.genericService = genericService;
    }


    public List<Service> getUniversalServiceValues() {
        List<Service> serviceValues = new ArrayList<>();
        for (TypesOfUniversalServices aEnum : TypesOfUniversalServices.values())
            serviceValues.add(new UniversalService(aEnum));
        return serviceValues;
    }

    public List<Service> getCombustionServiceValues() {
        List<Service> serviceValues = new ArrayList<>();
        for (TypesOfCombustionServices aEnum : TypesOfCombustionServices.values())
            serviceValues.add(new CombustionService(aEnum));
        return serviceValues;
    }

    public List<Service> getDieselServiceValues() {
        List<Service> serviceValues = new ArrayList<>();
        for (TypesOfDieselServices aEnum : TypesOfDieselServices.values())
            serviceValues.add(new DieselService(aEnum));
        return serviceValues;
    }

    public List<Service> getElectricServiceValues() {
        List<Service> serviceValues = new ArrayList<>();
        for (TypesOfElectricServices aEnum : TypesOfElectricServices.values())
            serviceValues.add(new ElectricService(aEnum));
        return serviceValues;
    }

    public List<Service> getGasolineServiceValues() {
        List<Service> serviceValues = new ArrayList<>();
        for (TypesOfGasolineServices aEnum : TypesOfGasolineServices.values())
            serviceValues.add(new GasolineService(aEnum));
        return serviceValues;
    }

    @Override
    public String toString() {
        return "Service{" +
                "genericService=" + genericService +
                '}';
    }
}