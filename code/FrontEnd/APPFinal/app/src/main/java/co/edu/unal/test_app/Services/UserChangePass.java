package co.edu.unal.test_app.Services;

public class UserChangePass {

    private String user_e_mail;

    private String last_password;

    private String new_password;

    public UserChangePass(String user_e_mail, String last_password, String new_password) {
        this.user_e_mail = user_e_mail;
        this.last_password = last_password;
        this.new_password = new_password;
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public String getLast_password() {
        return last_password;
    }

    public void setLast_password(String last_password) {
        this.last_password = last_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}