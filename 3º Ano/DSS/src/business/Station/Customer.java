package business.Station;

import business.vehicle.Vehicle;

import java.util.List;
import java.util.Objects;

public class Customer extends User {

    private final String VATNumber;
    private final String address;
    private final List<Vehicle> vehicles;
    private final String telephone;

    public Customer(String name, String email, String VATNumber, String address, String telephone,
                    List<Vehicle> vehicles) {
        super(name, email);
        this.VATNumber = VATNumber;
        this.address = address;
        this.telephone = telephone;
        this.vehicles = vehicles;
    }

    public Customer(int id, String name, String email, String VATNumber, String address, String telephone,
                    List<Vehicle> vehicles) {
        super(id, name, email);
        this.VATNumber = VATNumber;
        this.address = address;
        this.telephone = telephone;
        this.vehicles = vehicles;
    }

    public String getVATNumber() {
        return VATNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public Vehicle getVehicle(int vehicleChoice) {
        return this.vehicles.get(vehicleChoice);
    }

    public String getTelephone() {
        return telephone;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Customer customer = (Customer) o;
        return Objects.equals(getVATNumber(), customer.getVATNumber())
                && Objects.equals(getAddress(), customer.getAddress())
                && Objects.equals(getVehicles(), customer.getVehicles())
                && Objects.equals(getTelephone(), customer.getTelephone());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "VATNumber='" + VATNumber + '\'' +
                ", address='" + address + '\'' +
                ", vehicles=" + vehicles +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}