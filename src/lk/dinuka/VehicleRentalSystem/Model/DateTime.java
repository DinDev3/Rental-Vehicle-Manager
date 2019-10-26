package lk.dinuka.VehicleRentalSystem.Model;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hours;
    private int mins;
    private String ampm;


    public DateTime(int year, int month, int day) {         //this order of parameters needs to be maintained to properly validate day
        this.year = year;

    }

    public DateTime(int year, int month, int day, int hours, int mins, String ampm) {         //this order of parameters needs to be maintained to properly validate day
        this.year = year;
        setMonth(month);           //validate month
        setDay(day);             //validate day
        this.hours = hours;     //have validations in setters!!!!!!!!!!!!!!
        this.mins = mins;     //have validations in setters!!!!!!!!!!!!!!
        this.ampm = ampm;     //have validations in setters!!!!!!!!!!!!!! and make sure that toString is given properly

        System.out.printf("Date & Time entered is : %s\n", this);           //checking input date & time
    }

    private void setMonth(int month) {               //validate month
        if (month > 0 && month <= 12) {
            this.month = month;
        } else {
            System.out.printf("Invalid month (%d); set to 1\n", month);
            this.month = 1;             //inserted to maintain object in consistent state
        }
    }

    private void setDay(int day) {               //validate day

        int[] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 2 && day == 29 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
            this.day = day;
        } else if (day > 0 && day <= daysPerMonth[month]) {                             //check if date is within range of month
            this.day = day;
        } else {
            System.out.printf("Invalid day (%d); set to 1\n", day);
            this.day = 1;             //inserted to maintain object in consistent state
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHours() {
        return hours;
    }

    private void setHours(int hours) {
        this.hours = hours;
    }

    public int getMins() {
        return mins;
    }

    private void setMins(int mins) {
        this.mins = mins;
    }

    public String getAmpm() {
        return ampm;
    }

    private void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    @Override
    public String toString() {
        return "" + day +
                "/" + month +
                "/" + year +
                "  " + hours +
                ":" + mins +
                "" + ampm +
                "";
    }
}
