package co.edu.unal.keys;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class User_Interest_Id implements Serializable{
    
    private String user_e_mail;
    private Integer interest_id;

    public User_Interest_Id() {
    }

    /*public User_Interest_Id(Integer user_id, Integer interest_id) {
        this.user_id = user_id;
        this.interest_id = interest_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }*/

    public Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }
    
}
