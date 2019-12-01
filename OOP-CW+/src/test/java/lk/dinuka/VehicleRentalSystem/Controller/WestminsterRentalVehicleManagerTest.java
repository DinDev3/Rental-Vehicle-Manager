package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Car;
import lk.dinuka.VehicleRentalSystem.Model.Motorbike;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager.*;
import static lk.dinuka.VehicleRentalSystem.Model.RentalVehicleManager.MAX_VEHICLES;
import static lk.dinuka.VehicleRentalSystem.Model.Vehicle.count;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WestminsterRentalVehicleManagerTest {

    //test HashMap
    private static HashMap<String, Vehicle> vehiclesMap = getAllVehicles();          //used to check whether the plate No already exists in the system

    //test arrayList
    private static List<Vehicle> vehiclesArrayList = getVehiclesInSystem();      //getting the existing arrayList into a temporary arrayList


    @Test
    public void addVehicleCar() {          //testing whether a car can be added into the system
        Vehicle newCar = new Car("CAR-123", "Honda", "Grace", "1300", BigDecimal.valueOf(70), "Car", "Auto", true);

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (initialNumOfVehicles <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots


            vehiclesMap.put("CAR-123", newCar);       //adding new car into vehiclesMap
            vehiclesArrayList.add(newCar);

//            System.out.println(vehiclesArrayList);
//            System.out.println(vehiclesMap);

//            assertTrue("New Car wasn't added into the system", vehiclesArrayList.add(newCar));      //checking whether the car was added to the arrayList
            assertEquals(initialNumOfVehicles + 1, vehiclesArrayList.size());          //??
            assertEquals(initialNumOfVehicles + 1, vehiclesMap.size());


            System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");


            assertTrue("The new car hasn't been added to the system arrayList", vehiclesArrayList.contains(newCar));
            assertTrue("The new car hasn't been added to the system hashMap", vehiclesMap.containsKey("CAR-123"));


        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs
    }

    @Test
    public void addVehicleBike() {          //testing whether a motorbike can be added into the system
        Vehicle newBike = new Motorbike("BIK-123", "Hero", "Honda", "800", BigDecimal.valueOf(40), "Motorbike", "Push", 15);

        int initialNumOfVehicles = vehiclesArrayList.size();
        System.out.println(vehiclesArrayList);
        System.out.println(vehiclesMap);

        if (initialNumOfVehicles <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots

            vehiclesMap.put("BIK-123", newBike);       //adding new car into vehicles arrayList
            vehiclesArrayList.add(newBike);

//            System.out.println(vehiclesArrayList);
//            System.out.println(vehiclesMap);


//            assertTrue("New Motorbike wasn't added into the system", vehiclesArrayList.add(newBike));      //checking whether the motorbike was added to the arrayList
            assertEquals(initialNumOfVehicles + 1, vehiclesArrayList.size());          //??
            assertEquals(initialNumOfVehicles+1, vehiclesMap.size());


            System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");


            assertTrue("The new motorbike hasn't been added to the system arrayList", vehiclesArrayList.contains(newBike));
            assertTrue("The new motorbike hasn't been added to the system hashMap", vehiclesMap.containsKey("BIK-123"));


        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs
    }


    @Test
    public void testEditCar() {
        Vehicle newCar = new Car("CAR-123", "Honda", "Grace", "1300", BigDecimal.valueOf(70), "Car", "Auto", true);
        String enteredPlateNo = newCar.getPlateNo();

        vehiclesArrayList.add((newCar));
        vehiclesMap.put(enteredPlateNo, newCar);

        if (vehiclesMap.containsKey(enteredPlateNo)) {
            System.out.println("This Plate No exists in the system.");
            System.out.println();           //to keep space for clarity

            //print information of vehicle when asked whether to edit
            System.out.println("Make: " + vehiclesMap.get(enteredPlateNo).getMake());
            System.out.println("Model: " + vehiclesMap.get(enteredPlateNo).getModel());
            System.out.println("Engine Capacity: " + vehiclesMap.get(enteredPlateNo).getEngineCapacity());
            System.out.println("Daily Cost (in £): " + vehiclesMap.get(enteredPlateNo).getDailyCost());
            System.out.println("Type: " + vehiclesMap.get(enteredPlateNo).getType());

            if (vehiclesMap.get(enteredPlateNo) instanceof Car) {
                System.out.println("Transmission: " + ((Car) vehiclesMap.get(enteredPlateNo)).getTransmission());
                System.out.println("Has Air Conditioning: " + ((Car) vehiclesMap.get(enteredPlateNo)).isHasAirCon());
            } else {
                System.out.println("Start Type: " + ((Motorbike) vehiclesMap.get(enteredPlateNo)).getStartType());
                System.out.println("Wheel Size: " + ((Motorbike) vehiclesMap.get(enteredPlateNo)).getWheelSize());
            }

            boolean edit = true;
            if (edit) {
                System.out.println("\nMake required changes to vehicle information.");

            } else {
                System.out.println();       //keeps space and goes back to main menu
            }
        }

//        vehiclesMap.clear();        //clearing to make sure that other unit tests aren't affected by this unit test
//        vehiclesArrayList.clear();

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs
    }

    @Test
    public void testEditBike() {
        Vehicle newBike = new Motorbike("BIK-123", "Hero", "Honda", "800", BigDecimal.valueOf(40), "Motorbike", "Push", 15);
        String enteredPlateNo = newBike.getPlateNo();

        vehiclesArrayList.add(newBike);
        vehiclesMap.put(enteredPlateNo, newBike);

        if (vehiclesMap.containsKey(enteredPlateNo)) {
            System.out.println("This Plate No exists in the system.");
            System.out.println();           //to keep space for clarity

            //print information of vehicle when asked whether to edit
            System.out.println("Make: " + vehiclesMap.get(enteredPlateNo).getMake());
            System.out.println("Model: " + vehiclesMap.get(enteredPlateNo).getModel());
            System.out.println("Engine Capacity: " + vehiclesMap.get(enteredPlateNo).getEngineCapacity());
            System.out.println("Daily Cost (in £): " + vehiclesMap.get(enteredPlateNo).getDailyCost());
            System.out.println("Type: " + vehiclesMap.get(enteredPlateNo).getType());

            if (vehiclesMap.get(enteredPlateNo) instanceof Car) {
                System.out.println("Transmission: " + ((Car) vehiclesMap.get(enteredPlateNo)).getTransmission());
                System.out.println("Has Air Conditioning: " + ((Car) vehiclesMap.get(enteredPlateNo)).isHasAirCon());
            } else {
                System.out.println("Start Type: " + ((Motorbike) vehiclesMap.get(enteredPlateNo)).getStartType());
                System.out.println("Wheel Size: " + ((Motorbike) vehiclesMap.get(enteredPlateNo)).getWheelSize());
            }

            boolean edit = true;
            if (edit) {
                System.out.println("\nMake required changes to vehicle information.");

            } else {
                System.out.println();       //keeps space and goes back to main menu
            }
        }

//        vehiclesMap.clear();        //clearing to make sure that other unit tests aren't affected by this unit test
//        vehiclesArrayList.clear();

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs
    }


    @Test
    public void testDeleteCarAvailable() {      //testing the result when a car that is in the system is requested to be deleted
        String carPlateNo = "CAR-123";

        int initialNumOfVehicles = vehiclesMap.size();

//        System.out.println(vehiclesArrayList);
//        System.out.println(vehiclesMap);


        if (vehiclesMap.containsKey(carPlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(carPlateNo);

            vehiclesArrayList.remove(vehicleToBeDeleted);
//            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
            vehiclesMap.remove(carPlateNo);
            count -= 1;          //decreasing the number of vehicles from the system by one

            String type = vehicleToBeDeleted.getType();

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted: " + vehicleToBeDeleted.toString());      //displaying information of deleted vehicle


//            assertEquals(initialNumOfVehicles - 1, vehiclesArrayList.size());
            assertEquals(initialNumOfVehicles - 1, vehiclesMap.size());
        } else {
            System.out.println("There's no vehicle related to the Plate No: " + carPlateNo);
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs
    }

    @Test
    public void testDeleteCarUnavailable() {        // testing the result when cars that are not
        // in the system are requested to be deleted
        String carPlateNo = "CAR-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

//        System.out.println(vehiclesArrayList);
//        System.out.println(vehiclesMap);


        if (vehiclesMap.containsKey(carPlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(carPlateNo);

            vehiclesArrayList.remove(vehicleToBeDeleted);
//            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
            vehiclesMap.remove(carPlateNo);
            count -= 1;          //decreasing the number of vehicles from the system by one

            String type = vehicleToBeDeleted.getType();

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted: " + vehicleToBeDeleted.toString());      //displaying information of deleted vehicle


            assertEquals(initialNumOfVehicles - 1, vehiclesArrayList.size());
            assertEquals(initialNumOfVehicles - 1, vehiclesMap.size());
        } else {
            System.out.println("There's no vehicle related to the Plate No: " + carPlateNo);
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs

    }


    @Test
    public void testDeleteBike() {      //testing the result when a motorbike that is in the system is requested to be deleted
        String bikePlateNo = "BIK-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

//        System.out.println(vehiclesArrayList);
//        System.out.println(vehiclesMap);


        if (vehiclesMap.containsKey(bikePlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(bikePlateNo);

            vehiclesArrayList.remove(vehicleToBeDeleted);
//            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
            vehiclesMap.remove(bikePlateNo);
            count -= 1;          //decreasing the number of vehicles from the system by one

            String type = vehicleToBeDeleted.getType();

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted: " + vehicleToBeDeleted.toString());      //displaying information of deleted vehicle


            assertEquals(initialNumOfVehicles - 1, vehiclesArrayList.size());
            assertEquals(initialNumOfVehicles - 1, vehiclesMap.size());
        } else {
            System.out.println("There's no vehicle related to the Plate No: " + bikePlateNo);
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs

    }

    @Test
    public void testDeleteBikeUnavailable() {           // testing the result when motorbikes that are not
        // in the system are requested to be deleted
        String bikePlateNo = "BIK-123";

        int initialNumOfVehicles = vehiclesMap.size();

//        System.out.println(vehiclesArrayList);
//        System.out.println(vehiclesMap);


        if (vehiclesMap.containsKey(bikePlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(bikePlateNo);

            vehiclesArrayList.remove(vehicleToBeDeleted);
//            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
            vehiclesMap.remove(bikePlateNo);
            count -= 1;          //decreasing the number of vehicles from the system by one

            String type = vehicleToBeDeleted.getType();

            System.out.println("\nA " + type + " has been deleted from the system.");
            System.out.println("The details of the vehicle that was deleted: " + vehicleToBeDeleted.toString());      //displaying information of deleted vehicle


            assertEquals(initialNumOfVehicles - 1, vehiclesArrayList.size());
            assertEquals(initialNumOfVehicles - 1, vehiclesMap.size());
        } else {
            System.out.println("There's no vehicle related to the Plate No: " + bikePlateNo);
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs

    }


    @Test
    public void testPrintList() {
        Vehicle newCar = new Car("CAR-123", "Honda", "Grace", "1300", BigDecimal.valueOf(70), "Car", "Auto", true);
        Vehicle newBike = new Motorbike("BIK-123", "Hero", "Honda", "800", BigDecimal.valueOf(40), "Motorbike", "Push", 15);

        vehiclesArrayList.add(newCar);
        vehiclesArrayList.add(newBike);

        allVehicles.put(newCar.getPlateNo(),newCar);
        allVehicles.put(newBike.getPlateNo(),newBike);


        Collections.sort(vehiclesArrayList);     //sort vehicles alphabetically, according to make


        // print the plate number, the type of vehicle (car/ motorbike).

        String leftAlignFormat = "| %-15s | %-12s |%n";

        System.out.format("+-----------------+--------------+%n");
        System.out.format("|   Plate ID      |   Type       |%n");
        System.out.format("+-----------------+--------------+%n");

        for (Vehicle item : vehiclesArrayList) {
            if (item instanceof Car) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Car");
            } else if (item instanceof Motorbike) {
                System.out.format(leftAlignFormat, item.getPlateNo(), "Motorbike");
            }
        }
        System.out.println("+--------------------------------+");

        vehiclesArrayList.clear();              //emptying arrayList so that other unit tests can run smoothly
        count -= 2;

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");       //used to separate test outputs

    }


    //testing write/ save file ---------------
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testSaveFile() throws IOException {
        File file = tempFolder.newFile("test.txt");

        FileWriter soldFile = new FileWriter("test.txt", true);


        soldFile.write(String.format("+-----------------+---------------+--------------+----------------+---------------+-----------+--------------+--------+------------+------------+%n"));
        soldFile.write(String.format("|   Plate ID      |   Make        |   Model      | Engine Capacity| Daily Cost(£) |   Type    | transmission | AirCon | Start type | Wheel Size |%n"));
        soldFile.write(String.format("+-----------------+---------------+--------------+----------------+---------------+-----------+--------------+--------+------------+------------+%n"));
//                soldFile.write(System.getProperty("line.separator"));       //line break


        String leftAlignFormat2 = "| %-15s | %-13s | %-12s | %-14s | %-13s | %-9s | %-12s | %-6s | %-10s | %-10s |%n";


        //writing into the file
        for (Vehicle veh : vehiclesArrayList) {
            if (veh instanceof Motorbike) {
                soldFile.write(String.format(leftAlignFormat2, veh.getPlateNo(), veh.getMake(), veh.getModel(), veh.getEngineCapacity(),
                        veh.getDailyCost(), veh.getType(), "      -     ", "   -  ", ((Motorbike) veh).getStartType(), ((Motorbike) veh).getWheelSize()));
            } else {
                soldFile.write(String.format(leftAlignFormat2, veh.getPlateNo(), veh.getMake(), veh.getModel(), veh.getEngineCapacity(),
                        veh.getDailyCost(), veh.getType(), ((Car) veh).getTransmission(), ((Car) veh).isHasAirCon(), "     -    ", "     -    "));
            }
            soldFile.write(System.getProperty("line.separator"));       //line break
        }
        soldFile.write(String.format("+-----------------+---------------+--------------+----------------+---------------+-----------+--------------+--------+------------+------------+%n"));


        assertTrue(file.exists());


    }
    // ----------------


    @Test
    public void testViewGUIFX() {
//        GUI.main(null);       //used to open javafx application
        //test won't complete until javaFX application is closed
    }

    @Test
    public void testViewGUIAngular() {
//        API.getAllVehiclesToFront();                //send vehicles to front end
//        API.postBookingsFromFront();                //handle booking
//        API.postAvailabilityFromFront();            //handle availability
//
//
//        //Open Angular GUI in browser
//        ProcessBuilder builder = new ProcessBuilder("explorer.exe", "http://localhost:4200/");
//
//        builder.redirectErrorStream(true);
//
//        Process p = null;
//        try {
//            p = builder.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while (true) {
//            try {
//                line = r.readLine();
//                if (line == null) {
//                    break;
//                }
//                System.out.println(line);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Test
    public void testIntInputValidation() {
        int scanInput = 4;

        assertTrue("Only integer numbers are allowed! Please provide a valid input", scanInput == (int) scanInput);

//        Scanner scanInput = new Scanner(System.in);
//
//        File initialFile = new File("C:\\Users\\Dell XPS15\\Documents\\IIT Work\\L5\\OOP\\Coursework 01\\OOP-CW\\TestFiles\\TestInput.txt");
//        try {
//            InputStream targetStream = new FileInputStream(initialFile);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        while (!scanInput.hasNextInt()) {
//            System.out.println("Only integer numbers are allowed! Please provide a valid input");              //error handling message for characters other than integers
//            scanInput.next();                                                     //removing incorrect input entered
//        }

    }

    @Test
    public void testDoubleInputValidation() {
        double scanInput = 4.02;

        assertTrue("Only integer numbers are allowed! Please provide a valid input", scanInput == (double) scanInput);
    }
}



/*Reference:

https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests

https://www.mkyong.com/unittest/junit-4-tutorial-2-expected-exception-test/

https://stackoverflow.com/questions/12558206/how-can-i-check-if-a-value-is-of-type-integer

* */