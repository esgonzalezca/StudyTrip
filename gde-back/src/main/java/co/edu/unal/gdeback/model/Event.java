package co.edu.unal.gdeback.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event")
@EntityListeners(AuditingEntityListener.class)
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer event_id;

    @NotBlank
    private String event_name;
    
    @NotBlank
    private String event_description;
    
    @NotNull
    private Integer event_time;
    
    @NotNull
    private Integer event_duration;
    
    @NotBlank
    private String event_special_guest;
    
    //@OneToOne(mappedBy = "interest_id")//esto es necesario? sería el mismo interés que el del grupo
    //private Integer event_interest;
    
    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    private Location location;
    
    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private StudyGroup studyGroup_e;

    public StudyGroup getStudyGroup_e() {
        return studyGroup_e;
    }

    public void setStudyGroup_e(StudyGroup studyGroup_e) {
        this.studyGroup_e = studyGroup_e;
    }
    
    
    
    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }
    
    public Integer getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Integer event_time) {
        this.event_time = event_time;
    }

    public Integer getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(Integer event_duration) {
        this.event_duration = event_duration;
    }

    public String getEvent_special_guest() {
        return event_special_guest;
    }

    public void setEvent_special_guest(String event_special_guest) {
        this.event_special_guest = event_special_guest;
    }
    
}