package business.Facades;

import business.Station.Service;

import java.util.List;

public interface IServicesFacade {
    Service getGenericService(int choice);

    int getGenericService(Service s);

    List<Service> getUniversalServices();

    List<Service> getDieselServices();

    List<Service> getElectricServices();

    List<Service> getGasolineServices();

    List<Service> getCombustionServices();

    List<Service> getAllServices(Service a);

    Service getRandomService(List<Service> services);

    Service getAllGenericServices(int option);

    boolean isValidServiceChoice(int serviceChoice, List<Service> allServices);

    String extractServiceName(Service service);

    void printServicesFromWorkstation(String services);
}
