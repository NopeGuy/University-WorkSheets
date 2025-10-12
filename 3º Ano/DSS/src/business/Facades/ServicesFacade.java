package business.Facades;

import business.Station.Service;
import business.Station.enums.GenericService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ServicesFacade implements IServicesFacade {
    private final Service service;

    public ServicesFacade() {
        this.service = new Service();
    }


    public static Service valueOf(String serviceString) {
        if (serviceString == null || serviceString.isEmpty()) {
            return new Service(GenericService.NoService);
        }

        try {
            return Arrays.stream(GenericService.values())
                    .filter(genericService -> serviceString.contains(genericService.name()))
                    .findFirst()
                    .map(Service::new)
                    .orElseGet(() -> new Service(GenericService.NoService));
        } catch (IllegalArgumentException e) {
            return new Service(GenericService.NoService);
        }
    }

    public static List<Service> parseServices(String servicesString) {
        if (servicesString == null || servicesString.isEmpty()) {
            return List.of(new Service(GenericService.NoService));
        }

        String[] serviceStrings = servicesString.split(",");
        List<Service> services = Arrays.stream(serviceStrings)
                .map(ServicesFacade::valueOf)
                .collect(Collectors.toList());

        return services;
    }

    public Service getGenericService(int choice) {
        Service s = new Service();
        switch (choice) {
            case 0:
                s.setGenericService(GenericService.UniversalService);
                break;
            case 1:
                s.setGenericService(GenericService.CombustionService);
                break;
            case 2:
                s.setGenericService(GenericService.DieselService);
                break;
            case 3:
                s.setGenericService(GenericService.ElectricService);
                break;
            case 4:
                s.setGenericService(GenericService.GasolineService);
                break;
        }
        return s;
    }

    public int getGenericService(Service s) {
        GenericService gs = s.getGenericService();
        return switch (gs) {
            case UniversalService -> 0;
            case CombustionService -> 1;
            case DieselService -> 2;
            case ElectricService -> 3;
            case GasolineService -> 4;
            default -> throw new IllegalArgumentException("Unexpected value: " + gs);
        };
    }

    public List<Service> getUniversalServices() {
        return service.getUniversalServiceValues();
    }

    public List<Service> getDieselServices() {
        return service.getDieselServiceValues();
    }

    public List<Service> getElectricServices() {
        return service.getElectricServiceValues();
    }

    public List<Service> getGasolineServices() {
        return service.getGasolineServiceValues();
    }

    public List<Service> getCombustionServices() {
        return service.getCombustionServiceValues();
    }

    public List<Service> getAllServices(Service a) {
        GenericService gs = a.getGenericService();

        return switch (gs) {
            case UniversalService -> getUniversalServices();
            case CombustionService -> getCombustionServices();
            case DieselService -> getDieselServices();
            case ElectricService -> getElectricServices();
            case GasolineService -> getGasolineServices();
            default -> throw new IllegalArgumentException("Unexpected value: " + gs);
        };
    }

    public Service getRandomService(List<Service> services) {
        if (services == null || services.isEmpty()) {
            throw new IllegalArgumentException("Service list is empty or null");
        }

        Random random = new Random();
        return services.get(random.nextInt(services.size()));
    }

    public Service getAllGenericServices(int option) {
        return this.getGenericService(option);
    }

    public boolean isValidServiceChoice(int serviceChoice, List<Service> allServices) {
        return serviceChoice >= 0 && serviceChoice < allServices.size();
    }

    public String extractServiceName(Service service) {
        Pattern currentServicePattern = Pattern.compile("=([^,}]+)");
        Matcher currentServiceMatcher = currentServicePattern.matcher(service.toString());

        return currentServiceMatcher.find() ? currentServiceMatcher.group(1).trim() : "";
    }


    public void printServicesFromWorkstation(String services) {
        Pattern pattern = Pattern.compile("=([^,}]+)");
        Matcher matcher = pattern.matcher(services);

        int i = 0;
        while (matcher.find()) {
            String serviceName = matcher.group(1).trim();
            System.out.println("Service " + i + " : " + serviceName);
            i++;
        }
    }
}
