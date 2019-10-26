package lk.dinuka.VehicleRentalSystem.Model;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Vehicle implements Comparable<Vehicle>{
    private String plateNo;
    private String make;
    private String model;
    private boolean availability;
    private Schedule schedule;
    private String engineCapacity;
    private BigDecimal dailyCost;

    public static int count = 0;

    public Vehicle(String plateNo, String make, String model, boolean availability, Schedule schedule, String engineCapacity, BigDecimal dailyCost) {
        this.plateNo = plateNo;
        this.make = make;
        this.model = model;
        this.availability = availability;
        this.schedule = schedule;
        this.engineCapacity = engineCapacity;
        this.dailyCost = dailyCost;

        count++;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plateNo='" + plateNo + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", availability=" + availability +
                ", schedule=" + schedule +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", dailyCost=" + dailyCost +
                '}';
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailability() {
        return availability;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public BigDecimal getDailyCost() {
        return dailyCost;
    }

    public BigDecimal calculateRent(){
        return dailyCost;       //calculate dailycost*no of days and return!!!!!!!!!!!!!!!
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return availability == vehicle.availability &&
                Objects.equals(plateNo, vehicle.plateNo) &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(schedule, vehicle.schedule) &&
                Objects.equals(engineCapacity, vehicle.engineCapacity) &&
                Objects.equals(dailyCost, vehicle.dailyCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNo, make, model, availability, schedule, engineCapacity, dailyCost);
    }

    @Override
    public int compareTo(Vehicle obj) {
        return this.make.compareTo(obj.getMake());      //used for sorting vehicle alphabetically according to make
    }
}
