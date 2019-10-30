package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.RentalVehicleManager;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private static Scanner scanInput = new Scanner(System.in);

    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //protected: making sure that customers can't modify the vehicles in the system
    public static ArrayList<Vehicle> bookedVehicles = new ArrayList<>();

    public static ArrayList<Vehicle> getVehiclesInSystem() {         //accessed in GUI
        return vehiclesInSystem;
    }


    @Override
    public void addVehicle() {

        if (Vehicle.getCount() <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots

            System.out.println("\nChoose the type of Vehicle to be added:");
            System.out.println("1)Car\n2)Motorbike");
            System.out.print(">");
            intInputValidation();
            int typeSelection = scanInput.nextInt();
            scanInput.nextLine();              //to consume the rest of the line

            if (typeSelection == 1) {       //new Car chosen


            } else if (typeSelection == 2) {         //new Motorbike chosen


            }

        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }
    }

    @Override
    public void deleteVehicle() {

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


//    ---- repeated methods ----


    private static void intInputValidation() {                     //validating integer input

        while (!scanInput.hasNextInt()) {
            System.out.println("Only integer numbers are allowed! Please provide a valid input");              //error handling message for characters other than integers
            scanInput.next();                                                     //removing incorrect input entered
        }
    }

    private static void doubleInputValidation() {                     //validating double input

        while (!scanInput.hasNextDouble()) {
            System.out.println("Only numbers are allowed! Please provide a valid input");              //error handling message for characters other than integers
            scanInput.next();                                                     //removing incorrect input entered
        }
    }


}
