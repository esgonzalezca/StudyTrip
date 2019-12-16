package co.edu.unal.test_app.Services;

import java.util.Set;

public class StudyGroup {

    private Integer group_id;
    private String group_name;
    private String group_description;

    private Integer interest_id;


    public StudyGroup(Integer group_id, String group_name, String group_description, Integer group_interest) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_description = group_description;
        this.interest_id = group_interest;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }



    public Integer getGroup_interest() {
        return interest_id;
    }

    public void setGroup_interest(Integer group_interest) {
        this.interest_id = group_interest;
    }






}
