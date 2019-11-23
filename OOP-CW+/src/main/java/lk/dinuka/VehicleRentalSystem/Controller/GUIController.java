package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager.bookedVehicles;


public class GUIController {

    public static boolean createBooking(Vehicle chosenVeh, Schedule newBooking ) {
        //used to create a booking as required and add booking info into the system

        List<Schedule> bookedVehicleDates = new ArrayList<>();     //used to record pick up & drop off dates of a vehicle
        //Only used to store the dates into the bookedVehicles HashMap

        boolean availability = checkAvailabilityOfVeh(chosenVeh, newBooking);   //checking whether vehicle is available for booking


        System.out.println();
        System.out.println("---checked availability---");
        System.out.println();

        if (availability) {
            System.out.println("Vehicle is available for booking");

            if (bookedVehicles.containsKey(chosenVeh.getPlateNo())) {
                bookedVehicleDates = bookedVehicles.get(chosenVeh.getPlateNo());            //getting recorded bookings into temporary list
            }
            bookedVehicleDates.add(newBooking);     //adding the newly booked dates to the list of bookings.

            WestminsterRentalVehicleManager.bookedVehicles.put(chosenVeh.getPlateNo(), (ArrayList) bookedVehicleDates);       //adding all booked vehicles to bookedVehicles HashMap

            System.out.println(WestminsterRentalVehicleManager.bookedVehicles);         //checking whether required booking was entered into the system
            return true;
        } else {
            System.out.println("Vehicle isn't available for booking during the requested time period.");
            //vehicle isn't available to be book
            return false;
        }
    }


    //``````~~~~~~~~~~~~~~~~~~~``````


    public static boolean checkAvailabilityOfVeh(Vehicle chosenVeh, Schedule newBooking ) {
        //used to check for the availability of a chosen vehicle

        String plateNoOfChosen = chosenVeh.getPlateNo();        //The plate number of the chosen vehicle


        if (!WestminsterRentalVehicleManager.bookedVehicles.containsKey(plateNoOfChosen)) {
            return true;        //vehicle is not booked
        } else {

            List<Schedule> bookedVehicleDates = new ArrayList<>();     //used to record pick up & drop off dates of a vehicle
            bookedVehicleDates = bookedVehicles.get(chosenVeh.getPlateNo());            //getting recorded bookings into temporary list
            //Only used to get each of the dates from the bookedVehicles HashMap Values


            int totalBookings = bookedVehicles.get(plateNoOfChosen).size();
            int passedChecks = 0;

            for (int i = 0; i < totalBookings; i++) {

                boolean checkPickUpBefore = LocalDate.from(newBooking.getPickUp()).isBefore(        //pick up before booked pickup
                        bookedVehicleDates.get(i).getPickUp());
                boolean checkDropOffBefore = LocalDate.from(newBooking.getDropOff()).isBefore(      //drop off before booked pick up
                        bookedVehicleDates.get(i).getPickUp());

                boolean checkPickUpAfter = LocalDate.from(newBooking.getPickUp()).isAfter(                //pick up after booked drop off
                        bookedVehicleDates.get(i).getDropOff());
                boolean checkDropOffAfter = LocalDate.from(newBooking.getDropOff()).isAfter(                 //drop off after booked drop off
                        bookedVehicleDates.get(i).getDropOff());


                if ((checkPickUpBefore && checkDropOffBefore) || (checkPickUpAfter && checkDropOffAfter)) {
                    // if both requested pick up and drop off are either before the booked pick up date or after the
                    // booked drop off date, the vehicle is available for requested period

                    passedChecks += 1;

                }
                //if false for at least one, can't book
            }

            //-------------------

//            if (totalBookings>0){
//                return passedChecks == totalBookings;            //if all the bookings don't interfere with the requested time -> true
//
//            } else{
//                return true;
            //since this else block will run only if there has been at least one previous entry, the above verification isn't required
            return passedChecks == totalBookings;            //if all the bookings don't interfere with the requested time -> true

        }
    }


    public static BigDecimal getCalculatedRent(BigDecimal dailyCost, Schedule newBooking) {
//        have calculation of total cost here

        BigDecimal totalCost = BigDecimal.valueOf(0);

        Period period = Period.between(newBooking.getPickUp(),newBooking.getDropOff());//difference between the number of days
        int noOfDays = period.getDays();

        if (noOfDays > 0) {
            return dailyCost.multiply(BigDecimal.valueOf(noOfDays));    //dailyCost*noOfDays
        }
        return totalCost;
    }






    //----------------------------->>>>
    //Booking methods for Angular GUI
    public static boolean createBooking(String plateNo, Schedule newBooking ) {
        //used to create a booking as required and add booking info into the system

        List<Schedule> bookedVehicleDates = new ArrayList<>();     //used to record pick up & drop off dates of a vehicle
        //Only used to store the dates into the bookedVehicles HashMap

        boolean availability = checkAvailabilityOfVeh(plateNo, newBooking);   //checking whether vehicle is available for booking


        System.out.println();
        System.out.println("---checked availability---");
        System.out.println();

        if (availability) {
            System.out.println("Vehicle is available for booking");

            if (bookedVehicles.containsKey(plateNo)) {
                bookedVehicleDates = bookedVehicles.get(plateNo);            //getting recorded bookings into temporary list
            }
            bookedVehicleDates.add(newBooking);     //adding the newly booked dates to the list of bookings.

            WestminsterRentalVehicleManager.bookedVehicles.put(plateNo, (ArrayList) bookedVehicleDates);       //adding all booked vehicles to bookedVehicles HashMap

            System.out.println(WestminsterRentalVehicleManager.bookedVehicles);         //checking whether required booking was entered into the system
            return true;
        } else {
            System.out.println("Vehicle isn't available for booking during the requested time period.");
            //vehicle isn't available to be book
            return false;
        }
    }


    //``````~~~~~~~~~~~~~~~~~~~``````


    public static boolean checkAvailabilityOfVeh(String plateNo, Schedule newBooking ) {
        //used to check for the availability of a chosen vehicle

        String plateNoOfChosen = plateNo;        //The plate number of the chosen vehicle


        if (!WestminsterRentalVehicleManager.bookedVehicles.containsKey(plateNoOfChosen)) {
            return true;        //vehicle is not booked
        } else {

            List<Schedule> bookedVehicleDates = new ArrayList<>();     //used to record pick up & drop off dates of a vehicle
            bookedVehicleDates = bookedVehicles.get(plateNoOfChosen);            //getting recorded bookings into temporary list
            //Only used to get each of the dates from the bookedVehicles HashMap Values


            int totalBookings = bookedVehicles.get(plateNoOfChosen).size();
            int passedChecks = 0;

            for (int i = 0; i < totalBookings; i++) {

                boolean checkPickUpBefore = LocalDate.from(newBooking.getPickUp()).isBefore(        //pick up before booked pickup
                        bookedVehicleDates.get(i).getPickUp());
                boolean checkDropOffBefore = LocalDate.from(newBooking.getDropOff()).isBefore(      //drop off before booked pick up
                        bookedVehicleDates.get(i).getPickUp());

                boolean checkPickUpAfter = LocalDate.from(newBooking.getPickUp()).isAfter(                //pick up after booked drop off
                        bookedVehicleDates.get(i).getDropOff());
                boolean checkDropOffAfter = LocalDate.from(newBooking.getDropOff()).isAfter(                 //drop off after booked drop off
                        bookedVehicleDates.get(i).getDropOff());


                if ((checkPickUpBefore && checkDropOffBefore) || (checkPickUpAfter && checkDropOffAfter)) {
                    // if both requested pick up and drop off are either before the booked pick up date or after the
                    // booked drop off date, the vehicle is available for requested period

                    passedChecks += 1;

                }
                //if false for at least one, can't book
            }

            //-------------------

//            if (totalBookings>0){
//                return passedChecks == totalBookings;            //if all the bookings don't interfere with the requested time -> true
//
//            } else{
//                return true;
            //since this else block will run only if there has been at least one previous entry, the above verification isn't required
            return passedChecks == totalBookings;            //if all the bookings don't interfere with the requested time -> true

        }
    }

}
