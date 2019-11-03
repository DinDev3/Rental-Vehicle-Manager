package lk.dinuka.VehicleRentalSystem.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Schedule {
    private Date pickUp;
    private Date dropOff;
    private String time;

    public Schedule(Date pickUp, Date dropOff) {
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        setTime();
    }

    public Date getPickUp() {
        return pickUp;
    }

    public void setPickUp(Date pickUp) {            //Is there a point in having setters here!!!!!!!?????
        this.pickUp = pickUp;
    }

    public Date getDropOff() {
        return dropOff;
    }

    public void setDropOff(Date dropOff) {
        this.dropOff = dropOff;
    }


    public String getTime() {
        return time;
    }

    public void setTime() {     //getting time at which the booking was made

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        String h1 =  sdf.format(cal.getTime()) ;

        this.time = sdf.format(cal.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(pickUp, schedule.pickUp) &&
                Objects.equals(dropOff, schedule.dropOff) &&
                Objects.equals(time, schedule.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickUp, dropOff, time);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "pickUp=" + pickUp +
                ", dropOff=" + dropOff +
                ", time='" + time + '\'' +
                '}';
    }

}


/*
References:

Current time
https://stackoverflow.com/questions/833768/java-code-for-getting-current-time
https://docs.oracle.com/javase/1.5.0/docs/api/java/text/SimpleDateFormat.html#month
https://www.javatpoint.com/java-get-current-date

 */