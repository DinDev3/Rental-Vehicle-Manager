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

    protected static HashMap<String, Vehicle> allVehicles = new HashMap<>();          //used to check whether the plate No already exists in the system
    protected static ArrayList<Vehicle> vehiclesInSystem = new ArrayList<>();       //used for sorting and printing.    protected: making sure that customers can't modify the vehicles in the system
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

    private static boolean replaceVeh;          //used to check whether vehicle data is being added or edited


    @Override
    public void addVehicle() {

        if (Vehicle.getCount() <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots

            System.out.println("\nChoose the type of Vehicle to be added:");
            System.out.println("1)Car\n2)Motorbike");
            System.out.print(">");
            intInputValidation();
            int typeSelection = scanInput.nextInt();
            scanInput.nextLine();              //to consume the rest of the line


            System.out.println("\nEnter Plate No:");
            System.out.print(">");
            plateNo = scanInput.nextLine();

            if (allVehicles.containsKey(plateNo)) {
                System.out.println("This Plate No exists in the system.");
                System.out.println();           //to keep space for clarity

                replaceVeh =false;

                //print information of vehicle
                System.out.println("Make: " + allVehicles.get(plateNo).getMake());
                System.out.println("Model: " + allVehicles.get(plateNo).getModel());
                System.out.println("Availability: " + allVehicles.get(plateNo).isAvailability());
                System.out.println("Engine Capacity: " + allVehicles.get(plateNo).getEngineCapacity());
                System.out.println("Daily Cost: " + allVehicles.get(plateNo).getDailyCost());
                System.out.println("Type: " + allVehicles.get(plateNo).getType());

                if (allVehicles.get(plateNo) instanceof Car) {
                    System.out.println("Transmission: " + ((Car) allVehicles.get(plateNo)).getTransmission());
                    System.out.println("Has Air Conditioning: " + ((Car) allVehicles.get(plateNo)).isHasAirCon());
                } else {
                    System.out.println("Start Type: " + ((Motorbike) allVehicles.get(plateNo)).getStartType());
                    System.out.println("Wheel Size: " + ((Motorbike) allVehicles.get(plateNo)).getWheelSize());
                }


                System.out.println();           //to keep space for clarity
                System.out.println("Do u want to edit information related to this vehicle?");
                System.out.print(">");

                boolean edit = yesOrNo();

                if (edit) {

                    replaceVeh = true;

                    //remove vehicle from db
                    DatabaseController.deleteFromSystemDB(plateNo);

                    addInfo(typeSelection);             //add information related to a Vehicle of identified plateNo.

                } else {
                    System.out.println();       //keeps space and goes back to main menu
                }
            } else {

                addInfo(typeSelection);             //add information related to a Vehicle of identified plateNo.

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

        if (allVehicles.containsKey(searchNo)) {
            Vehicle vehicleToBeDeleted = findVehicle(searchNo);

            type = vehicleToBeDeleted.getType();

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted:" + vehicleToBeDeleted.toString());      //displaying information of deleted vehicle

            vehiclesInSystem.remove(vehicleToBeDeleted);
            allVehicles.remove(searchNo);
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

    private static void addInfo(int typeSelection) {          //method to add information related to a Vehicle of identified plateNo.

        if (replaceVeh) {
            vehiclesInSystem.remove(allVehicles.get(plateNo));              //removing vehicle from ArrayList, if editing it's information
        }

        if (typeSelection == 1) {       //new Car chosen
            addCommonInfo();

            type = "Car";

            System.out.println("\nEnter the type of transmission:");
            System.out.print(">");
            transmission = scanInput.nextLine();


            System.out.println("\nDoes this car have A/C?");
            System.out.print(">");

            hasAirCon = yesOrNo();


            Vehicle newCar = new Car(plateNo, make, model, availability, engineCapacity, dailyCostBigD, type, transmission, hasAirCon);

            allVehicles.put(plateNo, newCar);           //adding a car into the allVehicles hashMap
            vehiclesInSystem.add(newCar);

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

            allVehicles.put(plateNo, newBike);           //adding a motorbike into the allVehicles hashMap
            vehiclesInSystem.add(newBike);

            //adding new Bike to noSQL database
            DatabaseController.addToSystemDB(plateNo, make, model, availability, engineCapacity, dailyCostD, type, startType, wheelSize);

            System.out.println(newBike);        //displaying added vehicle
        }

        System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");

//            save();     //save changes to file??


    }


    private static void addCommonInfo() {       //common information related to Car & Motorbike in addVehicle


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


    private static boolean yesOrNo() {           //gets yes/ no input

        while (!scanInput.hasNextBoolean()) {                                            //check whether this works as expected!!!!!!!!!!!
            String inputYN = scanInput.nextLine().toLowerCase();
            if (inputYN.equals("y") || inputYN.equals("yes")) {
                return true;
            } else if (inputYN.equals("n") || inputYN.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please try again.");
                System.out.print(">");
            }
        }
        return false;           //won't reach this point (added to get rid of the missing return statement error)
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


    public static Vehicle findVehicle(String searchPlateNo) {          //used to search for vehicle in GUI

        return allVehicles.get(searchPlateNo);                  //only this line is enough! A method isn't required for this!!!
//        for (Vehicle searchItem : vehiclesInSystem) {
//            if (searchItem.getPlateNo().equals(searchPlateNo)) {
//                return searchItem;
//            }
//        }
//        return null;
    }



}


/*
References:

Java Big Decimal
https://www.geeksforgeeks.org/bigdecimal-class-java/

https://stackoverflow.com/questions/27409718/java-reading-multiple-objects-from-a-file-as-they-were-in-an-array

-------

https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo

https://www.callicoder.com/java-arraylist/

https://stackoverflow.com/questions/48720936/java-enhanced-for-loop-for-arraylist-with-custom-object

To open GUI from console
https://stackoverflow.com/questions/2550310/can-a-main-method-of-class-be-invoked-from-another-class-in-java

File handling
https://www.w3schools.com/java/java_files.asp

Next line in file handling
https://stackoverflow.com/questions/17716192/insert-line-break-when-writing-to-file

File handling - table format
https://stackoverflow.com/questions/26229140/writing-data-to-text-file-in-table-format

Table display format for print list
https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console

Selling date/time
https://www.javatpoint.com/java-get-current-date

Search for object in ArrayList
https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property
*/