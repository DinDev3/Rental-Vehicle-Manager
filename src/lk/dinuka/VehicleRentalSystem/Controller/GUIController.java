package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Date;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GUIController {

    public static void createBooking(Vehicle chosenVeh, int yearPickUpInput, int monthPickUpInput, int dayPickUpInput,
                                     int yearDropOffInput, int monthDropOffInput, int dayDropOffInput) {
        //used to create a booking as required and add booking info into the system


        boolean availability = checkAvailabilityOfVeh(chosenVeh, yearPickUpInput, monthPickUpInput,
                dayPickUpInput, yearDropOffInput, monthDropOffInput, dayDropOffInput);   //checking whether vehicle is available for booking


        System.out.println();
        System.out.println("---checked availability---");
        System.out.println();

        if (availability) {
            Schedule newBooking = new Schedule(yearPickUpInput, monthPickUpInput, dayPickUpInput,
                    yearDropOffInput, monthDropOffInput, dayDropOffInput);


            WestminsterRentalVehicleManager.bookedVehicles.put(chosenVeh.getPlateNo(), newBooking);       //adding booked vehicle to bookedVehicles HashMap

            //add this booking to database

            System.out.println(WestminsterRentalVehicleManager.bookedVehicles);         //checking whether required booking was entered into the system
        }else{
            //vehicle isn't available to be book
        }
    }

    public static boolean checkAvailabilityOfVeh(Vehicle chosenVeh, int yearPickUpInput, int monthPickUpInput, int dayPickUpInput,
                                                 int yearDropOffInput, int monthDropOffInput, int dayDropOffInput) {
        //used to check for the availability of a chosen vehicle

        Schedule newBooking = new Schedule(yearPickUpInput, monthPickUpInput, dayPickUpInput,       //creating new Schedule to check
                yearDropOffInput, monthDropOffInput, dayDropOffInput);


        if (!WestminsterRentalVehicleManager.bookedVehicles.containsKey(chosenVeh.getPlateNo())) {
            return true;        //vehicle is not booked
        } else {
            boolean checkPickUpBefore = LocalDate.from(newBooking.getPickUp()).isBefore(        //pick up before booked pickup
                    WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh.getPlateNo()).getPickUp());
            boolean checkDropOffBefore = LocalDate.from(newBooking.getDropOff()).isBefore(      //drop off before booked pick up
                    WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh.getPlateNo()).getDropOff());

            boolean checkPickUpAfter = LocalDate.from(newBooking.getDropOff()).isAfter(                //pick up after booked drop off
                    WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh.getPlateNo()).getDropOff());
            boolean checkDropOffAfter = LocalDate.from(newBooking.getDropOff()).isAfter(                 //drop off after booked drop off
                    WestminsterRentalVehicleManager.bookedVehicles.get(chosenVeh.getPlateNo()).getDropOff());

            if ((checkPickUpBefore && checkDropOffBefore) || (checkPickUpAfter && checkDropOffAfter)) {
                // if both pick up and drop off are either before the booked pick up date or after the booked drop off date
                // vehicle is available for requested period

                return true;

            } else {
                return false;
            }
        }

//        return false;
    }


    public static BigDecimal getCalculatedRent(BigDecimal dailyCost) {
//        have calculation of total cost here

        BigDecimal totalCost = BigDecimal.valueOf(0);
        int noOfDays = 0;


        if (noOfDays > 0) {
            return dailyCost.multiply(BigDecimal.valueOf(noOfDays));    //dailyCost*noOfDays
        }
        return totalCost;
    }

}
