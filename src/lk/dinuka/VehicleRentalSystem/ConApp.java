package lk.dinuka.VehicleRentalSystem;

import lk.dinuka.VehicleRentalSystem.Controller.DatabaseController;
import lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager;

import java.util.Scanner;

public class ConApp {

    public static void main(String[] args) {
        int chooseOption;

        DatabaseController.importSystemDB();              //importing items saved in database to itemsInStore ArrayList

        do {
            System.out.println("\n\t\\~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~/");
            System.out.println("\t~~\tVehicle Rental System\t~~");
            System.out.println("\t/~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\");

            //display main menu
            System.out.println("\n1)Add item");
            System.out.println("2)Delete item");
            System.out.println("3)Print list of items");
            System.out.println("4)Open GUI");
            System.out.println("5)Exit program");
            Scanner sc = new Scanner(System.in);
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
                    System.out.println("\nThank you for using the Vehicle Management System");
                    System.out.println("\tLooking forward to assist you in the future.");
                    System.out.println("\tExiting Program...");
                    System.exit(0);

                default:
                    System.out.println("Invalid input. Please try again");
            }
        } while (chooseOption != 5);
    }
}
