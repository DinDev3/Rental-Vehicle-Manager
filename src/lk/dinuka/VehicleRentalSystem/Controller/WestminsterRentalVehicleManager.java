package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.*;
import lk.dinuka.VehicleRentalSystem.View.GUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private static Scanner scanInput = new Scanner(System.in);

    public static HashMap<String, Vehicle> allPlateNos = new HashMap<>();          //used to check whether the plate No already exists in the system
    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //protected: making sure that customers can't modify the vehicles in the system
    public static HashMap<String, Schedule> bookedVehicles = new HashMap<>();       //used to record pick up & drop off dates of vehicles   (plateNo, Schedule)

    public static ArrayList<Vehicle> getVehiclesInSystem() {         //accessed in GUI
        return vehiclesInSystem;
    }


    private static String plateNo;
    private static String make;
    private static String model;
    private static boolean availability;
    private static Schedule schedule;           //used in GUI controller, when booking is made??? (Java/ Angular??)
    private static String engineCapacity;
    private static double dailyCostD;
    private static BigDecimal dailyCostBigD;
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

                System.out.println("\nEnter the type of transmission:");
                System.out.println(">");
                transmission = scanInput.nextLine();


                System.out.println("\nDoes this car have A/C?");
                System.out.println(">");

                String hasAirC;

                do {                                            //check whether this works as expected!!!!!!!!!!!
                    hasAirC = scanInput.nextLine().toLowerCase();
                    if (hasAirC.equals("y") || hasAirC.equals("yes")) {
                        hasAirCon = true;
                    } else if (hasAirC.equals("n") || hasAirC.equals("no")) {
                        hasAirCon = false;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                    }
                } while (!(hasAirC.equals("y") || hasAirC.equals("yes") || hasAirC.equals("n") || hasAirC.equals("no")));


                Vehicle newCar = new Car(plateNo, make, model, availability, engineCapacity, dailyCostBigD, type, transmission, hasAirCon);

                vehiclesInSystem.add(newCar);       //adding a car into the vehiclesInSystem arrayList
                allPlateNos.put(plateNo, newCar);


                //adding new Car to noSQL database
                DatabaseController.addToSystemDB(plateNo, make, model, availability, engineCapacity, dailyCostD, type, transmission, hasAirCon);

                System.out.println(newCar);        //displaying added vehicle

            } else if (typeSelection == 2) {         //new Motorbike chosen
                addCommonInfo();

                type = "Motorbike";

                System.out.println("\nEnter start type:");
                System.out.print(">");
                startType = scanInput.nextLine();

                System.out.println("\nEnter wheel size:");
                System.out.print(">");
                wheelSize = scanInput.nextDouble();
                scanInput.nextLine();           //to consume the rest of the line


                Vehicle newBike = new Motorbike(plateNo, make, model, availability, engineCapacity, dailyCostBigD, type, startType, wheelSize);

                vehiclesInSystem.add(newBike);       //adding a motorbike into the vehiclesInSystem arrayList
                allPlateNos.put(plateNo, newBike);


                //adding new Bike to noSQL database
                DatabaseController.addToSystemDB(plateNo, make, model, availability, engineCapacity, dailyCostD, type, startType, wheelSize);

                System.out.println(newBike);        //displaying added vehicle
            }

            System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");

//            save();     //save changes to file??

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

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted:"+ vehicleToBeDeleted.toString());      //displaying information of deleted vehicle

            vehiclesInSystem.remove(vehicleToBeDeleted);
            allPlateNos.remove(searchNo);
            Vehicle.count -= 1;          //decreasing the number of vehicles from the system by one

            //Deleting from noSQL Database
            DatabaseController.deleteFromSystemDB(searchNo);

            System.out.println("There are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left in the garage.");

//            save();     //save changes to file??

        } else {
            System.out.println("There's no item related to the item ID: " + searchNo);
        }
    }


    @Override
    public void printList() {       //prints list of vehicles in the system

        Collections.sort(vehiclesInSystem);     //sort vehicles alphabetically, according to make


        // print the plate number, the type of vehicle (car/ van/ motorbike).

        String leftAlignFormat = "| %-14s | %-12s |%n";

        System.out.format("+-----------------+--------------+%n");
        System.out.format("|   Plate ID      |   Type       |%n");
        System.out.format("+-----------------+--------------+%n");

        for (Vehicle item : vehiclesInSystem) {
            if (item instanceof Car) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Car");
            } else if (item instanceof Motorbike) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Motorbike");
            }
        }
        System.out.println("+--------------------------------+");
    }

    @Override
    public void save() {        //saves the information of vehicles entered into the system

        //Rewrite the file every time a change is made.
//        try {       //creating the file
//            File myFile = new File("allVehicles.txt");
//            myFile.createNewFile();
//
////                System.out.println("\nFile created: " + myFile.getName());
//            FileWriter soldFile = new FileWriter("allVehicles.txt", true);
//
//
//            //Add specialized information as well!!!!!!!!!!!!!!!!!
//
//
//            soldFile.write(String.format("+-----------------+---------------+--------------+--------------+----------------+---------------+-----------+--------------+--------+------------+------------+%n"));
//            soldFile.write(String.format("|   Plate ID      |   Make        |   Model      | Availability | Engine Capacity| Daily Cost($) |   Type    | transmission | AirCon | Start type | Wheel Size |%n"));
//            soldFile.write(String.format("+-----------------+---------------+--------------+--------------+----------------+---------------+-----------+--------------+--------+------------+------------+%n"));
////                soldFile.write(System.getProperty("line.separator"));       //line break
//
//
//            String leftAlignFormat2 = "| %-15s | %-13s | %-12s | %-12s | %-14s | %-13s | %-9s | %-12s | %-6s | %-10s | %-10s |%n";
//
//
//            //writing into the file
//            for (Vehicle item : vehiclesInSystem) {
//
//                if (item.isAvailability()){     //if availability == true
//                    String avail = "yes";
//                } else{
//                    String avail = "no";
//                }
//
//
////                soldFile.write(String.format());      get availability as a String(use if, else)
//                soldFile.write(System.getProperty("line.separator"));       //line break
//
//            }
//
//            soldFile.close();
//
//        } catch (IOException e) {
//            System.out.println("\nAn error occurred.");
//            e.printStackTrace();
//        }

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
//                (yes/no)!!!!!!!?????
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

        System.out.println("\nEnter Daily cost (in $):");
        System.out.print(">$");
        doubleInputValidation();
        dailyCostD = scanInput.nextDouble();

        dailyCostBigD = BigDecimal.valueOf(dailyCostD);     //converting double to BigDecimal, to use for calculations


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
