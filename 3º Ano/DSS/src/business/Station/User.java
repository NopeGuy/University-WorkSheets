package business.Station;

import business.vehicle.Vehicle;

import java.util.List;
import java.util.Objects;

public abstract class User {
    private final String email;
    private final String name;
    private int id;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User user))
            return false;
        return getId() == user.getId() && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public abstract List<Vehicle> getVehicles();

    public abstract Vehicle getVehicle(int vehicleChoice);

    public abstract String getVATNumber();

    public abstract String getAddress();

    public abstract String getTelephone();

}