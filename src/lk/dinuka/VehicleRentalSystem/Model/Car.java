package lk.dinuka.VehicleRentalSystem.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Car extends Vehicle {

    private String transmission;
    private boolean hasAirCon;

    public Car(String plateNo, String make, String model, String engineCapacity, BigDecimal dailyCost, String type, String transmission, boolean hasAirCon) {
        super(plateNo, make, model, engineCapacity, dailyCost, type);
        this.transmission = transmission;                 //making sure that this extra info is added when creating a new Car object
        this.hasAirCon = hasAirCon;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public boolean isHasAirCon() {
        return hasAirCon;
    }

    public void setHasAirCon(boolean hasAirCon) {
        this.hasAirCon = hasAirCon;
    }

    @Override
    public String toString() {
        return super.toString() + " {" +
                "transmission='" + transmission + '\'' +
                ", hasAirCon=" + hasAirCon +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return hasAirCon == car.hasAirCon &&
                Objects.equals(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transmission, hasAirCon);
    }
}
