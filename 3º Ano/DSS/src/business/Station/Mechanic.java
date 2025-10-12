package business.Station;

import java.util.Objects;

public class Mechanic {

    private final String name;
    private final Service genericSkill;
    public Shift shift;
    private int id;

    public Mechanic(String name, Service genericSkill, Shift shift) {
        this.name = name;
        this.genericSkill = genericSkill;
        this.shift = shift;
    }

    public Mechanic(int id, String name, Service genericSkill, Shift shift) {
        this.id = id;
        this.name = name;
        this.genericSkill = genericSkill;
        this.shift = shift;
    }

    public Mechanic(Mechanic m) {
        this.id = m.getId();
        this.name = m.getName();
        this.genericSkill = m.getGenericSkill();
        this.shift = m.getShift();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Service getGenericSkill() {
        return genericSkill;
    }

    public Shift getShift() {
        return shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Mechanic mechanic))
            return false;
        return getId() == mechanic.getId() && Objects.equals(getName(), mechanic.getName())
                && Objects.equals(getGenericSkill(), mechanic.getGenericSkill())
                && Objects.equals(getShift(), mechanic.getShift());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGenericSkill(), getShift());
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", genericSkill=" + genericSkill +
                ", shift=" + shift +
                '}';
    }

    public Mechanic clone() {
        return new Mechanic(this);
    }
}
