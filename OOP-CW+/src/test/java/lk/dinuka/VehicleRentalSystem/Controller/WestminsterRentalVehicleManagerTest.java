package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Car;
import lk.dinuka.VehicleRentalSystem.Model.Motorbike;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager.getAllVehicles;
import static lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager.getVehiclesInSystem;
import static lk.dinuka.VehicleRentalSystem.Model.RentalVehicleManager.MAX_VEHICLES;
import static lk.dinuka.VehicleRentalSystem.Model.Vehicle.count;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WestminsterRentalVehicleManagerTest {

    //test HashMap
    private HashMap<String, Vehicle> vehiclesMap = getAllVehicles();          //used to check whether the plate No already exists in the system

    //test arrayList
    private List<Vehicle> vehiclesArrayList = getVehiclesInSystem();      //getting the existing arrayList into a temporary arrayList


    @Test
    public void addVehicleCar() {          //testing whether a car can be added into the system
        Vehicle newCar = new Car("CAR-123", "Honda", "Grace", "1300", BigDecimal.valueOf(70), "Car", "Auto", true);

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (initialNumOfVehicles <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots


            vehiclesMap.put("CAR-123", newCar);       //adding new car into vehicles arrayList

            assertTrue("New Car wasn't added into the system", vehiclesArrayList.add(newCar));      //checking whether the car was added to the arrayList
            assertEquals(initialNumOfVehicles + 1, vehiclesArrayList.size());          //??
            assertEquals(initialNumOfVehicles + 1, vehiclesMap.size());


            System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");


            assertTrue("The new car hasn't been added to the system arrayList", vehiclesArrayList.contains(newCar));
            assertTrue("The new car hasn't been added to the system hashMap", vehiclesMap.containsKey("CAR-123"));


        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs
    }

    @Test
    public void addVehicleBike() {          //testing whether a motorbike can be added into the system
        Vehicle newBike = new Motorbike("BIK-123", "Hero", "Honda", "800", BigDecimal.valueOf(40), "Motorbike", "Push", 15);

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (initialNumOfVehicles <= MAX_VEHICLES) {       //checking whether the vehicles existing in the system has occupied all the available parking lots

            vehiclesMap.put("BIK-123", newBike);       //adding new car into vehicles arrayList

            assertTrue("New Motorbike wasn't added into the system", vehiclesArrayList.add(newBike));      //checking whether the motorbike was added to the arrayList
            assertEquals(initialNumOfVehicles + 1, vehiclesArrayList.size());          //??
            assertEquals(initialNumOfVehicles + 1, vehiclesMap.size());


            System.out.println("\nThere are " + (MAX_VEHICLES - Vehicle.getCount()) + " parking lots left, to park vehicles.");


            assertTrue("The new motorbike hasn't been added to the system arrayList", vehiclesArrayList.contains(newBike));
            assertTrue("The new motorbike hasn't been added to the system hashMap", vehiclesMap.containsKey("BIK-123"));


        } else {
            System.out.println("There are no available spaces. 50 vehicles have been added!");
        }

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs
    }


    @Test
    public void testDeleteCarAvailable() {      //testing the result when a car that is in the system is requested to be deleted
        String carPlateNo = "CAR-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (vehiclesMap.containsKey(carPlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(carPlateNo);

            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
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

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs
    }

    @Test
    public void testDeleteCarUnavailable() {        // testing the result when cars that are not
        // in the system are requested to be deleted
        String carPlateNo = "CAR-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (vehiclesMap.containsKey(carPlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(carPlateNo);

            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
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

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs

    }


    @Test
    public void testDeleteBike() {      //testing the result when a motorbike that is in the system is requested to be deleted
        String bikePlateNo = "BIK-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (vehiclesMap.containsKey(bikePlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(bikePlateNo);

            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
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

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs

    }

    @Test
    public void testDeleteBikeUnavailable() {           // testing the result when motorbikes that are not
        // in the system are requested to be deleted
        String bikePlateNo = "BIK-123";

        int initialNumOfVehicles = vehiclesArrayList.size();

        if (vehiclesMap.containsKey(bikePlateNo)) {
            Vehicle vehicleToBeDeleted = vehiclesMap.get(bikePlateNo);

            assertTrue(vehiclesArrayList.remove(vehicleToBeDeleted));
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

        System.out.println("----------------------------------------------------------------------------------------------");       //used to separate test outputs

    }



    @Test
    public void printList() {
        Vehicle newCar = new Car("CAR-123", "Honda", "Grace", "1300", BigDecimal.valueOf(70), "Car", "Auto", true);
        Vehicle newBike = new Motorbike("BIK-123", "Hero", "Honda", "800", BigDecimal.valueOf(40), "Motorbike", "Push", 15);

        vehiclesArrayList.add(newCar);
        vehiclesArrayList.add(newBike);


        Collections.sort(vehiclesArrayList);     //sort vehicles alphabetically, according to make


        // print the plate number, the type of vehicle (car/ van/ motorbike).

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
        count -=2;

    }


    //testing write/ save file ---------------
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCreateFile() throws IOException {
        File file = tempFolder.newFile("test.txt");
        assertTrue(file.exists());
    }
    // ----------------


    @Test
    public void testViewGUI() {

    }
}