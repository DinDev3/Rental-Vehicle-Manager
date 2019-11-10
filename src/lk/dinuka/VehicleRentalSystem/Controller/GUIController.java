package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Date;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

import java.math.BigDecimal;

public class GUIController {

    public static void createBooking(Vehicle chosenVeh, int yearPickUpInput, int monthPickUpInput, int dayPickUpInput,
                                     int yearDropOffInput, int monthDropOffInput, int dayDropOffInput) {
        //used to create a booking as required and add booking info into the system

        Date pickUpDate = new Date(yearPickUpInput, monthPickUpInput, dayPickUpInput);
        Date dropOffDate = new Date(yearDropOffInput, monthDropOffInput, dayDropOffInput);

        Schedule newBooking = new Schedule(pickUpDate, dropOffDate);


        WestminsterRentalVehicleManager.bookedVehicles.put(chosenVeh.getPlateNo(), newBooking);       //adding booked vehicle to bookedVehicles HashMap

        System.out.println(WestminsterRentalVehicleManager.bookedVehicles);         //checking whether required booking was entered into the system
    }


    public static boolean checkAvailabilityOfVeh(Vehicle chosenVeh, int yearPickUpInput, int monthPickUpInput, int dayPickUpInput,
                                                 int yearDropOffInput, int monthDropOffInput, int dayDropOffInput) {
        //used to check for the availability of a chosen vehicle

        Date pickUpDateCheck = new Date(yearPickUpInput, monthPickUpInput, dayPickUpInput);     //used to check pick up date entered
        Date dropOffDateCheck = new Date(yearDropOffInput, monthDropOffInput, dayDropOffInput);     //used to check drop off date entered



        if (!WestminsterRentalVehicleManager.bookedVehicles.containsKey(chosenVeh.getPlateNo())) {
            return true;        //vehicle is not booked
        }
//        else if (WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh).getDropOff(). ||
//                WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh).getPickUp()) {
//            //check with before
//        }

        return false;
    }


    public static BigDecimal getCalculatedRent(BigDecimal dailyCost){
//        have calculation of total cost here

        BigDecimal totalCost= BigDecimal.valueOf(0);
        int noOfDays = 0;


        if (noOfDays>0){
            return dailyCost.multiply(BigDecimal.valueOf(noOfDays));    //dailyCost*noOfDays
        }
        return totalCost;
    }

}
