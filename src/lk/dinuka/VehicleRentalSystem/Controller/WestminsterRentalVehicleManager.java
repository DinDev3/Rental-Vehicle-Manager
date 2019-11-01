package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.*;
import lk.dinuka.VehicleRentalSystem.View.GUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private static Scanner scanInput = new Scanner(System.in);

    public static HashMap<String, Vehicle> allPlateNos = new HashMap<>();          //used to check whether the plate No already exists in the system
    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //protected: making sure that customers can't modify the vehicles in the system
    public static HashMap<Vehicle, Schedule> bookedVehicles = new HashMap<>();       //used to record pick up & drop off dates of vehicles

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
    private static boolean hasAirCon;
    private static String type;


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
                addCommonInfo();

                type = "Car";

                System.out.println("Enter the type of transmission:");
                System.out.println(">");
                transmission = scanInput.nextLine();

                System.out.println("Does this car have A/C?");
                System.out.println(">");
                String hasAirC = scanInput.nextLine().toLowerCase();

                do {                                            //check whether this works as expected!!!!!!!!!!!
                    if (hasAirC.equals("y") || hasAirC.equals("yes")) {
                        hasAirCon = true;
                    } else if (hasAirC.equals("n") || hasAirC.equals("no")) {
                        hasAirCon = false;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                    }
                } while (!(hasAirC.equals("y") || hasAirC.equals("yes") || hasAirC.equals("n") || hasAirC.equals("no")));


                Vehicle newCar = new Car(plateNo, make, model, availability, engineCapacity, dailyCost, type, transmission, hasAirCon);

                vehiclesInSystem.add(newCar);       //adding a car into the vehiclesInSystem arrayList
                allPlateNos.put(plateNo, newCar);


                //adding new Car to noSQL database


            } else if (typeSelection == 2) {         //new Motorbike chosen
                addCommonInfo();

                type = "Motorbike";

                System.out.println("Enter start type:");
                System.out.println(">");
                startType = scanInput.nextLine();

                System.out.println("Enter wheel size:");
                System.out.println(">");
                wheelSize = scanInput.nextDouble();
                scanInput.nextLine();           //to consume the rest of the line


                Vehicle newBike = new Motorbike(plateNo, make, model, availability, engineCapacity, dailyCost, type, startType, wheelSize);

                vehiclesInSystem.add(newBike);       //adding a motorbike into the vehiclesInSystem arrayList
                allPlateNos.put(plateNo, newBike);


                //adding new Bike to noSQL database


            }

        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }
    }

    @Override
    public void deleteVehicle() {                  //delete item by entering plate no. of vehicle
        System.out.println("Enter the plate number of the vehicle that u desire to delete:");
        System.out.print(">");              //get plateNo from user to choose vehicle to be deleted
        String searchNo = scanInput.nextLine();

        if (allPlateNos.containsKey(searchNo)) {
            Vehicle vehicleToBeDeleted = findVehicle(searchNo);

            type = vehicleToBeDeleted.getType();
            vehiclesInSystem.remove(vehicleToBeDeleted);
            allPlateNos.remove(searchNo);
            Vehicle.count -= 1;          //decreasing the number of vehicles from the system by one

            //Deleting from noSQL Database
//            DatabaseController.deleteFromDB(searchNo);

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("There are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left in the garage.");

        } else {
            System.out.println("There's no item related to the item ID: " + searchNo);
        }
    }


    @Override
    public void printList() {       //prints list of vehicles in the system

        Collections.sort(vehiclesInSystem);     //sort vehicles alphabetically, according to make


        // print the plate number, the type of vehicle (car/ van/ motorbike).

        String leftAlignFormat = "| %-15s | %-12s |%n";

        System.out.format("+-----------------+--------------+%n");
        System.out.format("|   Item ID       |   Type       |%n");
        System.out.format("+-----------------+--------------+%n");

        for (Vehicle item : vehiclesInSystem) {
            if (item instanceof Car) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Car");
            } else if (item instanceof Motorbike) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Motorbike");
            }
        }
    }

    @Override
    public void save() {        //saves the information of vehicles entered into the system
        //Rewrite the file every time a change is made.


    }

    @Override
    public void viewGUI() {
        GUI.main(null);
    }


//    ---- repeated methods ----

    private static void addCommonInfo() {       //common information related to Car & Motorbike in addVehicle

        System.out.println("\nEnter Plate No:");
        do {
            System.out.print(">");
            plateNo = scanInput.nextLine();
            if (allPlateNos.containsKey(plateNo)) {
                System.out.println("This Plate No exists in the system. Do u want to edit information related to this vehicle?");
                //print information of vehicle

                //ask whether to edit the vehicle information related to this plate no!!!!!!!!!!!!!!
            }
        } while (allPlateNos.containsKey(plateNo));

        System.out.println("\nEnter Make:");
        System.out.print(">");
        make = scanInput.nextLine();

        System.out.println("\nEnter Model:");
        System.out.print(">");
        model = scanInput.nextLine();

        availability = true;        //availability is set to true when vehicle data is entered to the system;

        System.out.println("\nEnter Engine Capacity:");
        System.out.print(">");
        engineCapacity = scanInput.nextLine();

        System.out.println("Enter Daily cost (in $):");
        System.out.print(">$");
        doubleInputValidation();
//        dailyCost = scanInput.nextDouble();                           //get BigDecimal conversion to work!!!!!!!!!!
        scanInput.nextLine();              //to consume the rest of the line

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
