package co.edu.unal.test_app.keys;

public class User_Event_Id {

    private Integer event_id;
    private String user_e_mail;

    public User_Event_Id(Integer event_id, String user_e_mail) {
        this.event_id = event_id;
        this.user_e_mail = user_e_mail;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }
}
