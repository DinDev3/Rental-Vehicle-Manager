package lk.dinuka.VehicleRentalSystem.Model;

public abstract class Vehicle {
    private String plateNo;
    private String make;
    private String model;
    private boolean availability;
    private Schedule schedule;
    private String engineCapacity;
    private String fuelType;

    public static int count = 0;

    public Vehicle(String plateNo, String make, String model, boolean availability, Schedule schedule, String engineCapacity, String fuelType) {
        this.plateNo = plateNo;
        this.make = make;
        this.model = model;
        this.availability = availability;
        this.schedule = schedule;
        this.engineCapacity = engineCapacity;
        this.fuelType = fuelType;
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
                ", fuelType='" + fuelType + '\'' +
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

    public String getFuelType() {
        return fuelType;
    }
}
