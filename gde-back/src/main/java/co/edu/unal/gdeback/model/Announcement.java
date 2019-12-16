package co.edu.unal.gdeback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "announcement")
@EntityListeners(AuditingEntityListener.class)
public class Announcement implements Serializable {

    public Announcement() {
    }

    /*public Announcement(Integer announcement_id, String announcement_body) {
        this.announcement_id = announcement_id;
        //this.announcement_body = announcement_body;
    }*/
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer announcement_id;

    @NotBlank
    private String announcement_body;
    
    @MapsId("group_id")
    @ManyToOne
    @JoinColumn(name="group_id", nullable=false,referencedColumnName = "group_id")
    private StudyGroup a_studyGroup;
    
    //@JsonIgnore
    public Integer getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(Integer announcement_id) {
        this.announcement_id = announcement_id;
    }

    //@JsonIgnore
    public String getAnnouncement_body() {
        return announcement_body;
    }

    public void setAnnouncement_body(String announcement_body) {
        this.announcement_body = announcement_body;
    }

    //@JsonIgnore
    public StudyGroup getA_studyGroup() {
        return a_studyGroup;
    }

    public void setA_studyGroup(StudyGroup a_studyGroup) {
        this.a_studyGroup = a_studyGroup;
    }
    
}
