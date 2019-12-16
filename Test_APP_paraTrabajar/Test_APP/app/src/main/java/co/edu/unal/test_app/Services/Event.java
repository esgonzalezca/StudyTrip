package co.edu.unal.test_app.Services;

public class Event {

    private Integer event_id;
    private String event_name;
    private String event_description;
    private Integer event_time;
    private Integer event_duration;
    private String event_special_guest;
    private Location location;
    private StudyGroup studyGroup;

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

    public Integer getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Integer event_time) {
        this.event_time = event_time;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
