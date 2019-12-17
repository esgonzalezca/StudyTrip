package co.edu.unal.test_app.Services;

public class Reservation {
    private Integer reservation_id;
    private Integer day;
    private Integer hour;
    private Integer location_id;

    public Reservation(Integer hour, Integer day, Integer location_id) {
        this.day = day;
        this.hour = hour;
        this.location_id = location_id;
    }

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }
}
