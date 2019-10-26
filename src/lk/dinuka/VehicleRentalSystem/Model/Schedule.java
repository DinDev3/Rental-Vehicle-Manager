package lk.dinuka.VehicleRentalSystem.Model;

import java.util.Objects;

public class Schedule {
    private DateTime pickUp;
    private DateTime dropOff;

    public Schedule(DateTime pickUp, DateTime dropOff) {
        this.pickUp = pickUp;
        this.dropOff = dropOff;
    }

    public DateTime getPickUp() {
        return pickUp;
    }

    public void setPickUp(DateTime pickUp) {            //Is there a point in having setters here!!!!!!!?????
        this.pickUp = pickUp;
    }

    public DateTime getDropOff() {
        return dropOff;
    }

    public void setDropOff(DateTime dropOff) {
        this.dropOff = dropOff;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "pick up=" + pickUp +
                ", drop off=" + dropOff +
                '}';
    }

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
}
