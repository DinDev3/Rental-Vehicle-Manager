package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.RentalVehicleManager;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private static Scanner scanInput = new Scanner(System.in);

    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //making sure that customers can't modify the vehicles in the system
    public static ArrayList<Vehicle> bookedVehicles = new ArrayList<>();

    public static ArrayList<Vehicle> getVehiclesInSystem() {         //accessed in GUI
        return vehiclesInSystem;
    }


    @Override
    public void addVehicle(Vehicle newVehicle) {

    }

    @Override
    public void deleteVehicle(Vehicle delVehicle) {

    }

    @Override
    public void printList() {

    }

    @Override
    public void save() {

    }

    @Override
    public void viewGUI() {

    }
}
