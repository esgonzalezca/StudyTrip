package co.edu.unal.gdeback.model;

import co.edu.unal.keys.User_Event_Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.persistence.EmbeddedId;

@Entity
@Table(name = "user_event")
@EntityListeners(AuditingEntityListener.class)
public class User_Event {
    
    private User_Event_Id user_event_Id=new User_Event_Id();
    private User user_e;
    private Event event;

    public User_Event() {
    }
    
    @EmbeddedId
    @JsonIgnore
    public User_Event_Id getId(){
        return   user_event_Id;
    }
    
    public void setId(User_Event_Id user_event_Id){
        this.user_event_Id=user_event_Id;
    }
    
    @MapsId("user_e_mail")
    @ManyToOne
    @JoinColumn(name="user_e_mail", nullable=false,referencedColumnName = "user_e_mail")
    @JsonIgnore
    public User getUser_e(){
        return user_e;
    }
    
    public void setUser_e(User user){
        this.user_e=user;
    }
    
    @MapsId("event_id")
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false,referencedColumnName = "event_id")
    @JsonIgnore
    public Event getEvent(){
        return event;
    }
    public void setEvent(Event event){
        this.event=event;
    }
}