package co.edu.unal.test_app.Services;

public class Announcement {

    private Integer announcement_id;
    private String announcement_body;
    private StudyGroup a_studyGroup;

    public Announcement(Integer announcement_id, String announcement_body, StudyGroup a_studyGroup) {
        this.announcement_id = announcement_id;
        this.announcement_body = announcement_body;
        this.a_studyGroup = a_studyGroup;
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

    public StudyGroup getA_studyGroup() {
        return a_studyGroup;
    }

    public void setA_studyGroup(StudyGroup a_studyGroup) {
        this.a_studyGroup = a_studyGroup;
    }
}
