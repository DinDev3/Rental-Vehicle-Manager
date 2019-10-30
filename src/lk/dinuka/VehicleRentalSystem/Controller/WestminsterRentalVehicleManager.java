package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.RentalVehicleManager;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;
import lk.dinuka.VehicleRentalSystem.View.GUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private static Scanner scanInput = new Scanner(System.in);

    public static HashMap<String, String> allPlateNos = new HashMap<>();          //used to check whether the plate No already exists in the system
    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //protected: making sure that customers can't modify the vehicles in the system
    public static ArrayList<Vehicle> bookedVehicles = new ArrayList<>();

    public static ArrayList<Vehicle> getVehiclesInSystem() {         //accessed in GUI
        return vehiclesInSystem;
    }


    private static String plateNo;
    private static String make;
    private static String model;
    private static boolean availability;
    private static Schedule schedule;
    private static String engineCapacity;
    private static BigDecimal dailyCost;
    private static String startType;
    private static double wheelSize;
    private static String transmission;


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
        GUI.main(null);
    }


//    ---- repeated methods ----

    private static void addCommonInfo() {       //common information related to Car & Motorbike in addVehicle

        System.out.println("\nEnter Item ID:");
        do {
            System.out.print(">");
            plateNo = scanInput.nextLine();
            if (allPlateNos.containsKey(plateNo)) {
                System.out.println("This Plate No exists in the system. Do u want to edit information related to this vehicle?");
                //print information of vehicle

                //ask whether to edit the vehicle information related to this plate no!!!!!!!!!!!!!!1
            }
        } while (allPlateNos.containsKey(plateNo));
    }


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


    private static Vehicle findVehicle(String searchPlateNo) {
        for (Vehicle searchItem : vehiclesInSystem) {
            if (searchItem.getPlateNo().equals(searchPlateNo)) {
                return searchItem;
            }
        }
        return null;
    }


}
