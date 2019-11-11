package lk.dinuka.VehicleRentalSystem.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Schedule {
    private LocalDate pickUp;
    private LocalDate dropOff;

    public Schedule(int yearPickUp, int monthPickUp, int dayPickUp,int yearDropOff, int monthDropOff,int dayDropOff) {
        setPickUp(yearPickUp,monthPickUp,dayPickUp);
        setDropOff(yearDropOff,monthDropOff,dayDropOff);
    }

    public LocalDate getPickUp() {
        return pickUp;
    }

    public void setPickUp(int yearPickUp, int monthPickUp, int dayPickUp) {
        Date pickUpDateCheck = new Date(yearPickUp,monthPickUp,dayPickUp);      //used to validate date
        LocalDate pickUpDate = LocalDate.of(pickUpDateCheck.getYear(), pickUpDateCheck.getMonth(), pickUpDateCheck.getDay());

        this.pickUp = pickUpDate;
    }

    public LocalDate getDropOff() {
        return dropOff;
    }

    public void setDropOff(int yearDropOff, int monthDropOff,int dayDropOff) {
        Date dropOffDateCheck = new Date(yearDropOff,monthDropOff,dayDropOff);      //used to validate date
        LocalDate dropOffDate = LocalDate.of(dropOffDateCheck.getYear(), dropOffDateCheck.getMonth(), dropOffDateCheck.getDay());

        this.dropOff = dropOffDate;
    }


//    public String getTime() {
//        return time;
//    }

//    public void setTime() {     //getting time at which the booking was made
//
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
////        String h1 =  sdf.format(cal.getTime()) ;
//
//        this.time = sdf.format(cal.getTime());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(pickUp, schedule.pickUp) &&
                Objects.equals(dropOff, schedule.dropOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickUp, dropOff);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "pickUp=" + pickUp +
                ", dropOff=" + dropOff +
                '}';
    }

}


/*
References:

Java 8 DateTime
https://gist.github.com/mscharhag/9195718

Current time
https://stackoverflow.com/questions/833768/java-code-for-getting-current-time
https://docs.oracle.com/javase/1.5.0/docs/api/java/text/SimpleDateFormat.html#month
https://www.javatpoint.com/java-get-current-date

 */