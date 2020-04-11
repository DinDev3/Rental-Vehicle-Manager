package lk.dinuka.VehicleRentalSystem.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Schedule {
    private LocalDate pickUp;
    private LocalDate dropOff;

    public Schedule(LocalDate pick, LocalDate drop) {
        this.pickUp = pick;
        this.dropOff = drop;
    }

    public LocalDate getPickUp() {
        return pickUp;
    }


    public LocalDate getDropOff() {
        return dropOff;
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