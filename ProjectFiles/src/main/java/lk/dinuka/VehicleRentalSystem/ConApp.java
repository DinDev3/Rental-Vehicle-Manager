package lk.dinuka.VehicleRentalSystem;

import lk.dinuka.VehicleRentalSystem.Controller.API;
import lk.dinuka.VehicleRentalSystem.Controller.DatabaseController;
import lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager;

import java.util.HashMap;
import java.util.Scanner;

public class ConApp {

    private static HashMap<String, String> accessCredentials = new HashMap<>();          //used to store for the user name & password to access the system functions
    //A hashMap is used to allow multiple user access credentials

    public static void main(String[] args) {

        accessCredentials.put("PrimaryAdmin", "welcome123");            //valid user name and password

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Login Credentials to access system");
        System.out.printf("UserName: ");
        String username = sc.nextLine();
        System.out.printf("Password: ");
        String password = sc.nextLine();
        sc.reset();                     //clearing the cache of the scanner to secure username and password

        if (accessCredentials.containsKey(username) && password.equals(accessCredentials.get(username))) {

            int chooseOption;

            API.allowHeaders();         //allow headers in multiple responses

            DatabaseController.importSystemDB();              //importing Vehicles and Bookings saved in database

            System.out.println("\n----All vehicles and bookings retrieved from database.----");
            System.out.println("```````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````");


            do {
                System.out.println("\n\t\\~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~/");
                System.out.println("\t||````` ~~\tVehicle Rental System\t~~ `````||");
                System.out.println("\t/~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\");

                //display main menu
                System.out.println("\n1) Add item");
                System.out.println("2) Delete item");
                System.out.println("3) Print list of items");
                System.out.println("4) Open GUI");
                System.out.println("5) Exit program");
//            Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter Option:\n>>");

                while (!sc.hasNextInt()) {              //validation for integer input
                    System.out.println("Only integer numbers are allowed! Please provide a valid input");              //error handling message for characters other than integers
                    sc.next();                                                     //removing incorrect input entered
                }

                chooseOption = sc.nextInt();

                WestminsterRentalVehicleManager managementAction = new WestminsterRentalVehicleManager();         //new object


                switch (chooseOption) {
                    case 1:         //add vehicle
                        managementAction.addVehicle();
                        break;

                    case 2:         //delete vehicle
                        managementAction.deleteVehicle();
                        break;

                    case 3:         //print list of vehicles
                        managementAction.printList();
                        break;

                    case 4:         //open GUI
                        managementAction.viewGUI();
                        break;

                    case 5:         //display exit message
                        System.out.println("\n\n****        +----------------------+          ****");
                        System.out.println(" Thank you for using the Vehicle Management System");
                        System.out.println("\tLooking forward to assist you in the future.");
                        System.out.println("\t\t\tExiting Program...");
                        System.out.println("                   +-------+ ");
                        System.exit(0);

                    default:
                        System.out.println("Invalid input. Please try again");
                }
            } while (chooseOption != 5);
        } else {
            System.out.println("> Incorrect Access Credentials were entered! <");
        }

    }
}


/*Reference:

https://stackoverflow.com/questions/7421612/slf4j-failed-to-load-class-org-slf4j-impl-staticloggerbinder

*/