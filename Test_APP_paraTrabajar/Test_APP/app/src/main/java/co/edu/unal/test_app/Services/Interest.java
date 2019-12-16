package co.edu.unal.test_app.Services;

import java.util.Set;

public class Interest {

    private Integer interest_id;
    private String interest_name;
    private StudyGroup interest_group;
    private Set<User_Interest> interests;

    public Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

    public StudyGroup getInterest_group() {
        return interest_group;
    }

    public void setInterest_group(StudyGroup interest_group) {
        this.interest_group = interest_group;
    }

    public Set<User_Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<User_Interest> interests) {
        this.interests = interests;
    }
}
