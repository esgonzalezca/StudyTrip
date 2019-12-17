package co.edu.unal.test_app.Services;

public class Event {

    private Integer event_id;
    private String event_name;
    private String event_description;

    private Integer event_duration;
    private String event_special_guest;
    private Integer group_id;
    private Integer reservation_id;

    public Event(String event_name, String event_description, Integer event_duration, String event_special_guest, Integer group_id, Integer reservation_id) {
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_duration = event_duration;
        this.event_special_guest = event_special_guest;
        this.group_id = group_id;
        this.reservation_id=reservation_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }


    public Integer getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(Integer event_duration) {
        this.event_duration = event_duration;
    }

    public String getEvent_special_guest() {
        return event_special_guest;
    }

    public void setEvent_special_guest(String event_special_guest) {
        this.event_special_guest = event_special_guest;
    }

}
