package co.edu.unal.test_app.keys;

public class User_studyGroup_Id {

    private String user_e_mail;
    private Integer studyGroup_id;

    public User_studyGroup_Id(String user_e_mail, Integer studyGroup_id) {
        this.user_e_mail = user_e_mail;
        this.studyGroup_id = studyGroup_id;
    }

    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public Integer getStudyGroup_id() {
        return studyGroup_id;
    }

    public void setStudyGroup_id(Integer studyGroup_id) {
        this.studyGroup_id = studyGroup_id;
    }
}
