package co.edu.unal.test_app.Services;

public class Announcement {

    private Integer announcement_id;
    private String announcement_topic;
    private String announcement_body;
    private Integer group_id;

    public Announcement(String announcement_topic, String announcement_body,Integer group_id) {
        this.announcement_topic=announcement_topic;
        this.announcement_body = announcement_body;
        this.group_id=group_id;
    }

    public Announcement(String announcement_topic, String announcement_body) {
        this.announcement_topic = announcement_topic;
        this.announcement_body = announcement_body;
    }

    public String getAnnouncement_topic() {
        return announcement_topic;
    }

    public void setAnnouncement_topic(String announcement_topic) {
        this.announcement_topic = announcement_topic;
    }

    public Integer getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(Integer announcement_id) {
        this.announcement_id = announcement_id;
    }

    public String getAnnouncement_body() {
        return announcement_body;
    }

    public void setAnnouncement_body(String announcement_body) {
        this.announcement_body = announcement_body;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }
}
