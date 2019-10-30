package lk.dinuka.VehicleRentalSystem.Model;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String plateNo;
    private String make;
    private String model;
    private boolean availability;
    private String engineCapacity;
    private BigDecimal dailyCost;
    private String type;
    private Schedule schedule;                          //Schedule is added when booking!!!!!!!!!!!!!!!!!!

    public static int count = 0;

    public Vehicle(String plateNo, String make, String model, boolean availability, String engineCapacity, BigDecimal dailyCost, String type) {
        this.plateNo = plateNo;
        this.make = make;
        this.model = model;
        this.availability = availability;
        this.engineCapacity = engineCapacity;
        this.dailyCost = dailyCost;
        this.type = type;

        count++;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plateNo='" + plateNo + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", availability=" + availability +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", dailyCost=" + dailyCost +
                ", type='" + type + '\'' +
                ", schedule=" + schedule +
                '}';
    }

    public static int getCount() {
        return count;
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

    public String getType() {
        return type;
    }

    public BigDecimal getCalculatedRent() {
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
