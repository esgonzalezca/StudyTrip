package co.edu.unal.gdeback.model;

import co.edu.unal.keys.User_studyGroup_Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.persistence.EmbeddedId;

@Entity
@Table(name = "user_studyGroup")
@EntityListeners(AuditingEntityListener.class)
public class User_StudyGroup  {
    
    private User_studyGroup_Id user_studyGroup_Id=new User_studyGroup_Id();
    private User user_g;
    private StudyGroup studyGroup;

    public User_StudyGroup() {
    }
    
    @EmbeddedId
    @JsonIgnore
    public User_studyGroup_Id getId(){
        return   user_studyGroup_Id;
    }
    
    public void setId(User_studyGroup_Id user_studyGroup_Id){
        this.user_studyGroup_Id=user_studyGroup_Id;
    }

    @MapsId("user_e_mail")
    @ManyToOne
    @JoinColumn(name="user_e_mail", nullable=false,referencedColumnName = "user_e_mail")
    @JsonIgnore
    public User getUser_g(){
        return user_g;
    }
    
    public void setUser_g(User user){
        this.user_g=user;
    }
    
    @MapsId("studyGroup_id")
    @ManyToOne
    @JoinColumn(name="group_id", nullable=false,referencedColumnName = "group_id")
    @JsonIgnore
    public StudyGroup getStudyGroup(){
        return studyGroup;
    }
    
    public void setStudyGroup(StudyGroup studyGroup){
        this.studyGroup=studyGroup;
    }

}