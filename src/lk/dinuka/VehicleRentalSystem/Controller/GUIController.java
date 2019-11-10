package lk.dinuka.VehicleRentalSystem.Controller;

import lk.dinuka.VehicleRentalSystem.Model.Date;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

public class GUIController {

    public static void createBooking(Vehicle chosenVeh, int yearPickUpInput, int monthPickUpInput, int dayPickUpInput,
                                     int yearDropOffInput, int monthDropOffInput, int dayDropOffInput){
        //used to create a booking as required and add booking info into the system

        Date pickUpDate = new Date(yearPickUpInput,monthPickUpInput,dayPickUpInput);
        Date dropOffDate = new Date(yearDropOffInput,monthDropOffInput,dayDropOffInput);

        Schedule newBooking = new Schedule(pickUpDate,dropOffDate);


        WestminsterRentalVehicleManager.bookedVehicles.put(chosenVeh.getPlateNo(),newBooking);       //adding booked vehicle to bookedVehicles HashMap

        System.out.println(WestminsterRentalVehicleManager.bookedVehicles);         //checking whether required booking was entered into the system
    }


    public static void checkAvailabilityOfVeh(){
        //used to check for the availability of a chosen vehicle

    }




    //have calculation of total cost here?????????????
//    dailycost*no of days
}
