package co.edu.unal.keys;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class User_Event_Id implements Serializable{
    
    private String user_e_mail;
    private Integer event_id;

    public User_Event_Id(String user_e_mail, Integer event_id) {
        this.user_e_mail = user_e_mail;
        this.event_id = event_id;
    }
 
    public User_Event_Id() {
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }
}