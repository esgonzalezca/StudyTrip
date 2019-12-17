package co.edu.unal.basics;

public class UserChangeInterest {
    private String user_e_mail;
    private Integer interest_id1;
    private Integer interest_id2;

    public UserChangeInterest(String user_e_mail, Integer interest_id1, Integer interest_id2) {
        this.user_e_mail = user_e_mail;
        this.interest_id1 = interest_id1;
        this.interest_id2 = interest_id2;
    }

    public UserChangeInterest() {
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public Integer getInterest_id1() {
        return interest_id1;
    }

    public void setInterest_id1(Integer interest_id1) {
        this.interest_id1 = interest_id1;
    }

    public Integer getInterest_id2() {
        return interest_id2;
    }

    public void setInterest_id2(Integer interest_id2) {
        this.interest_id2 = interest_id2;
    }
    
}