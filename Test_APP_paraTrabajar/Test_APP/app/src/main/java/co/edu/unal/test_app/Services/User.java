package co.edu.unal.test_app.Services;

public class User {

    private String user_e_mail;

    private String user_password;

    public User(String user_e_mail, String user_password) {
        this.user_e_mail = user_e_mail;
        this.user_password = user_password;
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

}