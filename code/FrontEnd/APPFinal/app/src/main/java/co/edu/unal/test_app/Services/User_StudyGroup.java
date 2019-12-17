package co.edu.unal.test_app.Services;

import co.edu.unal.test_app.keys.User_studyGroup_Id;

public class User_StudyGroup {

    private User_studyGroup_Id user_studyGroup_Id;
    private User user_g;
    private StudyGroup studyGroup;

    public User_studyGroup_Id getUser_studyGroup_Id() {
        return user_studyGroup_Id;
    }

    public void setUser_studyGroup_Id(User_studyGroup_Id user_studyGroup_Id) {
        this.user_studyGroup_Id = user_studyGroup_Id;
    }

    public User getUser_g() {
        return user_g;
    }

    public void setUser_g(User user_g) {
        this.user_g = user_g;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
