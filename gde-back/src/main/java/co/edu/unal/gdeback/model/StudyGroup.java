package co.edu.unal.gdeback.model;

import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "study_group")
@EntityListeners(AuditingEntityListener.class)
public class StudyGroup {

    public StudyGroup() {
    }

    public StudyGroup(Integer group_id,String group_description, String group_name, Integer interest_id) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_description = group_description;
        this.interest_id = interest_id;
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer group_id;

    @NotBlank
    private String group_name;
    
    @NotBlank
    private String group_description;
    private int interest_id;
    
    @OneToMany(mappedBy = "studyGroup_e")
    private Set<Event> events;
    
    
    @ManyToOne
    @JoinColumn(name="interest_id", nullable=false, updatable=false, insertable=false)
    private Interest interest_s;

    public Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }

    
    
   

    public void setInterest_s(Interest interest_s) {
        this.interest_s = interest_s;
    }
    
    
    
   /* @NotNull
    private Integer interest_id;*/
    
   
    
    @OneToMany(mappedBy = "a_studyGroup")
    private Set<Announcement> announcements;
    
    @OneToMany(mappedBy = "studyGroup")
    private Set<User_StudyGroup> studyGroup;
    
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
   
}