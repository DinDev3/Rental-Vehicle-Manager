package lk.dinuka.VehicleRentalSystem.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Motorbike extends Vehicle {

    private String startType;
    private double wheelSize;

    public Motorbike(String plateNo, String make, String model, boolean availability, String engineCapacity, BigDecimal dailyCost, String type, String startType, double wheelSize) {
        super(plateNo, make, model, availability, engineCapacity, dailyCost,type);
        this.startType = startType;                 //making sure that this extra info is added when creating a new Motorbike object
        this.wheelSize = wheelSize;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public double getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(double wheelSize) {
        this.wheelSize = wheelSize;
    }

    @Override
    public String toString() {
        return super.toString() + " " +
                "startType='" + startType + '\'' +
                ", wheelSize=" + wheelSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Motorbike motorbike = (Motorbike) o;
        return Double.compare(motorbike.wheelSize, wheelSize) == 0 &&
                Objects.equals(startType, motorbike.startType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startType, wheelSize);
    }
}
