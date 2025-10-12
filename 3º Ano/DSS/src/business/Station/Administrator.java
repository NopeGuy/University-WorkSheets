package business.Station;

import business.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    public Administrator(String name, String email) {
        super(name, email);
    }

    @Override
    public List<Vehicle> getVehicles() {
        return new ArrayList<>();
    }

    @Override
    public Vehicle getVehicle(int vehicleChoice) {
        return null;
    }

    @Override
    public String getVATNumber() {
        throw new UnsupportedOperationException("Unimplemented method 'getVATNumber'");
    }

    @Override
    public String getAddress() {
        throw new UnsupportedOperationException("Unimplemented method 'getAddress'");
    }

    @Override
    public String getTelephone() {
        throw new UnsupportedOperationException("Unimplemented method 'getTelephone'");
    }


}